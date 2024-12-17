package com.ivanfranchin.jpalocking.rest.dto;

import com.ivanfranchin.jpalocking.model.Player;
import com.ivanfranchin.jpalocking.model.StarCollection;

import java.util.Set;
import java.util.stream.Collectors;

public record PlayerResponse(long id, String username, int numStars, Set<LifeResponse> lives) {

    public record LifeResponse(long id) {
    }

    public static PlayerResponse from(Player player) {
        int numStars = player.getStars()
                .stream()
                .mapToInt(StarCollection::getNumAvailable).sum();

        Set<LifeResponse> lives = player.getLives().stream()
                .map(life -> new LifeResponse(life.getId()))
                .collect(Collectors.toSet());

        return new PlayerResponse(
                player.getId(),
                player.getUsername(),
                numStars,
                lives
        );
    }
}
