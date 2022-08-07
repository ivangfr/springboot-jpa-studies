package com.ivanfranchin.jpalocking.service;

import com.ivanfranchin.jpalocking.model.Player;

import java.util.List;

public interface PlayerService {

    Player validateAndGetPlayer(Long id);

    Player savePlayer(Player player);

    Player collectStars(Player player, int numStars);

    Player redeemStars(Player player);

    List<Player> getAllPlayers();
}
