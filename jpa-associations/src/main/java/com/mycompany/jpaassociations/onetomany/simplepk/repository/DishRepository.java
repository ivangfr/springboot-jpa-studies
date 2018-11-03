package com.mycompany.jpaassociations.onetomany.simplepk.repository;

import com.mycompany.jpaassociations.onetomany.simplepk.model.Dish;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DishRepository extends CrudRepository<Dish, Long> {

    Optional<Dish> findByIdAndRestaurantId(Long id, Long restaurantId);

}
