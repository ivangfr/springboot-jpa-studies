package com.mycompany.jpalocking.service;

import com.mycompany.jpalocking.exception.InsufficientStarsException;
import com.mycompany.jpalocking.exception.PlayerNotFoundException;
import com.mycompany.jpalocking.model.Life;
import com.mycompany.jpalocking.model.Player;
import com.mycompany.jpalocking.model.StarCollection;
import com.mycompany.jpalocking.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlayerServiceImpl implements PlayerService {

    private static final Integer NUM_STARS_REDEEM_LIFE = 50;

    private final PlayerRepository playerRepository;
    private final StarCollectionService starCollectionService;
    private final LifeService lifeService;

    public PlayerServiceImpl(PlayerRepository playerRepository, StarCollectionService starCollectionService, LifeService lifeService) {
        this.playerRepository = playerRepository;
        this.starCollectionService = starCollectionService;
        this.lifeService = lifeService;
    }

    @Override
    public Player validateAndGetPlayer(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(String.format("Player with id %s not found.", id)));
    }

    @Override
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    @Transactional
    @Override
    public Player collectStars(Long id, int numStars) {
        Player player = validateAndGetPlayer(id);

        StarCollection starCollection = new StarCollection();
        starCollection.setNumCollected(numStars);
        starCollection.setNumAvailable(numStars);

        player.addStarCollection(starCollection);
        return savePlayer(player);
    }

    @Transactional
    @Override
    public Player redeemStars(Long id) {
        Player player = validateAndGetPlayer(id);

        List<StarCollection> starCollections = starCollectionService.getAvailableStarCollections(player);
        int numStars = starCollections.stream().mapToInt(StarCollection::getNumAvailable).sum();
        if (numStars - NUM_STARS_REDEEM_LIFE < 0) {
            throw new InsufficientStarsException(String.format("Player %s has insufficient stars to redeem", player.getId()));
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

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

}