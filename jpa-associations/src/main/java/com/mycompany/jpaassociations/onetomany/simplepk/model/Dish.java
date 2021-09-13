package com.mycompany.jpaassociations.onetomany.simplepk.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@ToString(exclude = "restaurant")
@EqualsAndHashCode(exclude = "restaurant")
@Entity
@Table(name = "dishes")
public class Dish {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(nullable = false)
    private String name;

    public void addRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        restaurant.getDishes().add(this);
    }

    public void removeRestaurant() {
        this.restaurant.getDishes().remove(this);
        this.restaurant = null;
    }
}
