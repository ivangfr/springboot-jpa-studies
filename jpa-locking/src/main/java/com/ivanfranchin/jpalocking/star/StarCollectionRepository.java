package com.ivanfranchin.jpalocking.star;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarCollectionRepository extends JpaRepository<StarCollection, Long> {

  List<StarCollection> findByPlayerIdAndNumAvailableGreaterThanOrderByCreatedAt(
      Long playerId, int numAvailable);
}
