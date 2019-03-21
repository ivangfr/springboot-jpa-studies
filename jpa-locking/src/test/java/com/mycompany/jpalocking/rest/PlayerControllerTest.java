package com.mycompany.jpalocking.rest;

import com.mycompany.jpalocking.rest.dto.AddStarDto;
import com.mycompany.jpalocking.rest.dto.CreatePlayerDto;
import com.mycompany.jpalocking.rest.dto.GameDto;
import com.mycompany.jpalocking.rest.dto.GameSetupDto;
import com.mycompany.jpalocking.rest.dto.PlayerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("mysql-test")
@ExtendWith({SpringExtension.class, ContainersExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PlayerControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void test() throws InterruptedException {
        setupGame(10);

        Long player1 = createPlayer("player1", 100).getId();
        Long player2 = createPlayer("player2", 110).getId();

        Thread t1 = new Thread(() -> addStars(player1, 10));
        Thread t2 = new Thread(() -> addStars(player1, 20));
        Thread t3 = new Thread(() -> addStars(player2, 10));
        Thread t4 = new Thread(() -> addStars(player2, 10));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        Thread t5 = new Thread(() -> redeemStars(player1));
        Thread t6 = new Thread(() -> redeemStars(player1));
        Thread t7 = new Thread(() -> redeemStars(player1));
        Thread t8 = new Thread(() -> redeemStars(player1));

        Thread t9 = new Thread(() -> redeemStars(player2));
        Thread t10 = new Thread(() -> redeemStars(player2));
        Thread t11 = new Thread(() -> redeemStars(player2));

        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
        t11.start();

        t5.join();
        t6.join();
        t7.join();
        t8.join();
        t9.join();
        t10.join();
        t11.join();

        PlayerDto playerDto1 = getPlayerInfo(player1);
        PlayerDto playerDto2 = getPlayerInfo(player2);

        System.out.println(playerDto1);
        System.out.println(playerDto2);
        System.out.println(getGameInfo());
    }

    private void setupGame(int numLives) {
        GameSetupDto gameSetupDto = getGameSetupDto(numLives);
        testRestTemplate.postForEntity("/api/games", gameSetupDto, GameDto.class);
    }

    private GameDto getGameInfo() {
        ResponseEntity<GameDto> responseEntity = testRestTemplate.getForEntity("/api/games", GameDto.class);
        return responseEntity.getBody();
    }

    private PlayerDto createPlayer(String username, int numStars) {
        CreatePlayerDto createPlayerDto = getCreatePlayerDto(username, numStars);
        ResponseEntity<PlayerDto> responseEntity = testRestTemplate.postForEntity("/api/players", createPlayerDto, PlayerDto.class);
        return responseEntity.getBody();
    }

    private void addStars(Long playerId, int numStars) {
        AddStarDto addStarDto = getAddStarDto(numStars);
        String url = String.format("/api/players/%s/stars", playerId);
        testRestTemplate.postForEntity(url, addStarDto, PlayerDto.class);
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

    private CreatePlayerDto getCreatePlayerDto(String username, int numStars) {
        CreatePlayerDto createPlayerDto = new CreatePlayerDto();
        createPlayerDto.setUsername(username);
        createPlayerDto.setNumStars(numStars);
        return createPlayerDto;
    }

    private AddStarDto getAddStarDto(int numStars) {
        AddStarDto collectStarDto = new AddStarDto();
        collectStarDto.setNumStars(numStars);
        return collectStarDto;
    }

}