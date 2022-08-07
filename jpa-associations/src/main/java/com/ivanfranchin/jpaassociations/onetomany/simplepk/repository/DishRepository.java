package com.ivanfranchin.jpaassociations.onetomany.simplepk.repository;

import com.ivanfranchin.jpaassociations.onetomany.simplepk.model.Dish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepository extends CrudRepository<Dish, Long> {
    Optional<Dish> findByIdAndRestaurantId(Long id, Long restaurantId);
}
