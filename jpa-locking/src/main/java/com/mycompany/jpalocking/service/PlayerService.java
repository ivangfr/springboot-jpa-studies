package com.mycompany.jpalocking.service;

import com.mycompany.jpalocking.model.Player;

import java.util.List;

public interface PlayerService {

    Player validateAndGetPlayer(Long id);

    Player savePlayer(Player player);

    Player addStars(Long id, int numStars);

    Player redeemStars(Long id);

    List<Player> getAllPlayers();
}
