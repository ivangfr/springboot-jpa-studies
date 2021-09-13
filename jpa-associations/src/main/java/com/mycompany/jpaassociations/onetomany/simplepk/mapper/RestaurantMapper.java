package com.mycompany.jpaassociations.onetomany.simplepk.mapper;

import com.mycompany.jpaassociations.onetomany.simplepk.model.Restaurant;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.CreateRestaurantRequest;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.RestaurantResponse;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.UpdateRestaurantRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RestaurantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dishes", ignore = true)
    Restaurant toRestaurant(CreateRestaurantRequest createRestaurantRequest);

    RestaurantResponse toRestaurantResponse(Restaurant restaurant);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dishes", ignore = true)
    void updateRestaurantFromRequest(UpdateRestaurantRequest updateRestaurantRequest,
                                     @MappingTarget Restaurant restaurant);
}
