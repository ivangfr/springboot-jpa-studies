package com.ivanfranchin.jpaassociations.onetomany.compositepk.repository;

import com.ivanfranchin.jpaassociations.onetomany.compositepk.model.Weapon;
import com.ivanfranchin.jpaassociations.onetomany.compositepk.model.WeaponPk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponRepository extends CrudRepository<Weapon, WeaponPk> {
}
