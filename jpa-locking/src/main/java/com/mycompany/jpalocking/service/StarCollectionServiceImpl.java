package com.mycompany.jpalocking.service;

import com.mycompany.jpalocking.model.Player;
import com.mycompany.jpalocking.model.StarCollection;
import com.mycompany.jpalocking.repository.StarCollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StarCollectionServiceImpl implements StarCollectionService {

    private final StarCollectionRepository starCollectionRepository;

    @Override
    public List<StarCollection> getAvailableStarCollections(Player player) {
        return starCollectionRepository.findByPlayerIdAndNumAvailableGreaterThanOrderByCreatedAt(player.getId(), 0);
    }
}
