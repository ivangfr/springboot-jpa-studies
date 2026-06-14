package com.ivanfranchin.jpalocking.star;

import com.ivanfranchin.jpalocking.player.Player;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StarCollectionService {

  private final StarCollectionRepository starCollectionRepository;

  public List<StarCollection> getAvailableStarCollections(Player player) {
    return starCollectionRepository.findByPlayerIdAndNumAvailableGreaterThanOrderByCreatedAt(
        player.getId(), 0);
  }
}
