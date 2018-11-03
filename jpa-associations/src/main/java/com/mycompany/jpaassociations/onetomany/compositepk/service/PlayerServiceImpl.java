package com.mycompany.jpaassociations.onetomany.compositepk.service;

import com.mycompany.jpaassociations.onetomany.compositepk.exception.PlayerNotFoundException;
import com.mycompany.jpaassociations.onetomany.compositepk.model.Player;
import com.mycompany.jpaassociations.onetomany.compositepk.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player validateAndGetPlayer(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(String.format("Player with id '%s' not found", id)));
    }

    @Override
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public void deletePlayer(Player player) {
        playerRepository.delete(player);
    }
}
