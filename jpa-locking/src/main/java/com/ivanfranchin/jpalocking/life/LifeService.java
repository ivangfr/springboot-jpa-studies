package com.ivanfranchin.jpalocking.life;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LifeService {

  private final LifeRepository lifeRepository;

  public List<Life> getAllLives() {
    return lifeRepository.findAll();
  }

  public int countAvailableLives() {
    return lifeRepository.countByPlayerIdIsNull();
  }

  public Life saveLife(Life life) {
    return lifeRepository.save(life);
  }

  public Life getAvailableLife() {
    return lifeRepository.findFirstByPlayerIdIsNull().orElseThrow(AllLivesRedeemedException::new);
  }
}
