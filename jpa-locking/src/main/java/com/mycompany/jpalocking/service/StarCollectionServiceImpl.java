package com.mycompany.jpalocking.service;

import com.mycompany.jpalocking.model.Player;
import com.mycompany.jpalocking.model.StarCollection;
import com.mycompany.jpalocking.repository.StarCollectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StarCollectionServiceImpl implements StarCollectionService {

    private final StarCollectionRepository starCollectionRepository;

    public StarCollectionServiceImpl(StarCollectionRepository starCollectionRepository) {
        this.starCollectionRepository = starCollectionRepository;
    }

    @Override
    public List<StarCollection> getAvailableStarCollections(Player player) {
        return starCollectionRepository.findByPlayerIdAndNumAvailableGreaterThanOrderByCreatedAt(player.getId(), 0);
    }

}
