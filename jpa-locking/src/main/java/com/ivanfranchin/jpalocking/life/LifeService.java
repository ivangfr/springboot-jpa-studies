package com.ivanfranchin.jpalocking.life;

import java.util.List;

public interface LifeService {

    List<Life> getAllLives();

    int countAvailableLives();

    Life saveLife(Life life);

    Life getAvailableLife();
}
