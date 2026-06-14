package com.ivanfranchin.jpalocking.rest;

import static org.assertj.core.api.Assertions.assertThat;

import com.ivanfranchin.jpalocking.AbstractTestcontainers;
import com.ivanfranchin.jpalocking.rest.dto.CreatePlayerRequest;
import com.ivanfranchin.jpalocking.rest.dto.GameResponse;
import com.ivanfranchin.jpalocking.rest.dto.GameSetupRequest;
import com.ivanfranchin.jpalocking.rest.dto.PlayerResponse;
import com.ivanfranchin.jpalocking.rest.dto.StarCollectionRequest;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

@Slf4j
@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlayerControllerTest extends AbstractTestcontainers {

  @Autowired private TestRestTemplate testRestTemplate;

  @Test
  void testStarsRedemption() throws InterruptedException {
    setupGame(5);

    Long player1 = createPlayer("player1").id();
    Long player2 = createPlayer("player2").id();
    Long player3 = createPlayer("player3").id();

    // --- Concurrent star collection ---
    AtomicInteger collectErrors = new AtomicInteger(0);
    CyclicBarrier collectBarrier = new CyclicBarrier(5);
    List<Callable<Void>> collectTasks =
        List.of(
            collectStarsTask(collectBarrier, player1, 30, collectErrors),
            collectStarsTask(collectBarrier, player1, 20, collectErrors),
            collectStarsTask(collectBarrier, player2, 40, collectErrors),
            collectStarsTask(collectBarrier, player2, 10, collectErrors),
            collectStarsTask(collectBarrier, player3, 60, collectErrors));

    ExecutorService executor = Executors.newFixedThreadPool(5);
    executor.invokeAll(collectTasks);
    executor.shutdown();

    assertThat(collectErrors).hasValue(0);

    // Verify intermediate state after collection
    PlayerResponse player1AfterCollect = getPlayerInfo(player1);
    PlayerResponse player2AfterCollect = getPlayerInfo(player2);
    PlayerResponse player3AfterCollect = getPlayerInfo(player3);

    assertThat(player1AfterCollect.numStars()).isEqualTo(50);
    assertThat(player2AfterCollect.numStars()).isEqualTo(50);
    assertThat(player3AfterCollect.numStars()).isEqualTo(60);

    // --- Concurrent star redemption ---
    AtomicInteger player1Errors = new AtomicInteger(0);
    AtomicInteger player2Errors = new AtomicInteger(0);
    AtomicInteger player3Errors = new AtomicInteger(0);
    CyclicBarrier redeemBarrier = new CyclicBarrier(5);
    List<Callable<Void>> redeemTasks =
        List.of(
            redeemStarsTask(redeemBarrier, player1, player1Errors),
            redeemStarsTask(redeemBarrier, player1, player1Errors),
            redeemStarsTask(redeemBarrier, player2, player2Errors),
            redeemStarsTask(redeemBarrier, player2, player2Errors),
            redeemStarsTask(redeemBarrier, player3, player3Errors));

    executor = Executors.newFixedThreadPool(5);
    executor.invokeAll(redeemTasks);
    executor.shutdown();

    // Exactly one redemption per player1/player2 should fail with 409
    assertThat(player1Errors).hasValue(1);
    assertThat(player2Errors).hasValue(1);
    assertThat(player3Errors).hasValue(0);

    PlayerResponse playerResponse1 = getPlayerInfo(player1);
    PlayerResponse playerResponse2 = getPlayerInfo(player2);
    PlayerResponse playerResponse3 = getPlayerInfo(player3);
    GameResponse gameInfo = getGameInfo();

    log.info("playerResponse1 = {}", playerResponse1);
    log.info("playerResponse2 = {}", playerResponse2);
    log.info("playerResponse3 = {}", playerResponse3);
    log.info("getGameInfo = {}", gameInfo);

    assertThat(playerResponse1.numStars()).isEqualTo(0);
    assertThat(playerResponse1.lives()).hasSize(1);

    assertThat(playerResponse2.numStars()).isEqualTo(0);
    assertThat(playerResponse2.lives()).hasSize(1);

    assertThat(playerResponse3.numStars()).isEqualTo(10);
    assertThat(playerResponse3.lives()).hasSize(1);

    assertThat(gameInfo.availableLives()).isEqualTo(2);
    assertThat(
            gameInfo.lives().stream()
                .filter(lifeResponse -> lifeResponse.username() != null)
                .count())
        .isEqualTo(3);
    assertThat(
            gameInfo.lives().stream()
                .filter(lifeResponse -> lifeResponse.username() == null)
                .count())
        .isEqualTo(2);
  }

  private Callable<Void> collectStarsTask(
      CyclicBarrier barrier, Long playerId, int numStars, AtomicInteger errorCounter) {
    return () -> {
      try {
        barrier.await();
        StarCollectionRequest request = new StarCollectionRequest(numStars);
        String url = String.format(API_PLAYERS_ID_STARS_URL, playerId);
        testRestTemplate.postForEntity(url, request, PlayerResponse.class);
      } catch (RestClientException e) {
        log.warn("collectStars failed for player {}: {}", playerId, e.getMessage());
        errorCounter.incrementAndGet();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException(e);
      } catch (java.util.concurrent.BrokenBarrierException e) {
        throw new RuntimeException(e);
      }
      return null;
    };
  }

  private Callable<Void> redeemStarsTask(
      CyclicBarrier barrier, Long playerId, AtomicInteger errorCounter) {
    return () -> {
      try {
        barrier.await();
        String url = String.format(API_PLAYERS_ID_LIVES_URL, playerId);
        testRestTemplate.postForEntity(url, null, PlayerResponse.class);
      } catch (RestClientException e) {
        log.warn("redeemStars failed for player {}: {}", playerId, e.getMessage());
        errorCounter.incrementAndGet();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException(e);
      } catch (java.util.concurrent.BrokenBarrierException e) {
        throw new RuntimeException(e);
      }
      return null;
    };
  }

  private void setupGame(int numLives) {
    GameSetupRequest gameSetupRequest = new GameSetupRequest(numLives);
    testRestTemplate.postForEntity(API_GAMES_URL, gameSetupRequest, GameResponse.class);
  }

  private GameResponse getGameInfo() {
    ResponseEntity<GameResponse> responseEntity =
        testRestTemplate.getForEntity(API_GAMES_URL, GameResponse.class);
    return responseEntity.getBody();
  }

  private PlayerResponse createPlayer(String username) {
    CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest(username);
    ResponseEntity<PlayerResponse> responseEntity =
        testRestTemplate.postForEntity(API_PLAYERS_URL, createPlayerRequest, PlayerResponse.class);
    return responseEntity.getBody();
  }

  private PlayerResponse getPlayerInfo(Long playerId) {
    String url = String.format(API_PLAYERS_ID_URL, playerId);
    ResponseEntity<PlayerResponse> responseEntity =
        testRestTemplate.getForEntity(url, PlayerResponse.class);
    return responseEntity.getBody();
  }

  private static final String API_GAMES_URL = "/api/games";
  private static final String API_PLAYERS_URL = "/api/players";
  private static final String API_PLAYERS_ID_URL = "/api/players/%s";
  private static final String API_PLAYERS_ID_STARS_URL = "/api/players/%s/stars";
  private static final String API_PLAYERS_ID_LIVES_URL = "/api/players/%s/lives";
}
