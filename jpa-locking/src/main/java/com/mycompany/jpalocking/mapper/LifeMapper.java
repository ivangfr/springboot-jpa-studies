package com.mycompany.jpalocking.mapper;

import com.mycompany.jpalocking.model.Life;
import com.mycompany.jpalocking.model.Player;
import com.mycompany.jpalocking.rest.dto.GameResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LifeMapper {

    default GameResponse.LifeResponse toLifeResponse(Life life) {
        long id = life.getId();
        Player player = life.getPlayer();
        String username = player != null ? player.getUsername() : null;
        return new GameResponse.LifeResponse(id, username);
    }
}
