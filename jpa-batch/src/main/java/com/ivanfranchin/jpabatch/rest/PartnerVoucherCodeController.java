package com.ivanfranchin.jpabatch.rest;

import com.ivanfranchin.jpabatch.model.Partner;
import com.ivanfranchin.jpabatch.model.VoucherCode;
import com.ivanfranchin.jpabatch.rest.dto.CreatePartnerRequest;
import com.ivanfranchin.jpabatch.rest.dto.CreateVoucherCodeRequest;
import com.ivanfranchin.jpabatch.rest.dto.PartnerResponse;
import com.ivanfranchin.jpabatch.service.PartnerService;
import com.ivanfranchin.jpabatch.service.VoucherCodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/partners")
public class PartnerVoucherCodeController {

    private final PartnerService partnerService;
    private final VoucherCodeService voucherCodeService;

    @GetMapping("/{partnerId}")
    public PartnerResponse getPartner(@PathVariable Long partnerId) {
        Partner partner = partnerService.validateAndGetPartner(partnerId);
        return PartnerResponse.from(partner);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PartnerResponse createPartner(@Valid @RequestBody CreatePartnerRequest createPartnerRequest) {
        Partner partner = Partner.from(createPartnerRequest);
        partner = partnerService.savePartner(partner);
        return PartnerResponse.from(partner);
    }

    @DeleteMapping("/{partnerId}")
    public PartnerResponse deletePartner(@PathVariable Long partnerId) {
        Partner partner = partnerService.validateAndGetPartner(partnerId);
        partnerService.deletePartner(partner);
        return PartnerResponse.from(partner);
    }

    @Transactional
    @GetMapping("/{partnerId}/getStreamVoucherCodes")
    public List<String> getStreamVoucherCodes(@PathVariable Long partnerId) {
        Partner partner = partnerService.validateAndGetPartner(partnerId);

        try (Stream<VoucherCode> streamOfVoucherCodes = voucherCodeService.getStreamOfVoucherCodesByPartner(partner)) {
            return streamOfVoucherCodes
                    .filter(voucherCode -> !voucherCode.getDeleted())
                    .map(VoucherCode::getCode)
                    .collect(Collectors.toList());
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{partnerId}/insertVoucherCodes")
    public int insertVoucherCodes(@PathVariable Long partnerId,
                                  @Valid @RequestBody CreateVoucherCodeRequest createVoucherCodeRequest) {
        Partner partner = partnerService.validateAndGetPartner(partnerId);

        List<VoucherCode> voucherCodes = createVoucherCodeRequest.voucherCodes()
                .stream()
                .map(code -> new VoucherCode(partner, code))
                .collect(Collectors.toList());

        return voucherCodeService.saveVoucherCodes(voucherCodes).size();
    }

    @Transactional
    @PutMapping("/{partnerId}/softDeleteOldVoucherCodes")
    public long softDeleteOldVoucherCodes(@PathVariable Long partnerId) {
        Partner partner = partnerService.validateAndGetPartner(partnerId);

        try (Stream<VoucherCode> voucherCodes = voucherCodeService.getStreamOfVoucherCodesByPartner(partner)) {
            List<VoucherCode> deletedVoucherCodes = new ArrayList<>();
            voucherCodes.forEach(voucherCode -> {
                voucherCode.setDeleted(true);
                deletedVoucherCodes.add(voucherCode);
            });

            return deletedVoucherCodes.size();
        }
    }

    @DeleteMapping("/{partnerId}/hardDeleteOldVoucherCodes")
    public int hardDeleteOldVoucherCodes(@PathVariable Long partnerId) {
        Partner partner = partnerService.validateAndGetPartner(partnerId);

        List<VoucherCode> voucherCodes = voucherCodeService.getListOfVoucherCodesByPartner(partner);
        voucherCodeService.deleteVoucherCodes(voucherCodes);

        return voucherCodes.size();
    }
}
