package com.mycompany.jpaassociations.onetomany.compositepk.service;

import com.mycompany.jpaassociations.onetomany.compositepk.exception.PlayerNotFoundException;
import com.mycompany.jpaassociations.onetomany.compositepk.model.Player;
import com.mycompany.jpaassociations.onetomany.compositepk.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public Player validateAndGetPlayer(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
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
