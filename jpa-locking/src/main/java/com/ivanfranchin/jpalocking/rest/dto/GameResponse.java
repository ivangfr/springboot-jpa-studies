package com.ivanfranchin.jpalocking.rest.dto;

import com.ivanfranchin.jpalocking.life.Life;
import com.ivanfranchin.jpalocking.player.Player;

import java.util.List;

public record GameResponse(int availableLives, List<String> players, List<LifeResponse> lives) {

    public record LifeResponse(long id, String username) {
        public static LifeResponse from(Life life) {
            Player player = life.getPlayer();
            String username = player != null ? player.getUsername() : null;
            return new LifeResponse(life.getId(), username);
        }
    }
}
