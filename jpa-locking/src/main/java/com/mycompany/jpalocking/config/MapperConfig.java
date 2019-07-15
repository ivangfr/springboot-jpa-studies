package com.mycompany.jpalocking.config;

import com.mycompany.jpalocking.model.Life;
import com.mycompany.jpalocking.model.Player;
import com.mycompany.jpalocking.model.StarCollection;
import com.mycompany.jpalocking.rest.dto.GameDto;
import com.mycompany.jpalocking.rest.dto.PlayerDto;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    MapperFactory mapperFactory() {
        DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder()
                .useAutoMapping(true).mapNulls(false).build();

        mapperFactory.classMap(Life.class, GameDto.LifeDto.class)
                .byDefault()
                .customize(new CustomMapper<Life, GameDto.LifeDto>() {
                    @Override
                    public void mapAtoB(Life life, GameDto.LifeDto lifeDto, MappingContext context) {
                        super.mapAtoB(life, lifeDto, context);

                        Player player = life.getPlayer();
                        if (player != null) {
                            lifeDto.setUsername(player.getUsername());
                        }
                    }
                }).register();

        mapperFactory.classMap(Player.class, PlayerDto.class)
                .byDefault()
                .customize(new CustomMapper<Player, PlayerDto>() {
                    @Override
                    public void mapAtoB(Player player, PlayerDto playerDto, MappingContext context) {
                        super.mapAtoB(player, playerDto, context);

                        playerDto.setNumStars(player.getStars().stream().mapToInt(StarCollection::getNumAvailable).sum());
                    }
                }).register();

        return mapperFactory;
    }

    @Bean
    MapperFacade mapperFacade() {
        return mapperFactory().getMapperFacade();
    }
}

