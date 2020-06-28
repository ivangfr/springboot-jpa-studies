package com.mycompany.jpabatch.rest;

import com.mycompany.jpabatch.AbstractTestcontainers;
import com.mycompany.jpabatch.model.Partner;
import com.mycompany.jpabatch.model.VoucherCode;
import com.mycompany.jpabatch.repository.PartnerRepository;
import com.mycompany.jpabatch.repository.VoucherCodeRepository;
import com.mycompany.jpabatch.rest.dto.CreatePartnerDto;
import com.mycompany.jpabatch.rest.dto.CreateVoucherCodeDto;
import com.mycompany.jpabatch.rest.dto.PartnerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PartnerVoucherCodeControllerTest extends AbstractTestcontainers {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private VoucherCodeRepository voucherCodeRepository;

    @Test
    void testGetPartner() {
        Partner partner = partnerRepository.save(getDefaultPartner());

        String url = String.format(API_PARTNERS_PARTNER_ID_URL, partner.getId());
        ResponseEntity<PartnerDto> responseEntity = testRestTemplate.getForEntity(url, PartnerDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(partner.getId(), responseEntity.getBody().getId());
        assertEquals(partner.getName(), responseEntity.getBody().getName());
        assertEquals(partner.getId(), responseEntity.getBody().getId());
        assertEquals(partner.getName(), responseEntity.getBody().getName());
    }

    @Test
    void testCreatePartner() {
        CreatePartnerDto createPartnerDto = getDefaultCreatePartnerDto();
        ResponseEntity<PartnerDto> responseEntity = testRestTemplate.postForEntity(API_PARTNERS_URL, createPartnerDto, PartnerDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createPartnerDto.getName(), responseEntity.getBody().getName());

        Optional<Partner> partnerOptional = partnerRepository.findById(responseEntity.getBody().getId());
        assertTrue(partnerOptional.isPresent());
        partnerOptional.ifPresent(p -> assertEquals(createPartnerDto.getName(), p.getName()));
    }

    @Test
    void testDeletePartner() {
        Partner partner = partnerRepository.save(getDefaultPartner());

        String url = String.format(API_PARTNERS_PARTNER_ID_URL, partner.getId());
        ResponseEntity<PartnerDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, PartnerDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(partner.getId(), responseEntity.getBody().getId());
        assertEquals(partner.getName(), responseEntity.getBody().getName());

        Optional<Partner> partnerOptional = partnerRepository.findById(responseEntity.getBody().getId());
        assertFalse(partnerOptional.isPresent());
    }

    @Test
    void testInsertVoucherCodes() {
        Partner partner = partnerRepository.save(getDefaultPartner());
        CreateVoucherCodeDto createVoucherCodeDto = getDefaultCreateVoucherCodeDto();

        String url = String.format(API_PARTNERS_PARTNER_ID_INSERT_VOUCHER_CODES_URL, partner.getId());
        ResponseEntity<Integer> responseEntity = testRestTemplate.postForEntity(url, createVoucherCodeDto, Integer.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(createVoucherCodeDto.getVoucherCodes().size(), responseEntity.getBody().intValue());

        Optional<Partner> partnerOptional = partnerRepository.findById(partner.getId());
        assertTrue(partnerOptional.isPresent());
        partnerOptional.ifPresent(p -> assertEquals(createVoucherCodeDto.getVoucherCodes().size(), p.getVoucherCodes().size()));
    }

    @Test
    void testSoftDeleteOldVoucherCodes() {
        Partner partner = partnerRepository.save(getDefaultPartner());

        List<String> codes = Arrays.asList("111", "112");
        persistPartnerVoucherCodes(partner, codes);

        String url = String.format(API_PARTNERS_PARTNER_ID_SOFT_DELETE_OLD_VOUCHER_CODES_URL, partner.getId());
        ResponseEntity<Integer> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, null, Integer.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(codes.size(), responseEntity.getBody().intValue());

        Optional<Partner> partnerOptional = partnerRepository.findById(partner.getId());
        assertTrue(partnerOptional.isPresent());
        partnerOptional.ifPresent(p -> {
            assertEquals(codes.size(), p.getVoucherCodes().size());
            for (VoucherCode voucherCode : p.getVoucherCodes()) {
                assertTrue(voucherCode.getDeleted());
            }
        });
    }

    @Test
    void testHardDeleteOldVoucherCodes() {
        Partner partner = partnerRepository.save(getDefaultPartner());

        List<String> codes = Arrays.asList("111", "112", "113");
        persistPartnerVoucherCodes(partner, codes);

        String url = String.format(API_PARTNERS_PARTNER_ID_HARD_DELETE_OLD_VOUCHER_CODES_URL, partner.getId());
        ResponseEntity<Integer> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, Integer.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(codes.size(), responseEntity.getBody().intValue());

        Optional<Partner> partnerOptional = partnerRepository.findById(partner.getId());
        assertTrue(partnerOptional.isPresent());
        partnerOptional.ifPresent(p -> assertTrue(p.getVoucherCodes().isEmpty()));
    }

    private Partner getDefaultPartner() {
        Partner partner = new Partner();
        partner.setName("partner1");
        return partner;
    }

    private CreatePartnerDto getDefaultCreatePartnerDto() {
        CreatePartnerDto createPartnerDto = new CreatePartnerDto();
        createPartnerDto.setName("partner1");
        return createPartnerDto;
    }

    private CreateVoucherCodeDto getDefaultCreateVoucherCodeDto() {
        CreateVoucherCodeDto createVoucherCodeDto = new CreateVoucherCodeDto();
        Set<String> codes = new HashSet<>(Arrays.asList("111", "112", "113"));

        createVoucherCodeDto.setVoucherCodes(codes);
        return createVoucherCodeDto;
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