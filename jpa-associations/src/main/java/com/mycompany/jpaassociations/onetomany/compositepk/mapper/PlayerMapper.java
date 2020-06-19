package com.mycompany.jpaassociations.onetomany.compositepk.mapper;

import com.mycompany.jpaassociations.onetomany.compositepk.model.Player;
import com.mycompany.jpaassociations.onetomany.compositepk.rest.dto.CreatePlayerDto;
import com.mycompany.jpaassociations.onetomany.compositepk.rest.dto.PlayerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    Player toPlayer(CreatePlayerDto createPlayerDto);

    PlayerDto toPlayerDto(Player player);

}
