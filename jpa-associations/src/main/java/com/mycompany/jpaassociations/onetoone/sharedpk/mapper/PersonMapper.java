package com.mycompany.jpaassociations.onetoone.sharedpk.mapper;

import com.mycompany.jpaassociations.onetoone.sharedpk.model.Person;
import com.mycompany.jpaassociations.onetoone.sharedpk.model.PersonDetail;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.CreatePersonDetailRequest;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.CreatePersonRequest;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.PersonResponse;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.UpdatePersonDetailRequest;
import com.mycompany.jpaassociations.onetoone.sharedpk.rest.dto.UpdatePersonRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PersonMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "personDetail", ignore = true)
    Person toPerson(CreatePersonRequest createPersonRequest);

    PersonResponse toPersonResponse(Person person);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "personDetail", ignore = true)
    void updatePersonFromRequest(UpdatePersonRequest updatePersonRequest, @MappingTarget Person person);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    PersonDetail toPersonDetail(CreatePersonDetailRequest createPersonDetailRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    void updatePersonDetailFromRequest(UpdatePersonDetailRequest updatePersonDetailRequest,
                                       @MappingTarget PersonDetail personDetail);
}
