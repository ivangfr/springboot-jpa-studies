package com.mycompany.jpaassociations.onetomany.simplepk.rest;

import com.mycompany.jpaassociations.AbstractTestcontainers;
import com.mycompany.jpaassociations.onetomany.simplepk.model.Dish;
import com.mycompany.jpaassociations.onetomany.simplepk.model.Restaurant;
import com.mycompany.jpaassociations.onetomany.simplepk.repository.DishRepository;
import com.mycompany.jpaassociations.onetomany.simplepk.repository.RestaurantRepository;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.CreateDishDto;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.CreateRestaurantDto;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.DishDto;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.RestaurantDto;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.UpdateDishDto;
import com.mycompany.jpaassociations.onetomany.simplepk.rest.dto.UpdateRestaurantDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class RestaurantDishControllerTest extends AbstractTestcontainers {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    @Test
    void testGetRestaurant() {
        Restaurant restaurant = restaurantRepository.save(getDefaultRestaurant());

        String url = String.format(API_RESTAURANTS_RESTAURANT_ID_URL, restaurant.getId());
        ResponseEntity<RestaurantDto> responseEntity = testRestTemplate.getForEntity(url, RestaurantDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(restaurant.getId(), responseEntity.getBody().getId());
        assertEquals(restaurant.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getDishes().size());
    }

    @Test
    void testCreateRestaurant() {
        CreateRestaurantDto createRestaurantDto = getDefaultCreateRestaurantDto();
        ResponseEntity<RestaurantDto> responseEntity = testRestTemplate.postForEntity(API_RESTAURANTS_URL, createRestaurantDto, RestaurantDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createRestaurantDto.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getDishes().size());

        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(responseEntity.getBody().getId());
        assertTrue(restaurantOptional.isPresent());
        restaurantOptional.ifPresent(r -> assertEquals(createRestaurantDto.getName(), r.getName()));
    }

    @Test
    void testUpdateRestaurant() {
        Restaurant restaurant = restaurantRepository.save(getDefaultRestaurant());
        UpdateRestaurantDto updateRestaurantDto = getDefaultUpdateRestaurantDto();

        HttpEntity<UpdateRestaurantDto> requestUpdate = new HttpEntity<>(updateRestaurantDto);
        String url = String.format(API_RESTAURANTS_RESTAURANT_ID_URL, restaurant.getId());
        ResponseEntity<RestaurantDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, RestaurantDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(restaurant.getId(), responseEntity.getBody().getId());
        assertEquals(updateRestaurantDto.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getDishes().size());

        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurant.getId());
        assertTrue(restaurantOptional.isPresent());
        restaurantOptional.ifPresent(r -> assertEquals(updateRestaurantDto.getName(), r.getName()));
    }

    @Test
    void testDeleteRestaurant() {
        Restaurant restaurant = restaurantRepository.save(getDefaultRestaurant());

        String url = String.format(API_RESTAURANTS_RESTAURANT_ID_URL, restaurant.getId());
        ResponseEntity<RestaurantDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, RestaurantDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(restaurant.getId(), responseEntity.getBody().getId());
        assertEquals(restaurant.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getDishes().size());

        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurant.getId());
        assertFalse(restaurantOptional.isPresent());
    }

    @Test
    void testGetDish() {
        Restaurant restaurant = restaurantRepository.save(getDefaultRestaurant());

        Dish dish = getDefaultDish();
        dish.addRestaurant(restaurant);
        dish = dishRepository.save(dish);

        String url = String.format(API_RESTAURANTS_RESTAURANT_ID_DISHES_DISH_ID_URL, restaurant.getId(), dish.getId());
        ResponseEntity<DishDto> responseEntity = testRestTemplate.getForEntity(url, DishDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(dish.getId(), responseEntity.getBody().getId());
        assertEquals(dish.getName(), responseEntity.getBody().getName());
    }

    @Test
    void testCreateDish() {
        Restaurant restaurant = restaurantRepository.save(getDefaultRestaurant());
        CreateDishDto createDishDto = getDefaultCreateDishDto();

        String url = String.format(API_RESTAURANTS_RESTAURANT_ID_DISHES_URL, restaurant.getId());
        ResponseEntity<DishDto> responseEntity = testRestTemplate.postForEntity(url, createDishDto, DishDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createDishDto.getName(), responseEntity.getBody().getName());

        Optional<Dish> dishOptional = dishRepository.findById(responseEntity.getBody().getId());
        assertTrue(dishOptional.isPresent());
        dishOptional.ifPresent(d -> {
            assertEquals(restaurant.getId(), d.getRestaurant().getId());
            assertEquals(createDishDto.getName(), d.getName());
        });

        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurant.getId());
        assertTrue(restaurantOptional.isPresent());
        restaurantOptional.ifPresent(r -> {
            assertEquals(1, r.getDishes().size());
            dishOptional.ifPresent(d -> assertTrue(r.getDishes().contains(d)));
        });
    }

    @Test
    void testUpdateDish() {
        Restaurant restaurant = restaurantRepository.save(getDefaultRestaurant());

        Dish dish = getDefaultDish();
        dish.addRestaurant(restaurant);
        dish = dishRepository.save(dish);

        UpdateDishDto updateDishDto = getDefaultUpdateDishDto();

        HttpEntity<UpdateDishDto> requestUpdate = new HttpEntity<>(updateDishDto);
        String url = String.format(API_RESTAURANTS_RESTAURANT_ID_DISHES_DISH_ID_URL, restaurant.getId(), dish.getId());
        ResponseEntity<DishDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, DishDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(dish.getId(), responseEntity.getBody().getId());
        assertEquals(updateDishDto.getName(), responseEntity.getBody().getName());

        Optional<Dish> dishOptional = dishRepository.findById(dish.getId());
        assertTrue(dishOptional.isPresent());
        dishOptional.ifPresent(d -> assertEquals(updateDishDto.getName(), d.getName()));
    }

    @Test
    void testDeleteDish() {
        Restaurant restaurant = restaurantRepository.save(getDefaultRestaurant());

        Dish dishAux = getDefaultDish();
        dishAux.addRestaurant(restaurant);
        final Dish dish = dishRepository.save(dishAux);

        String url = String.format(API_RESTAURANTS_RESTAURANT_ID_DISHES_DISH_ID_URL, restaurant.getId(), dish.getId());
        ResponseEntity<DishDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, DishDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(dish.getId(), responseEntity.getBody().getId());
        assertEquals(dish.getName(), responseEntity.getBody().getName());

        Optional<Dish> dishOptional = dishRepository.findById(dish.getId());
        assertFalse(dishOptional.isPresent());

        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurant.getId());
        assertTrue(restaurantOptional.isPresent());
        restaurantOptional.ifPresent(r -> {
            assertEquals(0, r.getDishes().size());
            assertFalse(r.getDishes().contains(dish));
        });
    }

    private Restaurant getDefaultRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Happy Pizza");
        return restaurant;
    }

    private CreateRestaurantDto getDefaultCreateRestaurantDto() {
        CreateRestaurantDto createRestaurantDto = new CreateRestaurantDto();
        createRestaurantDto.setName("Happy Pizza");
        return createRestaurantDto;
    }

    private UpdateRestaurantDto getDefaultUpdateRestaurantDto() {
        UpdateRestaurantDto updateRestaurantDto = new UpdateRestaurantDto();
        updateRestaurantDto.setName("Happy Burger");
        return updateRestaurantDto;
    }

    private Dish getDefaultDish() {
        Dish dish = new Dish();
        dish.setName("Pizza Salami");
        return dish;
    }

    private CreateDishDto getDefaultCreateDishDto() {
        CreateDishDto createDishDto = new CreateDishDto();
        createDishDto.setName("Pizza Salami");
        return createDishDto;
    }

    private UpdateDishDto getDefaultUpdateDishDto() {
        UpdateDishDto updateDishDto = new UpdateDishDto();
        updateDishDto.setName("Pizza Fungi");
        return updateDishDto;
    }

    private static final String API_RESTAURANTS_URL = "/api/restaurants";
    private static final String API_RESTAURANTS_RESTAURANT_ID_URL = "/api/restaurants/%s";
    private static final String API_RESTAURANTS_RESTAURANT_ID_DISHES_URL = "/api/restaurants/%s/dishes";
    private static final String API_RESTAURANTS_RESTAURANT_ID_DISHES_DISH_ID_URL = "/api/restaurants/%s/dishes/%s";
}