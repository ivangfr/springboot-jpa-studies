package com.mycompany.jpaassociations.onetomany.compositepk.service;

import com.mycompany.jpaassociations.onetomany.compositepk.model.Player;

public interface PlayerService {

    Player validateAndGetPlayer(Long id);

    Player savePlayer(Player player);

    void deletePlayer(Player player);

}
