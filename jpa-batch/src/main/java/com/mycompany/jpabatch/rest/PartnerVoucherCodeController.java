package com.mycompany.jpabatch.rest;

import com.mycompany.jpabatch.model.Partner;
import com.mycompany.jpabatch.model.VoucherCode;
import com.mycompany.jpabatch.rest.dto.CreatePartnerDto;
import com.mycompany.jpabatch.rest.dto.CreateVoucherCodeDto;
import com.mycompany.jpabatch.rest.dto.PartnerDto;
import com.mycompany.jpabatch.service.PartnerService;
import com.mycompany.jpabatch.service.VoucherCodeService;
import lombok.extern.slf4j.Slf4j;
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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
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

    @DeleteMapping("/{partnerId}")
    public PartnerDto deletePartner(@PathVariable Long partnerId) {
        Partner partner = partnerService.validateAndGetPartner(partnerId);
        partnerService.deletePartner(partner);
        return mapperFacade.map(partner, PartnerDto.class);
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
                                  @Valid @RequestBody CreateVoucherCodeDto createVoucherCodeDto) {
        Partner partner = partnerService.validateAndGetPartner(partnerId);

        List<VoucherCode> voucherCodes = createVoucherCodeDto.getVoucherCodes()
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
            return voucherCodes.peek(voucherCode -> voucherCode.setDeleted(true)).count();
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
