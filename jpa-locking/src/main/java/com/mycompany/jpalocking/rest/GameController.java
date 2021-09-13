package com.mycompany.jpalocking.rest;

import com.mycompany.jpalocking.mapper.LifeMapper;
import com.mycompany.jpalocking.model.Life;
import com.mycompany.jpalocking.model.Player;
import com.mycompany.jpalocking.rest.dto.GameResponse;
import com.mycompany.jpalocking.rest.dto.GameSetupRequest;
import com.mycompany.jpalocking.service.LifeService;
import com.mycompany.jpalocking.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final LifeService lifeService;
    private final PlayerService playerService;
    private final LifeMapper lifeMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GameResponse setupGame(@Valid @RequestBody GameSetupRequest gameSetupRequest) {
        for (int i = 0; i < gameSetupRequest.getNumLives(); i++) {
            lifeService.saveLife(new Life());
        }
        return getGameInfo();
    }

    @GetMapping
    public GameResponse getGameInfo() {
        return getGameInfoResponse();
    }

    private GameResponse getGameInfoResponse() {
        int availableLives = lifeService.countAvailableLives();
        List<GameResponse.LifeResponse> lives = lifeService.getAllLives().stream()
                .map(lifeMapper::toLifeResponse).collect(Collectors.toList());
        List<String> players = playerService.getAllPlayers().stream()
                .map(Player::getUsername).collect(Collectors.toList());
        return new GameResponse(availableLives, players, lives);
    }
}
