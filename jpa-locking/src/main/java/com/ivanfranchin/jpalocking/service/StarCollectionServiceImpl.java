package com.ivanfranchin.jpalocking.service;

import com.ivanfranchin.jpalocking.model.Player;
import com.ivanfranchin.jpalocking.model.StarCollection;
import com.ivanfranchin.jpalocking.repository.StarCollectionRepository;
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
