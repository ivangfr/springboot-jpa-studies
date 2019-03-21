package com.mycompany.jpalocking.config;

import com.mycompany.jpalocking.model.Life;
import com.mycompany.jpalocking.model.Player;
import com.mycompany.jpalocking.rest.dto.GameDto;
import com.mycompany.jpalocking.rest.dto.PlayerDto;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
public class MapperConfig {

    @Bean
    MapperFactory mapperFactory() {
        DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder().useAutoMapping(true).build();

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

        return mapperFactory;
    }

    @Bean
    MapperFacade mapperFacade() {
        return mapperFactory().getMapperFacade();
    }
}

