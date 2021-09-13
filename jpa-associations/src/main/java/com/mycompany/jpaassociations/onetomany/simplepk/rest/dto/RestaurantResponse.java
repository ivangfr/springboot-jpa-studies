package com.mycompany.jpaassociations.onetomany.simplepk.rest.dto;

import lombok.Value;

import java.util.List;

@Value
public class RestaurantResponse {

    Long id;
    String name;
    List<DishResponse> dishes;
}
