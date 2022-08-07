package com.ivanfranchin.jpalocking.rest.dto;

import java.util.List;

public record GameResponse(int availableLives, List<String> players, List<LifeResponse> lives) {

    public record LifeResponse(long id, String username) {
    }
}
