package com.ivanfranchin.jpaassociations.onetomany.compositepk.service;

import com.ivanfranchin.jpaassociations.onetomany.compositepk.exception.WeaponNotFoundException;
import com.ivanfranchin.jpaassociations.onetomany.compositepk.model.Weapon;
import com.ivanfranchin.jpaassociations.onetomany.compositepk.model.WeaponPk;
import com.ivanfranchin.jpaassociations.onetomany.compositepk.repository.WeaponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WeaponServiceImpl implements WeaponService {

    private final WeaponRepository weaponRepository;

    @Override
    public Weapon validateAndGetWeapon(Long playerId, Long weaponId) {
        WeaponPk weaponPk = new WeaponPk(weaponId, playerId);
        return weaponRepository.findById(weaponPk).orElseThrow(() -> new WeaponNotFoundException(weaponPk));
    }

    @Override
    public Weapon saveWeapon(Weapon weapon) {
        return weaponRepository.save(weapon);
    }

    @Override
    public void deleteWeapon(Weapon weapon) {
        weaponRepository.delete(weapon);
    }
}
