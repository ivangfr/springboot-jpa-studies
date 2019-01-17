package com.mycompany.jpaassociations.onetomany.simplepk.rest;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource(properties = {
        "spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class RestaurantDishControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    @Test
    void testGetRestaurant() {
        Restaurant restaurant = getDefaultRestaurant();
        restaurant = restaurantRepository.save(restaurant);

        String url = String.format("/api/restaurants/%s", restaurant.getId());
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
        ResponseEntity<RestaurantDto> responseEntity = testRestTemplate.postForEntity("/api/restaurants", createRestaurantDto, RestaurantDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createRestaurantDto.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getDishes().size());

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(responseEntity.getBody().getId());
        assertTrue(optionalRestaurant.isPresent());
        assertEquals(createRestaurantDto.getName(), optionalRestaurant.get().getName());
    }

    @Test
    void testUpdateRestaurant() {
        Restaurant restaurant = getDefaultRestaurant();
        restaurant = restaurantRepository.save(restaurant);

        UpdateRestaurantDto updateRestaurantDto = getDefaultUpdateRestaurantDto();

        HttpEntity<UpdateRestaurantDto> requestUpdate = new HttpEntity<>(updateRestaurantDto);
        String url = String.format("/api/restaurants/%s", restaurant.getId());
        ResponseEntity<RestaurantDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, RestaurantDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(restaurant.getId(), responseEntity.getBody().getId());
        assertEquals(updateRestaurantDto.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getDishes().size());

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurant.getId());
        assertTrue(optionalRestaurant.isPresent());
        assertEquals(updateRestaurantDto.getName(), optionalRestaurant.get().getName());
    }

    @Test
    void testDeleteRestaurant() {
        Restaurant restaurant = getDefaultRestaurant();
        restaurant = restaurantRepository.save(restaurant);

        String url = String.format("/api/restaurants/%s", restaurant.getId());
        ResponseEntity<RestaurantDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, RestaurantDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(restaurant.getId(), responseEntity.getBody().getId());
        assertEquals(restaurant.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getDishes().size());

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurant.getId());
        assertFalse(optionalRestaurant.isPresent());
    }

    @Test
    void testGetDish() {
        Restaurant restaurant = getDefaultRestaurant();
        restaurant = restaurantRepository.save(restaurant);

        Dish dish = getDefaultDish();
        dish.addRestaurant(restaurant);
        dish = dishRepository.save(dish);

        String url = String.format("/api/restaurants/%s/dishes/%s", restaurant.getId(), dish.getId());
        ResponseEntity<DishDto> responseEntity = testRestTemplate.getForEntity(url, DishDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(dish.getId(), responseEntity.getBody().getId());
        assertEquals(dish.getName(), responseEntity.getBody().getName());
    }

    @Test
    void testCreateDish() {
        Restaurant restaurant = getDefaultRestaurant();
        restaurant = restaurantRepository.save(restaurant);

        CreateDishDto createDishDto = getDefaultCreateDishDto();

        String url = String.format("/api/restaurants/%s/dishes", restaurant.getId());
        ResponseEntity<DishDto> responseEntity = testRestTemplate.postForEntity(url, createDishDto, DishDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createDishDto.getName(), responseEntity.getBody().getName());

        Optional<Dish> optionalDish = dishRepository.findById(responseEntity.getBody().getId());
        assertTrue(optionalDish.isPresent());
        assertEquals(restaurant.getId(), optionalDish.get().getRestaurant().getId());
        assertEquals(createDishDto.getName(), optionalDish.get().getName());

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurant.getId());
        assertTrue(optionalRestaurant.isPresent());
        assertEquals(1, optionalRestaurant.get().getDishes().size());
        assertTrue(optionalRestaurant.get().getDishes().contains(optionalDish.get()));
    }

    @Test
    void testUpdateDish() {
        Restaurant restaurant = getDefaultRestaurant();
        restaurant = restaurantRepository.save(restaurant);

        Dish dish = getDefaultDish();
        dish.addRestaurant(restaurant);
        dish = dishRepository.save(dish);

        UpdateDishDto updateDishDto = getDefaultUpdateDishDto();

        HttpEntity<UpdateDishDto> requestUpdate = new HttpEntity<>(updateDishDto);
        String url = String.format("/api/restaurants/%s/dishes/%s", restaurant.getId(), dish.getId());
        ResponseEntity<DishDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, DishDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(dish.getId(), responseEntity.getBody().getId());
        assertEquals(updateDishDto.getName(), responseEntity.getBody().getName());

        Optional<Dish> optionalDish = dishRepository.findById(dish.getId());
        assertTrue(optionalDish.isPresent());
        assertEquals(updateDishDto.getName(), optionalDish.get().getName());
    }

    @Test
    void testDeleteDish() {
        Restaurant restaurant = getDefaultRestaurant();
        restaurant = restaurantRepository.save(restaurant);

        Dish dish = getDefaultDish();
        dish.addRestaurant(restaurant);
        dish = dishRepository.save(dish);

        String url = String.format("/api/restaurants/%s/dishes/%s", restaurant.getId(), dish.getId());
        ResponseEntity<DishDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, DishDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(dish.getId(), responseEntity.getBody().getId());
        assertEquals(dish.getName(), responseEntity.getBody().getName());

        Optional<Dish> optionalDish = dishRepository.findById(dish.getId());
        assertFalse(optionalDish.isPresent());

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurant.getId());
        assertTrue(optionalRestaurant.isPresent());
        assertEquals(0, optionalRestaurant.get().getDishes().size());
        assertFalse(optionalRestaurant.get().getDishes().contains(dish));
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

}