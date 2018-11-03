package com.mycompany.jpabatch.rest;

import com.mycompany.jpabatch.model.Partner;
import com.mycompany.jpabatch.model.VoucherCode;
import com.mycompany.jpabatch.rest.dto.CreatePartnerDto;
import com.mycompany.jpabatch.rest.dto.CreateVoucherCodeDto;
import com.mycompany.jpabatch.rest.dto.PartnerDto;
import com.mycompany.jpabatch.service.PartnerService;
import com.mycompany.jpabatch.service.VoucherCodeService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/partners")
public class PartnerVoucherCodeController {

    private final PartnerService partnerService;
    private final VoucherCodeService voucherCodeService;
    private final MapperFacade mapperFacade;

    public PartnerVoucherCodeController(PartnerService partnerService, VoucherCodeService voucherCodeService,
                                        MapperFacade mapperFacade) {
        this.partnerService = partnerService;
        this.voucherCodeService = voucherCodeService;
        this.mapperFacade = mapperFacade;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{partnerId}")
    public PartnerDto getPartner(@PathVariable Long partnerId) {
        Partner partner = partnerService.validateAndGetPartner(partnerId);
        return mapperFacade.map(partner, PartnerDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PartnerDto createPartner(@Valid @RequestBody CreatePartnerDto createPartnerDto) {
        Partner partner = mapperFacade.map(createPartnerDto, Partner.class);
        partner = partnerService.savePartner(partner);
        return mapperFacade.map(partner, PartnerDto.class);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{partnerId}")
    public PartnerDto deletePartner(@PathVariable Long partnerId) {
        Partner partner = partnerService.validateAndGetPartner(partnerId);
        partnerService.deletePartner(partner);
        return mapperFacade.map(partner, PartnerDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{partnerId}/insertVoucherCodes")
    public int insertVoucherCodes(@PathVariable Long partnerId,
                                  @Valid @RequestBody CreateVoucherCodeDto createVoucherCodeDto) {
        final Partner partner = partnerService.validateAndGetPartner(partnerId);

        List<VoucherCode> voucherCodes = createVoucherCodeDto.getVoucherCodes().stream()
                .map(code -> new VoucherCode(partner, code))
                .collect(Collectors.toList());

        return voucherCodeService.saveVoucherCodes(voucherCodes).size();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{partnerId}/softDeleteOldVoucherCodes")
    public int softDeleteOldVoucherCodes(@PathVariable Long partnerId) {
        Partner partner = partnerService.validateAndGetPartner(partnerId);

        List<VoucherCode> voucherCodes = voucherCodeService.getVoucherCodesByPartnet(partner);
        List<VoucherCode> updatedVoucherCodes = voucherCodes.stream()
                .peek(voucherCode -> voucherCode.setDeleted(true))
                .collect(Collectors.toList());

        return voucherCodeService.saveVoucherCodes(updatedVoucherCodes).size();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{partnerId}/hardDeleteOldVoucherCodes")
    public int hardDeleteOldVoucherCodes(@PathVariable Long partnerId) {
        Partner partner = partnerService.validateAndGetPartner(partnerId);

        List<VoucherCode> voucherCodes = voucherCodeService.getVoucherCodesByPartnet(partner);
        voucherCodeService.deleteVoucherCodes(voucherCodes);

        return voucherCodes.size();
    }

}
