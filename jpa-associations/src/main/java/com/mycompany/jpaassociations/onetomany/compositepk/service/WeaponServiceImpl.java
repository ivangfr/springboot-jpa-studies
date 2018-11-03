package com.mycompany.jpaassociations.onetomany.compositepk.service;

import com.mycompany.jpaassociations.onetomany.compositepk.exception.WeaponNotFoundException;
import com.mycompany.jpaassociations.onetomany.compositepk.model.Weapon;
import com.mycompany.jpaassociations.onetomany.compositepk.model.WeaponPk;
import com.mycompany.jpaassociations.onetomany.compositepk.repository.WeaponRepository;
import org.springframework.stereotype.Service;

@Service
public class WeaponServiceImpl implements WeaponService {

    private WeaponRepository weaponRepository;

    public WeaponServiceImpl(WeaponRepository weaponRepository) {
        this.weaponRepository = weaponRepository;
    }

    @Override
    public Weapon validateAndGetWeapon(Long playerId, Long weaponId) {
        WeaponPk weaponPk = new WeaponPk(weaponId, playerId);
        return weaponRepository.findById(weaponPk).orElseThrow(() -> new WeaponNotFoundException(String.format("Weapon with id '%s' not found", weaponPk)));
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
