package com.ivanfranchin.jpalocking.star;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StarCollectionRepository extends JpaRepository<StarCollection, Long> {

    List<StarCollection> findByPlayerIdAndNumAvailableGreaterThanOrderByCreatedAt(Long playerId, int numAvailable);
}
