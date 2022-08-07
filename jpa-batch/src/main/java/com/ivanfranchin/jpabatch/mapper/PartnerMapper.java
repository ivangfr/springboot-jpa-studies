package com.ivanfranchin.jpabatch.mapper;

import com.ivanfranchin.jpabatch.model.Partner;
import com.ivanfranchin.jpabatch.rest.dto.CreatePartnerRequest;
import com.ivanfranchin.jpabatch.rest.dto.PartnerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartnerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "voucherCodes", ignore = true)
    Partner toPartner(CreatePartnerRequest createPartnerRequest);

    PartnerResponse toPartnerResponse(Partner partner);
}
