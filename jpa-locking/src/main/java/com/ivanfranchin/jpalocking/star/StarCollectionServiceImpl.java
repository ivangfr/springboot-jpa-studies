package com.ivanfranchin.jpalocking.star;

import com.ivanfranchin.jpalocking.player.Player;
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
