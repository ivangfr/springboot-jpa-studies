package com.ivanfranchin.jpalocking.life;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(LifeService.class)
class LifeServiceTest {

  @MockitoBean private LifeRepository lifeRepository;

  @Autowired private LifeService lifeService;

  @Test
  void getAllLivesShouldReturnAllLives() {
    List<Life> lives = List.of(new Life(), new Life());
    when(lifeRepository.findAll()).thenReturn(lives);

    List<Life> result = lifeService.getAllLives();

    assertThat(result).hasSize(2);
    verify(lifeRepository).findAll();
  }

  @Test
  void countAvailableLivesShouldReturnCount() {
    when(lifeRepository.countByPlayerIdIsNull()).thenReturn(3);

    int result = lifeService.countAvailableLives();

    assertThat(result).isEqualTo(3);
  }

  @Test
  void saveLifeShouldDelegateToRepository() {
    Life life = new Life();
    when(lifeRepository.save(life)).thenReturn(life);

    Life result = lifeService.saveLife(life);

    assertThat(result).isEqualTo(life);
    verify(lifeRepository).save(life);
  }

  @Test
  void getAvailableLifeWhenFoundShouldReturnLife() {
    Life life = new Life();
    when(lifeRepository.findFirstByPlayerIdIsNull()).thenReturn(Optional.of(life));

    Life result = lifeService.getAvailableLife();

    assertThat(result).isEqualTo(life);
  }

  @Test
  void getAvailableLifeWhenNotFoundShouldThrow() {
    when(lifeRepository.findFirstByPlayerIdIsNull()).thenReturn(Optional.empty());

    assertThatThrownBy(() -> lifeService.getAvailableLife())
        .isInstanceOf(AllLivesRedeemedException.class);
  }
}
