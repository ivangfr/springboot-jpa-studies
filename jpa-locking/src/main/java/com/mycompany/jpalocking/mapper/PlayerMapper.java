package com.mycompany.jpalocking.mapper;

import com.mycompany.jpalocking.model.Player;
import com.mycompany.jpalocking.rest.dto.CreatePlayerDto;
import com.mycompany.jpalocking.rest.dto.PlayerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    Player toPlayer(CreatePlayerDto createPlayerDto);

    @Mapping(
            target = "numStars",
            expression = "java(player.getStars().stream().mapToInt(starCollection -> starCollection.getNumAvailable()).sum())"
    )
    PlayerDto toPlayerDto(Player player);

}
