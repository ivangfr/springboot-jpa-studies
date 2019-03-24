package com.mycompany.jpalocking.rest;

import com.mycompany.jpalocking.exception.RedeemRaceConditionException;
import com.mycompany.jpalocking.model.Player;
import com.mycompany.jpalocking.rest.dto.CreatePlayerDto;
import com.mycompany.jpalocking.rest.dto.PlayerDto;
import com.mycompany.jpalocking.rest.dto.StarCollectionDto;
import com.mycompany.jpalocking.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;
    private final MapperFacade mapperFacade;

    public PlayerController(PlayerService playerService, MapperFacade mapperFacade) {
        this.playerService = playerService;
        this.mapperFacade = mapperFacade;
    }

    @PostMapping
    public PlayerDto createPlayer(@Valid @RequestBody CreatePlayerDto createPlayerDto) {
        Player player = playerService.savePlayer(mapperFacade.map(createPlayerDto, Player.class));
        return mapperFacade.map(player, PlayerDto.class);
    }

    @GetMapping("/{id}")
    public PlayerDto getPlayer(@PathVariable Long id) {
        Player player = playerService.validateAndGetPlayer(id);
        return mapperFacade.map(player, PlayerDto.class);
    }

    @PostMapping("/{id}/stars")
    public PlayerDto playerCollectStars(@PathVariable Long id, @Valid @RequestBody StarCollectionDto starCollectionDto) {
        log.info("==> Player {} collects stars", id);
        Player player = playerService.collectStars(id, starCollectionDto.getNumStars());
        log.info("<== Player {} collected stars", id);
        return mapperFacade.map(player, PlayerDto.class);
    }

    @PostMapping("/{id}/lives")
    public PlayerDto playerRedeemStars(@PathVariable Long id) {
        try {
            log.info("==> Player {} redeems stars", id);
            Player player = playerService.redeemStars(id);
            log.info("<== Player {} redeemed stars", id);
            return mapperFacade.map(player, PlayerDto.class);

        } catch (ObjectOptimisticLockingFailureException e) {
            log.error("An problem occurred while player {} redeems life. Error class: {}, error message: {}", id, e.getClass().getName(), e.getMessage());
            throw new RedeemRaceConditionException(
                    String.format("Two or more threads of player %s tried to redeem stars at the same time", id), e);
        }
    }

}
