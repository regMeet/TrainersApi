package com.hungryBear.trainersApi.service;

import com.hungryBear.trainersApi.common.entities.Trainer;
import com.hungryBear.trainersApi.common.errors.exceptions.TrainerDuplicatedException;
import com.hungryBear.trainersApi.common.errors.exceptions.TrainerNotFoundException;
import com.hungryBear.trainersApi.repository.TrainerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class TrainerService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TrainerRepository repository;

    public List<Trainer> getAll() {
        log.info("Getting all trainers");
        return repository.findAll();
    }

    public Trainer createNewTrainer(String email, BigInteger phoneNumber, String firstName, String lastName) throws TrainerDuplicatedException {
        this.checkIfPhoneNumberExists(phoneNumber);
        log.info("saving new trainer");
        return repository.save(new Trainer(email, phoneNumber, firstName, lastName));
    }

    private void checkIfPhoneNumberExists(BigInteger phoneNumber) throws TrainerDuplicatedException {
        if (repository.findByPhoneNumber(phoneNumber).isPresent()) {
            log.info("Duplicated trainer with phone number {}", phoneNumber);
            throw new TrainerDuplicatedException();
        }
    }

    public Trainer getTrainerById(Long id) throws TrainerNotFoundException {
        return repository.findById(id).orElseThrow(() -> {
            log.info("Trainer not found with id: {}. Method name: {}", id, "getTrainerById");
            return new TrainerNotFoundException();
        });
    }

    public List<Trainer> getTrainersByName(String name) throws TrainerNotFoundException {
        return repository.findByLastNameIgnoreCaseContaining(name).orElseThrow(() -> {
            log.info("Trainer not found with last name: {}. Method name: {}", name, "getTrainersByName");
            return new TrainerNotFoundException();
        });
    }
}
