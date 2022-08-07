package com.ivanfranchin.jpalocking.mapper;

import com.ivanfranchin.jpalocking.model.Life;
import com.ivanfranchin.jpalocking.model.Player;
import com.ivanfranchin.jpalocking.rest.dto.GameResponse;
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
