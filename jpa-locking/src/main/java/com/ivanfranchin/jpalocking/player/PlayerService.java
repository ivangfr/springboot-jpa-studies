package com.ivanfranchin.jpalocking.player;

import com.ivanfranchin.jpalocking.life.Life;
import com.ivanfranchin.jpalocking.life.LifeService;
import com.ivanfranchin.jpalocking.star.InsufficientStarsException;
import com.ivanfranchin.jpalocking.star.StarCollection;
import com.ivanfranchin.jpalocking.star.StarCollectionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlayerService {

  private static final int NUM_STARS_REDEEM_LIFE = 50;

  private final PlayerRepository playerRepository;
  private final StarCollectionService starCollectionService;
  private final LifeService lifeService;

  public Player validateAndGetPlayer(Long id) {
    return playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
  }

  public Player savePlayer(Player player) {
    return playerRepository.save(player);
  }

  @Transactional
  public Player collectStars(Player player, int numStars) {
    StarCollection starCollection = new StarCollection();
    starCollection.setNumCollected(numStars);
    starCollection.setNumAvailable(numStars);

    player.addStarCollection(starCollection);
    return savePlayer(player);
  }

  @Transactional
  public Player redeemStars(Player player) {
    List<StarCollection> starCollections =
        starCollectionService.getAvailableStarCollections(player);
    int numStars = starCollections.stream().mapToInt(StarCollection::getNumAvailable).sum();
    if (numStars - NUM_STARS_REDEEM_LIFE < 0) {
      throw new InsufficientStarsException(player.getId());
    }

    Life life = lifeService.getAvailableLife();
    log.info("Available life selected is {}", life.getId());

    player.addLife(life);

    int numRedeemStars = NUM_STARS_REDEEM_LIFE;
    for (StarCollection starCollection : starCollections) {
      numRedeemStars -= starCollection.getNumAvailable();
      if (numRedeemStars > 0) {
        starCollection.setNumAvailable(0);
      } else {
        starCollection.setNumAvailable(Math.abs(numRedeemStars));
        break;
      }
    }
    return savePlayer(player);
  }

  public List<Player> getAllPlayers() {
    return playerRepository.findAll();
  }
}
