package com.mycompany.jpaassociations.onetomany.simplepk.mapper;

import com.mycompany.jpaassociations.onetomany.simplepk.model.Dish;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.CreateDishDto;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.DishDto;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.UpdateDishDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DishMapper {

    Dish toDish(CreateDishDto createDishDto);

    DishDto toDishDto(Dish dish);

    void updateDishFromDto(UpdateDishDto updateDishDto, @MappingTarget Dish dish);

}
