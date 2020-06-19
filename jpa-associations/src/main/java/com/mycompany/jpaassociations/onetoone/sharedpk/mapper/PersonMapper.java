package com.mycompany.jpaassociations.onetoone.sharedpk.mapper;

import com.mycompany.jpaassociations.onetoone.sharedpk.model.Person;
import com.mycompany.jpaassociations.onetoone.sharedpk.model.PersonDetail;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.CreatePersonDetailDto;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.CreatePersonDto;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.PersonDto;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.UpdatePersonDetailDto;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.UpdatePersonDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PersonMapper {

    Person toPerson(CreatePersonDto createPersonDto);

    PersonDto toPersonDto(Person person);

    void updatePersonFromDto(UpdatePersonDto updatePersonDto, @MappingTarget Person person);

    PersonDetail toPersonDetail(CreatePersonDetailDto createPersonDetailDto);

    void updatePersonDetailFromDto(UpdatePersonDetailDto updatePersonDetailDto, @MappingTarget PersonDetail personDetail);

}
