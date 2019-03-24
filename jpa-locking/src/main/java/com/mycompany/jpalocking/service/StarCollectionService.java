package com.mycompany.jpalocking.service;

import com.mycompany.jpalocking.model.Player;
import com.mycompany.jpalocking.model.StarCollection;

import java.util.List;

public interface StarCollectionService {

    List<StarCollection> getAvailableStarCollections(Player player);

}
