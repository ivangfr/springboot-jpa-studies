package com.mycompany.jpabatch.mapper;

import com.mycompany.jpabatch.model.Partner;
import com.mycompany.jpabatch.rest.dto.CreatePartnerDto;
import com.mycompany.jpabatch.rest.dto.PartnerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartnerMapper {

    Partner toPartner(CreatePartnerDto createPartnerDto);

    PartnerDto toPartnerDto(Partner partner);

}
