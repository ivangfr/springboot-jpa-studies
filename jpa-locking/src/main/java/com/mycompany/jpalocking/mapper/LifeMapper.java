package com.mycompany.jpalocking.mapper;

import com.mycompany.jpalocking.model.Life;
import com.mycompany.jpalocking.model.Player;
import com.mycompany.jpalocking.rest.dto.GameDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LifeMapper {

    default GameDto.LifeDto toLifeDto(Life life) {
        GameDto.LifeDto lifeDto = new GameDto.LifeDto();
        lifeDto.setId(life.getId());
        Player player = life.getPlayer();
        if (player != null) {
            lifeDto.setUsername(player.getUsername());
        }
        return lifeDto;
    }

}
