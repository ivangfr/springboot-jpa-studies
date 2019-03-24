package com.mycompany.jpalocking.rest.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PlayerDto {

    private long id;
    private String username;
    private int numStars;
    private Set<LifeDto> lives;

    @Data
    public static class LifeDto {
        private long id;
    }
}
