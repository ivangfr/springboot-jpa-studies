package com.ivanfranchin.jpalocking.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ivanfranchin.jpalocking.life.Life;
import com.ivanfranchin.jpalocking.life.LifeService;
import com.ivanfranchin.jpalocking.star.InsufficientStarsException;
import com.ivanfranchin.jpalocking.star.StarCollection;
import com.ivanfranchin.jpalocking.star.StarCollectionService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(PlayerService.class)
class PlayerServiceTest {

  @MockitoBean private PlayerRepository playerRepository;

  @MockitoBean private StarCollectionService starCollectionService;

  @MockitoBean private LifeService lifeService;

  @Autowired private PlayerService playerService;

  @Test
  void validateAndGetPlayerWhenFoundShouldReturnPlayer() {
    Player player = new Player();
    player.setId(1L);
    when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

    Player result = playerService.validateAndGetPlayer(1L);

    assertThat(result).isEqualTo(player);
  }

  @Test
  void validateAndGetPlayerWhenNotFoundShouldThrow() {
    when(playerRepository.findById(1L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> playerService.validateAndGetPlayer(1L))
        .isInstanceOf(PlayerNotFoundException.class)
        .hasMessage("Player with id 1 not found.");
  }

  @Test
  void savePlayerShouldDelegateToRepository() {
    Player player = new Player();
    player.setUsername("test");
    when(playerRepository.save(player)).thenReturn(player);

    Player result = playerService.savePlayer(player);

    assertThat(result).isEqualTo(player);
    verify(playerRepository).save(player);
  }

  @Test
  void collectStarsShouldAddStarCollectionAndSave() {
    Player player = new Player();
    player.setId(1L);
    when(playerRepository.save(player)).thenReturn(player);

    Player result = playerService.collectStars(player, 30);

    assertThat(result.getStars()).hasSize(1);
    StarCollection sc = result.getStars().iterator().next();
    assertThat(sc.getNumCollected()).isEqualTo(30);
    assertThat(sc.getNumAvailable()).isEqualTo(30);
    verify(playerRepository).save(player);
  }

  @Test
  void redeemStarsWhenInsufficientStarsShouldThrow() {
    Player player = new Player();
    player.setId(1L);
    when(starCollectionService.getAvailableStarCollections(player)).thenReturn(List.of());

    assertThatThrownBy(() -> playerService.redeemStars(player))
        .isInstanceOf(InsufficientStarsException.class);
  }

  @Test
  void redeemStarsShouldDeductStarsAndAddLife() {
    Player player = new Player();
    player.setId(1L);
    StarCollection sc = new StarCollection();
    sc.setNumCollected(60);
    sc.setNumAvailable(60);
    Life life = new Life();

    when(starCollectionService.getAvailableStarCollections(player)).thenReturn(List.of(sc));
    when(lifeService.getAvailableLife()).thenReturn(life);
    when(playerRepository.save(player)).thenReturn(player);

    Player result = playerService.redeemStars(player);

    assertThat(sc.getNumAvailable()).isEqualTo(10);
    assertThat(result.getLives()).contains(life);
    verify(playerRepository).save(player);
  }

  @Test
  void getAllPlayersShouldReturnAllPlayers() {
    List<Player> players = List.of(new Player(), new Player());
    when(playerRepository.findAll()).thenReturn(players);

    List<Player> result = playerService.getAllPlayers();

    assertThat(result).hasSize(2);
    verify(playerRepository).findAll();
  }
}
