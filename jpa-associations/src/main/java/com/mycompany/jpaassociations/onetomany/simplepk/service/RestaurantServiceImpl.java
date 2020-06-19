package com.mycompany.jpaassociations.onetomany.simplepk.service;

import com.mycompany.jpaassociations.onetomany.simplepk.exception.RestaurantNotFoundException;
import com.mycompany.jpaassociations.onetomany.simplepk.model.Restaurant;
import com.mycompany.jpaassociations.onetomany.simplepk.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Restaurant validateAndGetRestaurant(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(String.format("Restaurant with id '%s' not found", id)));
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }
}
