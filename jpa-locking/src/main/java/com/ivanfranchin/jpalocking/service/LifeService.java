package com.ivanfranchin.jpalocking.service;

import com.ivanfranchin.jpalocking.model.Life;

import java.util.List;

public interface LifeService {

    List<Life> getAllLives();

    int countAvailableLives();

    Life saveLife(Life life);

    Life getAvailableLife();
}
