package com.mycompany.jpaassociations.onetomany.compositepk.repository;

import com.mycompany.jpaassociations.onetomany.compositepk.model.Weapon;
import com.mycompany.jpaassociations.onetomany.compositepk.model.WeaponPk;
import org.springframework.data.repository.CrudRepository;

public interface WeaponRepository extends CrudRepository<Weapon, WeaponPk> {
}
