package com.mycompany.jpalocking.service;

import com.mycompany.jpalocking.exception.AllLivesRedeemedException;
import com.mycompany.jpalocking.model.Life;
import com.mycompany.jpalocking.repository.LifeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class LifeServiceImpl implements LifeService {

    private final LifeRepository lifeRepository;

    public LifeServiceImpl(LifeRepository lifeRepository) {
        this.lifeRepository = lifeRepository;
    }

    @Override
    public List<Life> getAllLives() {
        return lifeRepository.findAll();
    }

    @Override
    public int countAvailableLives() {
        return lifeRepository.countByPlayerIdIsNull();
    }

    @Override
    public Life saveLife(Life life) {
        return lifeRepository.save(life);
    }

    @Override
    public Life getAvailableLife() {
        // -- Randomly
        List<Life> lives = lifeRepository.findByPlayerIdIsNull();
        if (lives.isEmpty()) {
            throw new AllLivesRedeemedException("There are no lives to be redeemed.");
        }
        return lives.get(getRandomNumber(lives.size()));

        // -- First
//        return lifeRepository.findFirstByPlayerIdIsNull();
    }

    private static final Random random = new Random();

    private static int getRandomNumber(int max) {
        return random.nextInt(max);
    }
}
