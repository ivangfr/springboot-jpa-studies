package com.mycompany.jpaassociations.onetomany.simplepk.service;

import com.mycompany.jpaassociations.onetomany.simplepk.exception.DishNotFoundException;
import com.mycompany.jpaassociations.onetomany.simplepk.model.Dish;
import com.mycompany.jpaassociations.onetomany.simplepk.repository.DishRepository;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public Dish validateAndGetDish(Long dishId, Long restaurantId) {
        return dishRepository.findByIdAndRestaurantId(dishId, restaurantId)
                .orElseThrow(() -> new DishNotFoundException(String.format("Restaurant with id '%s' doesn't have dish with id '%s'", restaurantId, dishId)));
    }

    @Override
    public Dish saveDish(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    public void deleteDish(Dish dish) {
        dishRepository.delete(dish);
    }
}
