package com.mycompany.jpaassociations.onetomany.compositepk.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlayerDto {

    private Long id;
    private String name;
    private List<Weapon> weapons;

    @Data
    public static final class Weapon {
        private Long id;
        private String name;
    }

}
