package com.mycompany.jpalocking.rest;

import com.mycompany.jpalocking.exception.RedeemRaceConditionException;
import com.mycompany.jpalocking.mapper.PlayerMapper;
import com.mycompany.jpalocking.model.Player;
import com.mycompany.jpalocking.rest.dto.CreatePlayerRequest;
import com.mycompany.jpalocking.rest.dto.PlayerResponse;
import com.mycompany.jpalocking.rest.dto.StarCollectionRequest;
import com.mycompany.jpalocking.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    @PostMapping
    public PlayerResponse createPlayer(@Valid @RequestBody CreatePlayerRequest createPlayerRequest) {
        Player player = playerService.savePlayer(playerMapper.toPlayer(createPlayerRequest));
        return playerMapper.toPlayerResponse(player);
    }

    @GetMapping("/{id}")
    public PlayerResponse getPlayer(@PathVariable Long id) {
        Player player = playerService.validateAndGetPlayer(id);
        return playerMapper.toPlayerResponse(player);
    }

    @PostMapping("/{id}/stars")
    public PlayerResponse playerCollectStars(@PathVariable Long id,
                                             @Valid @RequestBody StarCollectionRequest starCollectionRequest) {
        Player player = playerService.validateAndGetPlayer(id);
        log.info("==> Player {} collects stars. Number of Stars: {}, Number of Lives: {}", id, player.getStars(), player.getLives());
        player = playerService.collectStars(player, starCollectionRequest.getNumStars());
        log.info("<== Player {} collected stars. Number of Stars: {}, Number of Lives: {}", id, player.getStars(), player.getLives());
        return playerMapper.toPlayerResponse(player);
    }

    @PostMapping("/{id}/lives")
    public PlayerResponse playerRedeemStars(@PathVariable Long id) {
        Player player = playerService.validateAndGetPlayer(id);
        try {
            log.info("==> Player {} redeems stars. Number of Stars: {}, Number of Lives: {}", id, player.getStars(), player.getLives());
            player = playerService.redeemStars(player);
            log.info("<== Player {} redeemed stars. Number of Stars: {}, Number of Lives: {}", id, player.getStars(), player.getLives());
            return playerMapper.toPlayerResponse(player);

        } catch (ObjectOptimisticLockingFailureException e) {
            log.error("An problem occurred while player {} redeems life. Error class: {}, error message: {}", id, e.getClass().getName(), e.getMessage());
            throw new RedeemRaceConditionException(id, e);
        }
    }
}
