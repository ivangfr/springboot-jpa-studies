package com.ivanfranchin.jpalocking.rest;

import com.ivanfranchin.jpalocking.star.RedeemRaceConditionException;
import com.ivanfranchin.jpalocking.player.Player;
import com.ivanfranchin.jpalocking.rest.dto.CreatePlayerRequest;
import com.ivanfranchin.jpalocking.rest.dto.PlayerResponse;
import com.ivanfranchin.jpalocking.rest.dto.StarCollectionRequest;
import com.ivanfranchin.jpalocking.player.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping
    public PlayerResponse createPlayer(@Valid @RequestBody CreatePlayerRequest createPlayerRequest) {
        Player player = playerService.savePlayer(Player.from(createPlayerRequest));
        return PlayerResponse.from(player);
    }

    @GetMapping("/{id}")
    public PlayerResponse getPlayer(@PathVariable Long id) {
        Player player = playerService.validateAndGetPlayer(id);
        return PlayerResponse.from(player);
    }

    @PostMapping("/{id}/stars")
    public PlayerResponse playerCollectStars(@PathVariable Long id,
                                             @Valid @RequestBody StarCollectionRequest starCollectionRequest) {
        Player player = playerService.validateAndGetPlayer(id);
        log.info("==> Player {} collects stars. Number of Stars: {}, Number of Lives: {}", id, player.getStars(), player.getLives());
        player = playerService.collectStars(player, starCollectionRequest.numStars());
        log.info("<== Player {} collected stars. Number of Stars: {}, Number of Lives: {}", id, player.getStars(), player.getLives());
        return PlayerResponse.from(player);
    }

    @PostMapping("/{id}/lives")
    public PlayerResponse playerRedeemStars(@PathVariable Long id) {
        Player player = playerService.validateAndGetPlayer(id);
        try {
            log.info("==> Player {} redeems stars. Number of Stars: {}, Number of Lives: {}", id, player.getStars(), player.getLives());
            player = playerService.redeemStars(player);
            log.info("<== Player {} redeemed stars. Number of Stars: {}, Number of Lives: {}", id, player.getStars(), player.getLives());
            return PlayerResponse.from(player);

        } catch (ObjectOptimisticLockingFailureException e) {
            log.error("An problem occurred while player {} redeems stars. Error class: {}, error message: {}", id, e.getClass().getName(), e.getMessage());
            throw new RedeemRaceConditionException(id, e);
        }
    }
}
