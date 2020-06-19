package com.mycompany.jpaassociations.onetomany.compositepk.mapper;

import com.mycompany.jpaassociations.onetomany.compositepk.model.Weapon;
import com.mycompany.jpaassociations.onetomany.compositepk.rest.dto.CreateWeaponDto;
import com.mycompany.jpaassociations.onetomany.compositepk.rest.dto.WeaponDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeaponMapper {

    Weapon toWeapon(CreateWeaponDto createWeaponDto);

    WeaponDto toWeaponDto(Weapon weapon);

}
