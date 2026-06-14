package com.ivanfranchin.jpabatch.rest;

import com.ivanfranchin.jpabatch.AbstractTestcontainers;
import com.ivanfranchin.jpabatch.partner.Partner;
import com.ivanfranchin.jpabatch.voucher.VoucherCode;
import com.ivanfranchin.jpabatch.partner.PartnerRepository;
import com.ivanfranchin.jpabatch.voucher.VoucherCodeRepository;
import com.ivanfranchin.jpabatch.rest.dto.CreatePartnerRequest;
import com.ivanfranchin.jpabatch.rest.dto.CreateVoucherCodeRequest;
import com.ivanfranchin.jpabatch.rest.dto.PartnerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;

import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PartnerVoucherCodeControllerTest extends AbstractTestcontainers {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private VoucherCodeRepository voucherCodeRepository;

    @BeforeEach
    void setUp() {
        partnerRepository.deleteAll();
        voucherCodeRepository.deleteAll();
    }

    @Test
    void testGetPartner() {
        Partner partner = partnerRepository.save(getDefaultPartner());

        String url = String.format(API_PARTNERS_PARTNER_ID_URL, partner.getId());
        ResponseEntity<PartnerResponse> responseEntity = testRestTemplate.getForEntity(url, PartnerResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().id()).isEqualTo(partner.getId());
        assertThat(responseEntity.getBody().name()).isEqualTo(partner.getName());
    }

    @Test
    void testGetPartnerNotFound() {
        String url = String.format(API_PARTNERS_PARTNER_ID_URL, 999L);
        ResponseEntity<PartnerResponse> responseEntity = testRestTemplate.getForEntity(url, PartnerResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testCreatePartner() {
        CreatePartnerRequest createPartnerRequest = new CreatePartnerRequest("partner1");
        ResponseEntity<PartnerResponse> responseEntity = testRestTemplate.postForEntity(
                API_PARTNERS_URL, createPartnerRequest, PartnerResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().id()).isNotNull();
        assertThat(responseEntity.getBody().name()).isEqualTo(createPartnerRequest.name());

        assertThat(partnerRepository.findById(responseEntity.getBody().id()))
                .isPresent()
                .get()
                .extracting(Partner::getName)
                .isEqualTo(createPartnerRequest.name());
    }

    @Test
    void testCreatePartnerWithBlankName() {
        CreatePartnerRequest createPartnerRequest = new CreatePartnerRequest(" ");
        ResponseEntity<PartnerResponse> responseEntity = testRestTemplate.postForEntity(
                API_PARTNERS_URL, createPartnerRequest, PartnerResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testDeletePartner() {
        Partner partner = partnerRepository.save(getDefaultPartner());

        String url = String.format(API_PARTNERS_PARTNER_ID_URL, partner.getId());
        ResponseEntity<PartnerResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, PartnerResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().id()).isEqualTo(partner.getId());
        assertThat(responseEntity.getBody().name()).isEqualTo(partner.getName());

        assertThat(partnerRepository.findById(responseEntity.getBody().id())).isNotPresent();
    }

    @Test
    void testDeletePartnerNotFound() {
        String url = String.format(API_PARTNERS_PARTNER_ID_URL, 999L);
        ResponseEntity<PartnerResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, PartnerResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testInsertVoucherCodes() {
        Partner partner = partnerRepository.save(getDefaultPartner());
        CreateVoucherCodeRequest createVoucherCodeRequest = new CreateVoucherCodeRequest(Set.of("111", "112", "113"));

        String url = String.format(API_PARTNERS_PARTNER_ID_INSERT_VOUCHER_CODES_URL, partner.getId());
        ResponseEntity<Integer> responseEntity = testRestTemplate.postForEntity(
                url, createVoucherCodeRequest, Integer.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo(createVoucherCodeRequest.voucherCodes().size());

        assertThat(partnerRepository.findByIdWithVoucherCodes(partner.getId()))
                .isPresent()
                .get()
                .extracting(p -> p.getVoucherCodes().size())
                .isEqualTo(createVoucherCodeRequest.voucherCodes().size());
    }

    @Test
    void testGetStreamVoucherCodes() {
        Partner partner = partnerRepository.save(getDefaultPartner());
        List<String> codes = List.of("111", "112", "113");
        persistPartnerVoucherCodes(partner, codes);

        String url = String.format(API_PARTNERS_PARTNER_ID_GET_STREAM_VOUCHER_CODES_URL, partner.getId());
        ResponseEntity<List> responseEntity = testRestTemplate.getForEntity(url, List.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody()).containsExactlyElementsOf(codes);
    }

    @Test
    void testGetStreamVoucherCodesShouldExcludeDeleted() {
        Partner partner = partnerRepository.save(getDefaultPartner());
        persistPartnerVoucherCodes(partner, List.of("111", "112", "113"));

        List<VoucherCode> savedCodes = voucherCodeRepository.findByPartner(partner);
        VoucherCode toDelete = savedCodes.get(0);
        toDelete.setDeleted(true);
        voucherCodeRepository.save(toDelete);

        String url = String.format(API_PARTNERS_PARTNER_ID_GET_STREAM_VOUCHER_CODES_URL, partner.getId());
        ResponseEntity<List> responseEntity = testRestTemplate.getForEntity(url, List.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody()).doesNotContain(toDelete.getCode());
        assertThat(responseEntity.getBody()).hasSize(2);
    }

    @Test
    void testSoftDeleteOldVoucherCodes() {
        Partner partner = partnerRepository.save(getDefaultPartner());

        List<String> codes = List.of("111", "112");
        persistPartnerVoucherCodes(partner, codes);

        String url = String.format(API_PARTNERS_PARTNER_ID_SOFT_DELETE_OLD_VOUCHER_CODES_URL, partner.getId());
        ResponseEntity<Integer> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.PUT, null, Integer.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo(codes.size());

        assertThat(partnerRepository.findByIdWithVoucherCodes(partner.getId()))
                .isPresent()
                .get()
                .satisfies(p -> {
                    assertThat(p.getVoucherCodes()).hasSize(codes.size());
                    assertThat(p.getVoucherCodes()).allMatch(v -> v.isDeleted());
                });
    }

    @Test
    void testHardDeleteOldVoucherCodes() {
        Partner partner = partnerRepository.save(getDefaultPartner());

        List<String> codes = List.of("111", "112", "113");
        persistPartnerVoucherCodes(partner, codes);

        String url = String.format(API_PARTNERS_PARTNER_ID_HARD_DELETE_OLD_VOUCHER_CODES_URL, partner.getId());
        ResponseEntity<Integer> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, Integer.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo(codes.size());

        assertThat(partnerRepository.findByIdWithVoucherCodes(partner.getId()))
                .isPresent()
                .get()
                .satisfies(p -> assertThat(p.getVoucherCodes()).isEmpty());
    }

    private Partner getDefaultPartner() {
        Partner partner = new Partner();
        partner.setName("partner1");
        return partner;
    }

    private void persistPartnerVoucherCodes(Partner partner, List<String> codes) {
        List<VoucherCode> voucherCodes = codes.stream()
                .map(code -> new VoucherCode(partner, code))
                .collect(Collectors.toList());
        voucherCodeRepository.saveAll(voucherCodes);
    }

    private static final String API_PARTNERS_URL = "/api/partners";
    private static final String API_PARTNERS_PARTNER_ID_URL = "/api/partners/%s";
    private static final String API_PARTNERS_PARTNER_ID_INSERT_VOUCHER_CODES_URL = "/api/partners/%s/insertVoucherCodes";
    private static final String API_PARTNERS_PARTNER_ID_SOFT_DELETE_OLD_VOUCHER_CODES_URL = "/api/partners/%s/softDeleteOldVoucherCodes";
    private static final String API_PARTNERS_PARTNER_ID_HARD_DELETE_OLD_VOUCHER_CODES_URL = "/api/partners/%s/hardDeleteOldVoucherCodes";
    private static final String API_PARTNERS_PARTNER_ID_GET_STREAM_VOUCHER_CODES_URL = "/api/partners/%s/getStreamVoucherCodes";
}
