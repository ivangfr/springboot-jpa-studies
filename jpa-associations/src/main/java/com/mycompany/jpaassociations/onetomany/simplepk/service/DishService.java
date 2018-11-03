package com.mycompany.jpaassociations.onetomany.simplepk.service;

import com.mycompany.jpaassociations.onetomany.simplepk.model.Dish;

public interface DishService {

    Dish validateAndGetDish(Long dishId, Long restaurantId);

    Dish saveDish(Dish dish);

    void deleteDish(Dish dish);
}
