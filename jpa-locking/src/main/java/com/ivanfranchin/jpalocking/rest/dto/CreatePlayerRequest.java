package com.ivanfranchin.jpalocking.rest.dto;

import com.ivanfranchin.jpalocking.player.Player;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreatePlayerRequest(@Schema(example = "ivan.franchin") @NotBlank String username) {

  public Player toDomain() {
    Player player = new Player();
    player.setUsername(username);
    return player;
  }
}
