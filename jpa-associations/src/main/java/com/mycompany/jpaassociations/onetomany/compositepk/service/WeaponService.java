package com.mycompany.jpaassociations.onetomany.compositepk.service;

import com.mycompany.jpaassociations.onetomany.compositepk.model.Weapon;

public interface WeaponService {

    Weapon validateAndGetWeapon(Long playerId, Long weaponId);

    Weapon saveWeapon(Weapon weapon);

    void deleteWeapon(Weapon weapon);
}
