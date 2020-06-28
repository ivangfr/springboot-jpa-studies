package com.mycompany.jpaassociations.onetomany.compositepk.rest;

import com.mycompany.jpaassociations.AbstractTestcontainers;
import com.mycompany.jpaassociations.onetomany.compositepk.model.Player;
import com.mycompany.jpaassociations.onetomany.compositepk.model.Weapon;
import com.mycompany.jpaassociations.onetomany.compositepk.model.WeaponPk;
import com.mycompany.jpaassociations.onetomany.compositepk.repository.PlayerRepository;
import com.mycompany.jpaassociations.onetomany.compositepk.repository.WeaponRepository;
import com.mycompany.jpaassociations.onetomany.compositepk.rest.dto.CreatePlayerDto;
import com.mycompany.jpaassociations.onetomany.compositepk.rest.dto.CreateWeaponDto;
import com.mycompany.jpaassociations.onetomany.compositepk.rest.dto.PlayerDto;
import com.mycompany.jpaassociations.onetomany.compositepk.rest.dto.WeaponDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PlayerWeaponControllerTest extends AbstractTestcontainers {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private WeaponRepository weaponRepository;

    @Test
    void testGetPlayer() {
        Player player = playerRepository.save(getDefaultPlayer());

        String url = String.format(API_PLAYERS_PLAYER_ID_URL, player.getId());
        ResponseEntity<PlayerDto> responseEntity = testRestTemplate.getForEntity(url, PlayerDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(player.getId(), responseEntity.getBody().getId());
        assertEquals(player.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getWeapons().size());
    }

    @Test
    void testCreatePlayer() {
        CreatePlayerDto createPlayerDto = getDefaultCreatePlayerDto();
        ResponseEntity<PlayerDto> responseEntity = testRestTemplate.postForEntity(API_PLAYERS_URL, createPlayerDto, PlayerDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createPlayerDto.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getWeapons().size());

        Optional<Player> playerOptional = playerRepository.findById(responseEntity.getBody().getId());
        assertTrue(playerOptional.isPresent());
        playerOptional.ifPresent(p -> assertEquals(createPlayerDto.getName(), p.getName()));
    }

    @Test
    void testDeletePlayer() {
        Player player = playerRepository.save(getDefaultPlayer());

        String url = String.format("/api/players/%s", player.getId());
        ResponseEntity<PlayerDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, PlayerDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(player.getId(), responseEntity.getBody().getId());
        assertEquals(player.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getWeapons().size());

        Optional<Player> playerOptional = playerRepository.findById(player.getId());
        assertFalse(playerOptional.isPresent());
    }

    @Test
    void testGetWeapon() {
        Player player = playerRepository.save(getDefaultPlayer());

        Weapon weapon = getDefaultWeapon();
        weapon.setPlayer(player);
        weapon = weaponRepository.save(weapon);

        String url = String.format(API_PLAYERS_PLAYER_ID_WEAPONS_WEAPON_ID_URL, player.getId(), weapon.getId());
        ResponseEntity<WeaponDto> responseEntity = testRestTemplate.getForEntity(url, WeaponDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(weapon.getId(), responseEntity.getBody().getId());
        assertEquals(weapon.getName(), responseEntity.getBody().getName());
    }

    @Test
    void testAddWeapon() {
        Player player = playerRepository.save(getDefaultPlayer());
        CreateWeaponDto createWeaponDto = getDefaultCreateWeaponDto();

        String url = String.format(API_PLAYERS_PLAYER_ID_WEAPONS_URL, player.getId());
        ResponseEntity<WeaponDto> responseEntity = testRestTemplate.postForEntity(url, createWeaponDto, WeaponDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createWeaponDto.getName(), responseEntity.getBody().getName());

        Optional<Weapon> weaponOptional = weaponRepository.findById(new WeaponPk(responseEntity.getBody().getId(), player.getId()));
        assertTrue(weaponOptional.isPresent());
        weaponOptional.ifPresent(w -> assertEquals(player.getId(), w.getPlayer().getId()));

        Optional<Player> playerOptional = playerRepository.findById(player.getId());
        assertTrue(playerOptional.isPresent());
        playerOptional.ifPresent(p -> {
            assertEquals(1, p.getWeapons().size());
            weaponOptional.ifPresent(w -> assertTrue(p.getWeapons().contains(w)));
        });
    }

    @Test
    void testRemoveWeapon() {
        Player player = playerRepository.save(getDefaultPlayer());

        Weapon weaponAux = getDefaultWeapon();
        weaponAux.setPlayer(player);
        final Weapon weapon = weaponRepository.save(weaponAux);

        String url = String.format(API_PLAYERS_PLAYER_ID_WEAPONS_WEAPON_ID_URL, player.getId(), weapon.getId());
        ResponseEntity<WeaponDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, WeaponDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(weapon.getId(), responseEntity.getBody().getId());
        assertEquals(weapon.getName(), responseEntity.getBody().getName());

        Optional<Weapon> weaponOptional = weaponRepository.findById(new WeaponPk(player.getId(), weapon.getId()));
        assertFalse(weaponOptional.isPresent());

        Optional<Player> playerOptional = playerRepository.findById(player.getId());
        assertTrue(playerOptional.isPresent());
        playerOptional.ifPresent(p -> {
            assertEquals(0, p.getWeapons().size());
            assertFalse(p.getWeapons().contains(weapon));
        });
    }

    private Player getDefaultPlayer() {
        Player player = new Player();
        player.setName("Ivan Franchin");
        return player;
    }

    private CreatePlayerDto getDefaultCreatePlayerDto() {
        CreatePlayerDto createPlayerDto = new CreatePlayerDto();
        createPlayerDto.setName("Ivan Franchin");
        return createPlayerDto;
    }

    private Weapon getDefaultWeapon() {
        Weapon weapon = new Weapon();
        weapon.setId(1L);
        weapon.setName("Machine Gun");
        return weapon;
    }

    private CreateWeaponDto getDefaultCreateWeaponDto() {
        CreateWeaponDto createWeaponDto = new CreateWeaponDto();
        createWeaponDto.setName("Machine Gun");
        return createWeaponDto;
    }

    private static final String API_PLAYERS_URL = "/api/players";
    private static final String API_PLAYERS_PLAYER_ID_URL = "/api/players/%s";
    private static final String API_PLAYERS_PLAYER_ID_WEAPONS_URL = "/api/players/%s/weapons";
    private static final String API_PLAYERS_PLAYER_ID_WEAPONS_WEAPON_ID_URL = "/api/players/%s/weapons/%s";

}