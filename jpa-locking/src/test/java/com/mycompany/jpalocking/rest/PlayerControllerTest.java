package com.mycompany.jpalocking.rest;

import com.mycompany.jpalocking.ContainersExtension;
import com.mycompany.jpalocking.rest.dto.CreatePlayerDto;
import com.mycompany.jpalocking.rest.dto.GameDto;
import com.mycompany.jpalocking.rest.dto.GameSetupDto;
import com.mycompany.jpalocking.rest.dto.PlayerDto;
import com.mycompany.jpalocking.rest.dto.StarCollectionDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ActiveProfiles("mysql-test")
@ExtendWith({SpringExtension.class, ContainersExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PlayerControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void testStarsRedemption() throws InterruptedException {
        setupGame(5);

        Long player1 = createPlayer("player1").getId();
        Long player2 = createPlayer("player2").getId();
        Long player3 = createPlayer("player3").getId();

        Thread t1 = new Thread(() -> collectStars(player1, 30));
        Thread t2 = new Thread(() -> collectStars(player1, 20));
        Thread t3 = new Thread(() -> collectStars(player2, 40));
        Thread t4 = new Thread(() -> collectStars(player2, 10));
        Thread t5 = new Thread(() -> collectStars(player3, 60));

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();

        Thread t6 = new Thread(() -> redeemStars(player1));
        Thread t7 = new Thread(() -> redeemStars(player1));

        Thread t8 = new Thread(() -> redeemStars(player2));
        Thread t9 = new Thread(() -> redeemStars(player2));

        Thread t10 = new Thread(() -> redeemStars(player3));

        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();

        t6.join();
        t7.join();
        t8.join();
        t9.join();
        t10.join();

        PlayerDto playerDto1 = getPlayerInfo(player1);
        PlayerDto playerDto2 = getPlayerInfo(player2);
        PlayerDto playerDto3 = getPlayerInfo(player3);
        GameDto gameInfo = getGameInfo();

        log.info("playerDto1 = {}", playerDto1);
        log.info("playerDto2 = {}", playerDto2);
        log.info("playerDto3 = {}", playerDto3);
        log.info("getGameInfo = {}", getGameInfo());

        assertEquals(0, playerDto1.getNumStars());
        assertEquals(1, playerDto1.getLives().size());

        assertEquals(0, playerDto2.getNumStars());
        assertEquals(1, playerDto2.getLives().size());

        assertEquals(10, playerDto3.getNumStars());
        assertEquals(1, playerDto3.getLives().size());

        assertEquals(2, gameInfo.getAvailableLives());
        assertEquals(3, gameInfo.getLives().stream().filter(lifeDto -> lifeDto.getUsername() != null).count());
        assertEquals(2, gameInfo.getLives().stream().filter(lifeDto -> lifeDto.getUsername() == null).count());
    }

    private void setupGame(int numLives) {
        GameSetupDto gameSetupDto = getGameSetupDto(numLives);
        testRestTemplate.postForEntity("/api/games", gameSetupDto, GameDto.class);
    }

    private GameDto getGameInfo() {
        ResponseEntity<GameDto> responseEntity = testRestTemplate.getForEntity("/api/games", GameDto.class);
        return responseEntity.getBody();
    }

    private PlayerDto createPlayer(String username) {
        CreatePlayerDto createPlayerDto = getCreatePlayerDto(username);
        ResponseEntity<PlayerDto> responseEntity = testRestTemplate.postForEntity("/api/players", createPlayerDto, PlayerDto.class);
        return responseEntity.getBody();
    }

    private void collectStars(Long playerId, int numStars) {
        StarCollectionDto collectStarDto = getCollectStarDto(numStars);
        String url = String.format("/api/players/%s/stars", playerId);
        testRestTemplate.postForEntity(url, collectStarDto, PlayerDto.class);
    }

    private void redeemStars(Long playerId) {
        String url = String.format("/api/players/%s/lives", playerId);
        testRestTemplate.postForEntity(url, null, PlayerDto.class);
    }

    private PlayerDto getPlayerInfo(Long playerId) {
        String url = String.format("/api/players/%s", playerId);
        ResponseEntity<PlayerDto> responseEntity = testRestTemplate.getForEntity(url, PlayerDto.class);
        return responseEntity.getBody();
    }

    private GameSetupDto getGameSetupDto(int numLives) {
        GameSetupDto gameSetupDto = new GameSetupDto();
        gameSetupDto.setNumLives(numLives);
        return gameSetupDto;
    }

    private CreatePlayerDto getCreatePlayerDto(String username) {
        CreatePlayerDto createPlayerDto = new CreatePlayerDto();
        createPlayerDto.setUsername(username);
        return createPlayerDto;
    }

    private StarCollectionDto getCollectStarDto(int numStars) {
        StarCollectionDto starCollectionDto = new StarCollectionDto();
        starCollectionDto.setNumStars(numStars);
        return starCollectionDto;
    }

}