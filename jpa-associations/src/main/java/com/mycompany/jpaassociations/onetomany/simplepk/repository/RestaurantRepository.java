package com.mycompany.jpaassociations.onetomany.simplepk.repository;

import com.mycompany.jpaassociations.onetomany.simplepk.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
