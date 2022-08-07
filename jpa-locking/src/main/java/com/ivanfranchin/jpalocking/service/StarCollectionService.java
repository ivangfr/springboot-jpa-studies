package com.ivanfranchin.jpalocking.service;

import com.ivanfranchin.jpalocking.model.Player;
import com.ivanfranchin.jpalocking.model.StarCollection;

import java.util.List;

public interface StarCollectionService {

    List<StarCollection> getAvailableStarCollections(Player player);
}
