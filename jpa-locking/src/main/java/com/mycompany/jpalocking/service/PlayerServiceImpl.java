package com.mycompany.jpalocking.service;

import com.mycompany.jpalocking.exception.InsufficientStarsException;
import com.mycompany.jpalocking.exception.PlayerNotFoundException;
import com.mycompany.jpalocking.model.Life;
import com.mycompany.jpalocking.model.Player;
import com.mycompany.jpalocking.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class PlayerServiceImpl implements PlayerService {

    private static final Integer NUM_STARS_REDEEM_LIFE = 50;

    private final PlayerRepository playerRepository;
    private final LifeService lifeService;

    public PlayerServiceImpl(PlayerRepository playerRepository, LifeService lifeService) {
        this.playerRepository = playerRepository;
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

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Player addStars(Long id, int numStars) {
        Player player = validateAndGetPlayer(id);
        player.setNumStars(player.getNumStars() + numStars);
        return savePlayer(player);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Player redeemStars(Long id) {
        Player player = validateAndGetPlayer(id);

        int newNumStars = player.getNumStars() - NUM_STARS_REDEEM_LIFE;
        log.info("Number of start {} -> {} ", player.getNumStars(), newNumStars);
        if (newNumStars < 0) {
            throw new InsufficientStarsException(String.format("Player %s has insufficient stars to redeem", player.getId()));
        }

        Life life = lifeService.getAvailableLife();
        log.info("Available life selected is {}", life.getId());

        player.addLife(life);
        player.setNumStars(newNumStars);
        return savePlayer(player);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

}
