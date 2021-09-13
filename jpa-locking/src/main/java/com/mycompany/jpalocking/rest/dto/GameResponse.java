package com.mycompany.jpalocking.rest.dto;

import lombok.Value;

import java.util.List;

@Value
public class GameResponse {

    int availableLives;
    List<String> players;
    List<LifeResponse> lives;

    @Value
    public static class LifeResponse {
        long id;
        String username;
    }
}
