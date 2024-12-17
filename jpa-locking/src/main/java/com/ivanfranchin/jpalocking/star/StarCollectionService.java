package com.ivanfranchin.jpalocking.star;

import com.ivanfranchin.jpalocking.player.Player;

import java.util.List;

public interface StarCollectionService {

    List<StarCollection> getAvailableStarCollections(Player player);
}
