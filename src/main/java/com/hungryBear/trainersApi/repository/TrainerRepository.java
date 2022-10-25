package com.hungryBear.trainersApi.repository;

import com.hungryBear.trainersApi.common.entities.Trainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends CrudRepository<Trainer, Long> {

    List<Trainer> findAll();

    Optional<Trainer> findByPhoneNumber(BigInteger phoneNumber);

    Optional<List<Trainer>> findByLastNameIgnoreCaseContaining(String lastName);
}