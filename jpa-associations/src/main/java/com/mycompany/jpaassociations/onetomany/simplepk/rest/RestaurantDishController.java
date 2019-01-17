package com.mycompany.jpaassociations.onetomany.simplepk.rest;

import com.mycompany.jpaassociations.onetomany.simplepk.model.Dish;
import com.mycompany.jpaassociations.onetomany.simplepk.model.Restaurant;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.CreateDishDto;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.CreateRestaurantDto;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.DishDto;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.RestaurantDto;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.UpdateDishDto;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.UpdateRestaurantDto;
import com.mycompany.jpaassociations.onetomany.simplepk.service.DishService;
import com.mycompany.jpaassociations.onetomany.simplepk.service.RestaurantService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantDishController {

    private final RestaurantService restaurantService;
    private final DishService dishService;
    private final MapperFacade mapperFacade;

    public RestaurantDishController(RestaurantService restaurantService, DishService dishService, MapperFacade mapperFacade) {
        this.restaurantService = restaurantService;
        this.dishService = dishService;
        this.mapperFacade = mapperFacade;
    }

    //-----------
    // Restaurant

    @GetMapping("/{restaurantId}")
    public RestaurantDto getRestaurant(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.validateAndGetRestaurant(restaurantId);
        return mapperFacade.map(restaurant, RestaurantDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RestaurantDto createRestaurant(@Valid @RequestBody CreateRestaurantDto createRestaurantDto) {
        Restaurant restaurant = mapperFacade.map(createRestaurantDto, Restaurant.class);
        restaurant = restaurantService.saveRestaurant(restaurant);
        return mapperFacade.map(restaurant, RestaurantDto.class);
    }

    @PutMapping("/{restaurantId}")
    public RestaurantDto updateRestaurant(@PathVariable Long restaurantId, @Valid @RequestBody UpdateRestaurantDto updateRestaurantDto) {
        Restaurant restaurant = restaurantService.validateAndGetRestaurant(restaurantId);
        mapperFacade.map(updateRestaurantDto, restaurant);
        restaurantService.saveRestaurant(restaurant);
        return mapperFacade.map(restaurant, RestaurantDto.class);
    }

    @DeleteMapping("/{restaurantId}")
    public RestaurantDto deleteRestaurant(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.validateAndGetRestaurant(restaurantId);
        restaurantService.deleteRestaurant(restaurant);
        return mapperFacade.map(restaurant, RestaurantDto.class);
    }

    //-----
    // Dish

    @GetMapping("/{restaurantId}/dishes/{dishId}")
    public DishDto getDish(@PathVariable Long restaurantId, @PathVariable Long dishId) {
        Dish dish = dishService.validateAndGetDish(dishId, restaurantId);
        return mapperFacade.map(dish, DishDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{restaurantId}/dishes")
    public DishDto createDish(@PathVariable Long restaurantId, @Valid @RequestBody CreateDishDto createDishDto) {
        Restaurant restaurant = restaurantService.validateAndGetRestaurant(restaurantId);
        Dish dish = mapperFacade.map(createDishDto, Dish.class);
        dish.addRestaurant(restaurant);
        dish = dishService.saveDish(dish);
        return mapperFacade.map(dish, DishDto.class);
    }

    @PutMapping("/{restaurantId}/dishes/{dishId}")
    public DishDto updateDish(@PathVariable Long restaurantId, @PathVariable Long dishId, @Valid @RequestBody UpdateDishDto updateDishDto) {
        Dish dish = dishService.validateAndGetDish(dishId, restaurantId);
        mapperFacade.map(updateDishDto, dish);
        dish = dishService.saveDish(dish);
        return mapperFacade.map(dish, DishDto.class);
    }

    @DeleteMapping("/{restaurantId}/dishes/{dishId}")
    public DishDto deleteDish(@PathVariable Long restaurantId, @PathVariable Long dishId) {
        Dish dish = dishService.validateAndGetDish(dishId, restaurantId);
        dish.removeRestaurant();
        dishService.deleteDish(dish);
        return mapperFacade.map(dish, DishDto.class);
    }

}
