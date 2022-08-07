package com.ivanfranchin.jpaassociations.onetomany.compositepk.service;

import com.ivanfranchin.jpaassociations.onetomany.compositepk.model.Weapon;

public interface WeaponService {

    Weapon validateAndGetWeapon(Long playerId, Long weaponId);

    Weapon saveWeapon(Weapon weapon);

    void deleteWeapon(Weapon weapon);
}
