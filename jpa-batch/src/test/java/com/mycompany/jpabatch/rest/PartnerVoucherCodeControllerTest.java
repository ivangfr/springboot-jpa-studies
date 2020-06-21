package com.mycompany.jpabatch.rest;

import com.mycompany.jpabatch.ContainersExtension;
import com.mycompany.jpabatch.model.Partner;
import com.mycompany.jpabatch.model.VoucherCode;
import com.mycompany.jpabatch.repository.PartnerRepository;
import com.mycompany.jpabatch.repository.VoucherCodeRepository;
import com.mycompany.jpabatch.rest.dto.CreatePartnerDto;
import com.mycompany.jpabatch.rest.dto.CreateVoucherCodeDto;
import com.mycompany.jpabatch.rest.dto.PartnerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

@ExtendWith(ContainersExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PartnerVoucherCodeControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private VoucherCodeRepository voucherCodeRepository;

    @Test
    void testGetPartner() {
        Partner partner = getDefaultPartner();
        partner = partnerRepository.save(partner);

        String url = String.format("/api/partners/%s", partner.getId());
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
        ResponseEntity<PartnerDto> responseEntity = testRestTemplate.postForEntity("/api/partners", createPartnerDto, PartnerDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createPartnerDto.getName(), responseEntity.getBody().getName());

        Optional<Partner> optionalPartner = partnerRepository.findById(responseEntity.getBody().getId());
        assertTrue(optionalPartner.isPresent());
        assertEquals(createPartnerDto.getName(), optionalPartner.get().getName());
    }

    @Test
    void testDeletePartner() {
        Partner partner = getDefaultPartner();
        partner = partnerRepository.save(partner);

        String url = String.format("/api/partners/%s", partner.getId());
        ResponseEntity<PartnerDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, PartnerDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(partner.getId(), responseEntity.getBody().getId());
        assertEquals(partner.getName(), responseEntity.getBody().getName());

        Optional<Partner> optionalPartner = partnerRepository.findById(responseEntity.getBody().getId());
        assertFalse(optionalPartner.isPresent());
    }

    @Test
    void testInsertVoucherCodes() {
        Partner partner = getDefaultPartner();
        partner = partnerRepository.save(partner);

        CreateVoucherCodeDto createVoucherCodeDto = getDefaultCreateVoucherCodeDto();

        String url = String.format("/api/partners/%s/insertVoucherCodes", partner.getId());
        ResponseEntity<Integer> responseEntity = testRestTemplate.postForEntity(url, createVoucherCodeDto, Integer.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(createVoucherCodeDto.getVoucherCodes().size(), responseEntity.getBody().intValue());

        Optional<Partner> optionalPartner = partnerRepository.findById(partner.getId());
        assertTrue(optionalPartner.isPresent());
        assertEquals(createVoucherCodeDto.getVoucherCodes().size(), optionalPartner.get().getVoucherCodes().size());
    }

    @Test
    void testSoftDeleteOldVoucherCodes() {
        Partner partner = getDefaultPartner();
        partner = partnerRepository.save(partner);

        List<String> codes = Arrays.asList("111", "112");
        persistPartnerVoucherCodes(partner, codes);

        String url = String.format("/api/partners/%s/softDeleteOldVoucherCodes", partner.getId());
        ResponseEntity<Integer> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, null, Integer.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(codes.size(), responseEntity.getBody().intValue());

        Optional<Partner> optionalPartner = partnerRepository.findById(partner.getId());
        assertTrue(optionalPartner.isPresent());
        assertEquals(codes.size(), optionalPartner.get().getVoucherCodes().size());
        for (VoucherCode voucherCode : optionalPartner.get().getVoucherCodes()) {
            assertTrue(voucherCode.getDeleted());
        }
    }

    @Test
    void testHardDeleteOldVoucherCodes() {
        Partner partner = getDefaultPartner();
        partner = partnerRepository.save(partner);

        List<String> codes = Arrays.asList("111", "112", "113");
        persistPartnerVoucherCodes(partner, codes);

        String url = String.format("/api/partners/%s/hardDeleteOldVoucherCodes", partner.getId());
        ResponseEntity<Integer> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, Integer.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(codes.size(), responseEntity.getBody().intValue());

        Optional<Partner> optionalPartner = partnerRepository.findById(partner.getId());
        assertTrue(optionalPartner.isPresent());
        assertTrue(optionalPartner.get().getVoucherCodes().isEmpty());
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

}