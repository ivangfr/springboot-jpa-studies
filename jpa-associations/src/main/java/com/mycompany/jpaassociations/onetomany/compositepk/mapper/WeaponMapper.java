package com.mycompany.jpaassociations.onetomany.compositepk.mapper;

import com.mycompany.jpaassociations.onetomany.compositepk.model.Weapon;
import com.mycompany.jpaassociations.onetomany.compositepk.rest.dto.CreateWeaponRequest;
import com.mycompany.jpaassociations.onetomany.compositepk.rest.dto.WeaponResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WeaponMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "player", ignore = true)
    Weapon toWeapon(CreateWeaponRequest createWeaponRequest);

    WeaponResponse toWeaponResponse(Weapon weapon);
}
