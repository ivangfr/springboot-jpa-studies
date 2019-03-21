package com.mycompany.jpalocking.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class GameDto {

    private int availableLives;

    private List<String> players;
    private List<LifeDto> lives;

    @Data
    public static class LifeDto {

        private long id;
        private String username;
    }

}
