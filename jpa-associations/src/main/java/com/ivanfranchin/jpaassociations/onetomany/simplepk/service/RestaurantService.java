package com.ivanfranchin.jpaassociations.onetomany.simplepk.service;

import com.ivanfranchin.jpaassociations.onetomany.simplepk.model.Restaurant;

public interface RestaurantService {

    Restaurant validateAndGetRestaurant(Long id);

    Restaurant saveRestaurant(Restaurant restaurant);

    void deleteRestaurant(Restaurant restaurant);
}
