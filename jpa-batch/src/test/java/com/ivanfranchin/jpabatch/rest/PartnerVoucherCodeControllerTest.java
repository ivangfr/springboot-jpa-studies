package com.ivanfranchin.jpabatch.rest;

import com.ivanfranchin.jpabatch.AbstractTestcontainers;
import com.ivanfranchin.jpabatch.model.Partner;
import com.ivanfranchin.jpabatch.model.VoucherCode;
import com.ivanfranchin.jpabatch.repository.PartnerRepository;
import com.ivanfranchin.jpabatch.repository.VoucherCodeRepository;
import com.ivanfranchin.jpabatch.rest.dto.CreatePartnerRequest;
import com.ivanfranchin.jpabatch.rest.dto.CreateVoucherCodeRequest;
import com.ivanfranchin.jpabatch.rest.dto.PartnerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

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
    void testCreatePartner() {
        CreatePartnerRequest createPartnerRequest = new CreatePartnerRequest("partner1");
        ResponseEntity<PartnerResponse> responseEntity = testRestTemplate.postForEntity(
                API_PARTNERS_URL, createPartnerRequest, PartnerResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().id()).isNotNull();
        assertThat(responseEntity.getBody().name()).isEqualTo(createPartnerRequest.name());

        Optional<Partner> partnerOptional = partnerRepository.findById(responseEntity.getBody().id());
        assertThat(partnerOptional.isPresent()).isTrue();
        partnerOptional.ifPresent(p -> assertThat(p.getName()).isEqualTo(createPartnerRequest.name()));
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

        Optional<Partner> partnerOptional = partnerRepository.findById(responseEntity.getBody().id());
        assertThat(partnerOptional.isPresent()).isFalse();
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
        assertThat(responseEntity.getBody().intValue()).isEqualTo(createVoucherCodeRequest.voucherCodes().size());

        Optional<Partner> partnerOptional = partnerRepository.findById(partner.getId());
        assertThat(partnerOptional.isPresent()).isTrue();
        partnerOptional.ifPresent(p ->
                assertThat(p.getVoucherCodes().size()).isEqualTo(createVoucherCodeRequest.voucherCodes().size()));
    }

    @Test
    void testSoftDeleteOldVoucherCodes() {
        Partner partner = partnerRepository.save(getDefaultPartner());

        List<String> codes = Arrays.asList("111", "112");
        persistPartnerVoucherCodes(partner, codes);

        String url = String.format(API_PARTNERS_PARTNER_ID_SOFT_DELETE_OLD_VOUCHER_CODES_URL, partner.getId());
        ResponseEntity<Integer> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.PUT, null, Integer.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().intValue()).isEqualTo(codes.size());

        Optional<Partner> partnerOptional = partnerRepository.findById(partner.getId());
        assertThat(partnerOptional.isPresent()).isTrue();
        partnerOptional.ifPresent(p -> {
            assertThat(p.getVoucherCodes().size()).isEqualTo(codes.size());
            for (VoucherCode voucherCode : p.getVoucherCodes()) {
                assertThat(voucherCode.getDeleted()).isTrue();
            }
        });
    }

    @Test
    void testHardDeleteOldVoucherCodes() {
        Partner partner = partnerRepository.save(getDefaultPartner());

        List<String> codes = Arrays.asList("111", "112", "113");
        persistPartnerVoucherCodes(partner, codes);

        String url = String.format(API_PARTNERS_PARTNER_ID_HARD_DELETE_OLD_VOUCHER_CODES_URL, partner.getId());
        ResponseEntity<Integer> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, Integer.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().intValue()).isEqualTo(codes.size());

        Optional<Partner> partnerOptional = partnerRepository.findById(partner.getId());
        assertThat(partnerOptional.isPresent()).isTrue();
        partnerOptional.ifPresent(p -> assertThat(p.getVoucherCodes().isEmpty()).isTrue());
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
}