package com.mycompany.jpaassociations.onetomany.compositepk.rest;

import com.mycompany.jpaassociations.onetomany.compositepk.model.Player;
import com.mycompany.jpaassociations.onetomany.compositepk.model.Weapon;
import com.mycompany.jpaassociations.onetomany.compositepk.rest.dto.CreatePlayerDto;
import com.mycompany.jpaassociations.onetomany.compositepk.rest.dto.CreateWeaponDto;
import com.mycompany.jpaassociations.onetomany.compositepk.rest.dto.PlayerDto;
import com.mycompany.jpaassociations.onetomany.compositepk.rest.dto.WeaponDto;
import com.mycompany.jpaassociations.onetomany.compositepk.service.PlayerService;
import com.mycompany.jpaassociations.onetomany.compositepk.service.WeaponService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/players")
public class PlayerWeaponController {

    private final PlayerService playerService;
    private final WeaponService weaponService;
    private final MapperFacade mapperFacade;

    public PlayerWeaponController(PlayerService playerService, WeaponService weaponService, MapperFacade mapperFacade) {
        this.playerService = playerService;
        this.weaponService = weaponService;
        this.mapperFacade = mapperFacade;
    }

    // ------
    // Player

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{playerId}")
    public PlayerDto getPlayer(@PathVariable Long playerId) {
        Player player = playerService.validateAndGetPlayer(playerId);
        return mapperFacade.map(player, PlayerDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PlayerDto createPlayer(@Valid @RequestBody CreatePlayerDto createPlayerDto) {
        Player player = mapperFacade.map(createPlayerDto, Player.class);
        player = playerService.savePlayer(player);
        return mapperFacade.map(player, PlayerDto.class);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{playerId}")
    public PlayerDto deletePlayer(@PathVariable Long playerId) {
        Player player = playerService.validateAndGetPlayer(playerId);
        playerService.deletePlayer(player);
        return mapperFacade.map(player, PlayerDto.class);
    }

    // ------
    // Weapon

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{playerId}/weapons/{weaponId}")
    public WeaponDto getWeapon(@PathVariable Long playerId, @PathVariable Long weaponId) {
        Weapon weapon = weaponService.validateAndGetWeapon(playerId, weaponId);
        return mapperFacade.map(weapon, WeaponDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{playerId}/weapons")
    public WeaponDto addWeapon(@PathVariable Long playerId, @Valid @RequestBody CreateWeaponDto createWeaponDto) {
        Player player = playerService.validateAndGetPlayer(playerId);
        Weapon weapon = mapperFacade.map(createWeaponDto, Weapon.class);

        // to avoid "org.hibernate.HibernateException: No part of a composite identifier may be null"
        // in spite of the fact that it's set a fixed value here, hibernate will generate a new value
        weapon.setId(1L);
        weapon.setPlayer(player);
        weapon = weaponService.saveWeapon(weapon);

        return mapperFacade.map(weapon, WeaponDto.class);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{playerId}/weapons/{weaponId}")
    public WeaponDto removeWeapon(@PathVariable Long playerId, @PathVariable Long weaponId) {
        Weapon weapon = weaponService.validateAndGetWeapon(playerId, weaponId);
        weaponService.deleteWeapon(weapon);
        return mapperFacade.map(weapon, WeaponDto.class);
    }

}
