package com.hungryBear.trainersApi.controller;

import com.hungryBear.trainersApi.common.entities.Trainer;
import com.hungryBear.trainersApi.common.errors.exceptions.TrainerDuplicatedException;
import com.hungryBear.trainersApi.common.errors.exceptions.TrainerNotFoundException;
import com.hungryBear.trainersApi.common.vo.NewTrainerRequest;
import com.hungryBear.trainersApi.service.TrainerService;
import com.hungryBear.trainersApi.utils.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/trainers")
public class TrainerController {
    @Autowired
    private TrainerService trainerService;

    @GetMapping()
    @LogExecutionTime
    public List<Trainer> getAll() {
        return trainerService.getAll();
    }

    @GetMapping("/{id}")
    @LogExecutionTime
    public Trainer getTrainerById(@PathVariable("id") Long id) throws TrainerNotFoundException {
        return trainerService.getTrainerById(id);
    }

    @GetMapping("/name/{name}")
    @LogExecutionTime
    public List<Trainer> getTrainerByName(@PathVariable("name") String name) throws TrainerNotFoundException {
        return trainerService.getTrainersByName(name);
    }


    @PostMapping()
    @LogExecutionTime
    public @ResponseStatus(HttpStatus.CREATED) Trainer createNewTrainer(@RequestBody @Valid NewTrainerRequest req) throws TrainerDuplicatedException {
        return trainerService.createNewTrainer(req.getEmail(), new BigInteger(req.getPhoneNumber()), req.getFirstName(), req.getLastName());
    }
}
