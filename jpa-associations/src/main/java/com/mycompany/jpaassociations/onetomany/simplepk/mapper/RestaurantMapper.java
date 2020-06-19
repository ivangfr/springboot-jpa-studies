package com.mycompany.jpaassociations.onetomany.simplepk.mapper;

import com.mycompany.jpaassociations.onetomany.simplepk.model.Restaurant;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.CreateRestaurantDto;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.RestaurantDto;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.UpdateRestaurantDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RestaurantMapper {

    Restaurant toRestaurant(CreateRestaurantDto createRestaurantDto);

    RestaurantDto toRestaurantDto(Restaurant restaurant);

    void updateRestaurantFromDto(UpdateRestaurantDto updateRestaurantDto, @MappingTarget Restaurant restaurant);

}
