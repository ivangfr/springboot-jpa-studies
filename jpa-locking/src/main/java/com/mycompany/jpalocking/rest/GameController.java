package com.mycompany.jpalocking.rest;

import com.mycompany.jpalocking.mapper.LifeMapper;
import com.mycompany.jpalocking.model.Life;
import com.mycompany.jpalocking.model.Player;
import com.mycompany.jpalocking.rest.dto.GameDto;
import com.mycompany.jpalocking.rest.dto.GameSetupDto;
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
    public GameDto setupGame(@Valid @RequestBody GameSetupDto gameSetupDto) {
        for (int i = 0; i < gameSetupDto.getNumLives(); i++) {
            lifeService.saveLife(new Life());
        }
        return getGameInfo();
    }

    @GetMapping
    public GameDto getGameInfo() {
        return getGameInfoDto();
    }


    private GameDto getGameInfoDto() {
        GameDto gameDto = new GameDto();
        gameDto.setAvailableLives(lifeService.countAvailableLives());
        gameDto.setLives(lifeService.getAllLives().stream().map(lifeMapper::toLifeDto).collect(Collectors.toList()));
        gameDto.setPlayers(playerService.getAllPlayers().stream().map(Player::getUsername).collect(Collectors.toList()));
        return gameDto;
    }

}
