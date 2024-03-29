package com.ivanfranchin.jpalocking.mapper;

import com.ivanfranchin.jpalocking.model.Player;
import com.ivanfranchin.jpalocking.rest.dto.CreatePlayerRequest;
import com.ivanfranchin.jpalocking.rest.dto.PlayerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stars", ignore = true)
    @Mapping(target = "lives", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Player toPlayer(CreatePlayerRequest createPlayerRequest);

    @Mapping(
            target = "numStars",
            expression = "java(player.getStars().stream().mapToInt(starCollection -> starCollection.getNumAvailable()).sum())"
    )
    PlayerResponse toPlayerResponse(Player player);
}
