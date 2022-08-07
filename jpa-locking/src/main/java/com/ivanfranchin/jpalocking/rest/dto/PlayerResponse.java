package com.ivanfranchin.jpalocking.rest.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PlayerResponse {

    private long id;
    private String username;
    private int numStars;
    private Set<LifeResponse> lives;

    @Data
    public static class LifeResponse {
        private long id;
    }
}
