package com.mycompany.jpaassociations.onetomany.simplepk.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class RestaurantDto {

    private Long id;
    private String name;
    private List<DishDto> dishes;

}
