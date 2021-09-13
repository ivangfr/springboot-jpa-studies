package com.mycompany.jpaassociations.onetomany.compositepk.rest.dto;

import lombok.Value;

import java.util.List;

@Value
public class PlayerResponse {

    Long id;
    String name;
    List<Weapon> weapons;

    @Value
    public static class Weapon {
        Long id;
        String name;
    }
}
