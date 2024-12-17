package com.ivanfranchin.jpalocking.rest;

import com.ivanfranchin.jpalocking.AbstractTestcontainers;
import com.ivanfranchin.jpalocking.rest.dto.CreatePlayerRequest;
import com.ivanfranchin.jpalocking.rest.dto.GameResponse;
import com.ivanfranchin.jpalocking.rest.dto.GameSetupRequest;
import com.ivanfranchin.jpalocking.rest.dto.PlayerResponse;
import com.ivanfranchin.jpalocking.rest.dto.StarCollectionRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlayerControllerTest extends AbstractTestcontainers {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void testStarsRedemption() throws InterruptedException {
        setupGame(5);

        Long player1 = createPlayer("player1").id();
        Long player2 = createPlayer("player2").id();
        Long player3 = createPlayer("player3").id();

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

        PlayerResponse playerResponse1 = getPlayerInfo(player1);
        PlayerResponse playerResponse2 = getPlayerInfo(player2);
        PlayerResponse playerResponse3 = getPlayerInfo(player3);
        GameResponse gameInfo = getGameInfo();

        log.info("playerResponse1 = {}", playerResponse1);
        log.info("playerResponse2 = {}", playerResponse2);
        log.info("playerResponse3 = {}", playerResponse3);
        log.info("getGameInfo = {}", gameInfo);

        assertThat(playerResponse1.numStars()).isEqualTo(0);
        assertThat(playerResponse1.lives().size()).isEqualTo(1);

        assertThat(playerResponse2.numStars()).isEqualTo(0);
        assertThat(playerResponse2.lives().size()).isEqualTo(1);

        assertThat(playerResponse3.numStars()).isEqualTo(10);
        assertThat(playerResponse3.lives().size()).isEqualTo(1);

        assertThat(gameInfo.availableLives()).isEqualTo(2);
        assertThat(gameInfo.lives().stream().filter(lifeResponse -> lifeResponse.username() != null).count())
                .isEqualTo(3);
        assertThat(gameInfo.lives().stream().filter(lifeResponse -> lifeResponse.username() == null).count())
                .isEqualTo(2);
    }

    private void setupGame(int numLives) {
        GameSetupRequest gameSetupRequest = new GameSetupRequest(numLives);
        testRestTemplate.postForEntity(API_GAMES_URL, gameSetupRequest, GameResponse.class);
    }

    private GameResponse getGameInfo() {
        ResponseEntity<GameResponse> responseEntity = testRestTemplate.getForEntity(API_GAMES_URL, GameResponse.class);
        return responseEntity.getBody();
    }

    private PlayerResponse createPlayer(String username) {
        CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest(username);
        ResponseEntity<PlayerResponse> responseEntity = testRestTemplate.postForEntity(
                API_PLAYERS_URL, createPlayerRequest, PlayerResponse.class);
        return responseEntity.getBody();
    }

    private void collectStars(Long playerId, int numStars) {
        StarCollectionRequest collectStarResponse = new StarCollectionRequest(numStars);
        String url = String.format(API_PLAYERS_ID_STARS_URL, playerId);
        testRestTemplate.postForEntity(url, collectStarResponse, PlayerResponse.class);
    }

    private void redeemStars(Long playerId) {
        String url = String.format(API_PLAYERS_ID_LIVES_URL, playerId);
        testRestTemplate.postForEntity(url, null, PlayerResponse.class);
    }

    private PlayerResponse getPlayerInfo(Long playerId) {
        String url = String.format(API_PLAYERS_ID_URL, playerId);
        ResponseEntity<PlayerResponse> responseEntity = testRestTemplate.getForEntity(url, PlayerResponse.class);
        return responseEntity.getBody();
    }

    private static final String API_GAMES_URL = "/api/games";
    private static final String API_PLAYERS_URL = "/api/players";
    private static final String API_PLAYERS_ID_URL = "/api/players/%s";
    private static final String API_PLAYERS_ID_STARS_URL = "/api/players/%s/stars";
    private static final String API_PLAYERS_ID_LIVES_URL = "/api/players/%s/lives";
}