package com.mycompany.jpalocking.service;

import com.mycompany.jpalocking.model.Life;

import java.util.List;

public interface LifeService {

    List<Life> getAllLives();

    int countAvailableLives();

    Life saveLife(Life life);

    Life getAvailableLife();
}
