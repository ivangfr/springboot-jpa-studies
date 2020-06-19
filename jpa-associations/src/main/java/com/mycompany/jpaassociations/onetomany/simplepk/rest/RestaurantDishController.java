package com.mycompany.jpaassociations.onetomany.simplepk.rest;

import com.mycompany.jpaassociations.onetomany.simplepk.mapper.DishMapper;
import com.mycompany.jpaassociations.onetomany.simplepk.mapper.RestaurantMapper;
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
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantDishController {

    private final RestaurantService restaurantService;
    private final DishService dishService;
    private final RestaurantMapper restaurantMapper;
    private final DishMapper dishMapper;

    //-----------
    // Restaurant

    @GetMapping("/{restaurantId}")
    public RestaurantDto getRestaurant(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.validateAndGetRestaurant(restaurantId);
        return restaurantMapper.toRestaurantDto(restaurant);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RestaurantDto createRestaurant(@Valid @RequestBody CreateRestaurantDto createRestaurantDto) {
        Restaurant restaurant = restaurantMapper.toRestaurant(createRestaurantDto);
        restaurant = restaurantService.saveRestaurant(restaurant);
        return restaurantMapper.toRestaurantDto(restaurant);
    }

    @PutMapping("/{restaurantId}")
    public RestaurantDto updateRestaurant(@PathVariable Long restaurantId, @Valid @RequestBody UpdateRestaurantDto updateRestaurantDto) {
        Restaurant restaurant = restaurantService.validateAndGetRestaurant(restaurantId);
        restaurantMapper.updateRestaurantFromDto(updateRestaurantDto, restaurant);
        restaurantService.saveRestaurant(restaurant);
        return restaurantMapper.toRestaurantDto(restaurant);
    }

    @DeleteMapping("/{restaurantId}")
    public RestaurantDto deleteRestaurant(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.validateAndGetRestaurant(restaurantId);
        restaurantService.deleteRestaurant(restaurant);
        return restaurantMapper.toRestaurantDto(restaurant);
    }

    //-----
    // Dish

    @GetMapping("/{restaurantId}/dishes/{dishId}")
    public DishDto getDish(@PathVariable Long restaurantId, @PathVariable Long dishId) {
        Dish dish = dishService.validateAndGetDish(dishId, restaurantId);
        return dishMapper.toDishDto(dish);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{restaurantId}/dishes")
    public DishDto createDish(@PathVariable Long restaurantId, @Valid @RequestBody CreateDishDto createDishDto) {
        Restaurant restaurant = restaurantService.validateAndGetRestaurant(restaurantId);
        Dish dish = dishMapper.toDish(createDishDto);
        dish.addRestaurant(restaurant);
        dish = dishService.saveDish(dish);
        return dishMapper.toDishDto(dish);
    }

    @PutMapping("/{restaurantId}/dishes/{dishId}")
    public DishDto updateDish(@PathVariable Long restaurantId, @PathVariable Long dishId, @Valid @RequestBody UpdateDishDto updateDishDto) {
        Dish dish = dishService.validateAndGetDish(dishId, restaurantId);
        dishMapper.updateDishFromDto(updateDishDto, dish);
        dish = dishService.saveDish(dish);
        return dishMapper.toDishDto(dish);
    }

    @DeleteMapping("/{restaurantId}/dishes/{dishId}")
    public DishDto deleteDish(@PathVariable Long restaurantId, @PathVariable Long dishId) {
        Dish dish = dishService.validateAndGetDish(dishId, restaurantId);
        dish.removeRestaurant();
        dishService.deleteDish(dish);
        return dishMapper.toDishDto(dish);
    }

}
