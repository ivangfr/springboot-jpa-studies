package com.mycompany.jpalocking.repository;

import com.mycompany.jpalocking.model.StarCollection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StarCollectionRepository extends JpaRepository<StarCollection, Long> {

    List<StarCollection> findByPlayerIdAndNumAvailableGreaterThanOrderByCreatedAt(Long playerId, int numAvailable);

}
