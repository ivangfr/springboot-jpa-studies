package com.mycompany.jpaassociations.onetomany.compositepk.rest;

import com.mycompany.jpaassociations.ContainersExtension;
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
import org.junit.jupiter.api.extension.ExtendWith;
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

@ExtendWith(ContainersExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PlayerWeaponControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private WeaponRepository weaponRepository;

    @Test
    void testGetPlayer() {
        Player player = getDefaultPlayer();
        player = playerRepository.save(player);

        String url = String.format("/api/players/%s", player.getId());
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
        ResponseEntity<PlayerDto> responseEntity = testRestTemplate.postForEntity("/api/players", createPlayerDto, PlayerDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createPlayerDto.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getWeapons().size());

        Optional<Player> optionalPlayer = playerRepository.findById(responseEntity.getBody().getId());
        assertTrue(optionalPlayer.isPresent());
        assertEquals(createPlayerDto.getName(), optionalPlayer.get().getName());
    }

    @Test
    void testDeletePlayer() {
        Player player = getDefaultPlayer();
        player = playerRepository.save(player);

        String url = String.format("/api/players/%s", player.getId());
        ResponseEntity<PlayerDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, PlayerDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(player.getId(), responseEntity.getBody().getId());
        assertEquals(player.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getWeapons().size());

        Optional<Player> optionalPlayer = playerRepository.findById(player.getId());
        assertFalse(optionalPlayer.isPresent());
    }

    @Test
    void testGetWeapon() {
        Player player = getDefaultPlayer();
        player = playerRepository.save(player);

        Weapon weapon = getDefaultWeapon();
        weapon.setPlayer(player);
        weapon = weaponRepository.save(weapon);

        String url = String.format("/api/players/%s/weapons/%s", player.getId(), weapon.getId());
        ResponseEntity<WeaponDto> responseEntity = testRestTemplate.getForEntity(url, WeaponDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(weapon.getId(), responseEntity.getBody().getId());
        assertEquals(weapon.getName(), responseEntity.getBody().getName());
    }

    @Test
    void testAddWeapon() {
        Player player = getDefaultPlayer();
        player = playerRepository.save(player);

        CreateWeaponDto createWeaponDto = getDefaultCreateWeaponDto();

        String url = String.format("/api/players/%s/weapons", player.getId());
        ResponseEntity<WeaponDto> responseEntity = testRestTemplate.postForEntity(url, createWeaponDto, WeaponDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createWeaponDto.getName(), responseEntity.getBody().getName());

        Optional<Weapon> optionalWeapon = weaponRepository.findById(new WeaponPk(responseEntity.getBody().getId(), player.getId()));
        assertTrue(optionalWeapon.isPresent());
        assertEquals(player.getId(), optionalWeapon.get().getPlayer().getId());

        Optional<Player> optionalPlayer = playerRepository.findById(player.getId());
        assertTrue(optionalPlayer.isPresent());
        assertEquals(1, optionalPlayer.get().getWeapons().size());
        assertTrue(optionalPlayer.get().getWeapons().contains(optionalWeapon.get()));
    }

    @Test
    void testRemoveWeapon() {
        Player player = getDefaultPlayer();
        player = playerRepository.save(player);

        Weapon weapon = getDefaultWeapon();
        weapon.setPlayer(player);
        weapon = weaponRepository.save(weapon);

        String url = String.format("/api/players/%s/weapons/%s", player.getId(), weapon.getId());
        ResponseEntity<WeaponDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, WeaponDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(weapon.getId(), responseEntity.getBody().getId());
        assertEquals(weapon.getName(), responseEntity.getBody().getName());

        Optional<Weapon> optionalWeapon = weaponRepository.findById(new WeaponPk(player.getId(), weapon.getId()));
        assertFalse(optionalWeapon.isPresent());

        Optional<Player> optionalPlayer = playerRepository.findById(player.getId());
        assertTrue(optionalPlayer.isPresent());
        assertEquals(0, optionalPlayer.get().getWeapons().size());
        assertFalse(optionalPlayer.get().getWeapons().contains(weapon));
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

}