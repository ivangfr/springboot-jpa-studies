package com.ivanfranchin.jpalocking.rest;

import com.ivanfranchin.jpalocking.life.Life;
import com.ivanfranchin.jpalocking.player.Player;
import com.ivanfranchin.jpalocking.rest.dto.GameResponse;
import com.ivanfranchin.jpalocking.rest.dto.GameSetupRequest;
import com.ivanfranchin.jpalocking.life.LifeService;
import com.ivanfranchin.jpalocking.player.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final LifeService lifeService;
    private final PlayerService playerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GameResponse setupGame(@Valid @RequestBody GameSetupRequest gameSetupRequest) {
        for (int i = 0; i < gameSetupRequest.numLives(); i++) {
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
                .map(GameResponse.LifeResponse::from).collect(Collectors.toList());
        List<String> players = playerService.getAllPlayers().stream()
                .map(Player::getUsername).collect(Collectors.toList());
        return new GameResponse(availableLives, players, lives);
    }
}
