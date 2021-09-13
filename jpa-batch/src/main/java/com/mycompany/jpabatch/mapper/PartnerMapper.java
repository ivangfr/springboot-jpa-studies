package com.mycompany.jpabatch.mapper;

import com.mycompany.jpabatch.model.Partner;
import com.mycompany.jpabatch.rest.dto.CreatePartnerRequest;
import com.mycompany.jpabatch.rest.dto.PartnerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartnerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "voucherCodes", ignore = true)
    Partner toPartner(CreatePartnerRequest createPartnerRequest);

    PartnerResponse toPartnerResponse(Partner partner);
}
