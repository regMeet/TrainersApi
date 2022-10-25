package com.hungryBear.trainersApi.service;

import com.hungryBear.trainersApi.common.entities.Trainer;
import com.hungryBear.trainersApi.common.errors.ErrorCode;
import com.hungryBear.trainersApi.common.errors.exceptions.TrainerDuplicatedException;
import com.hungryBear.trainersApi.common.errors.exceptions.TrainerNotFoundException;
import com.hungryBear.trainersApi.repository.TrainerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {
    private static final Trainer trainer1 = new Trainer(1L, "alan.albertengo@gmail.com", new BigInteger("1234567890"), "Alan", "Albertengo");
    private static final Trainer trainer2 = new Trainer(1L, "trainer2@gmail.com", new BigInteger("9876543210"), "trainer2", "Albertengo");
    private static final List<Trainer> trainerList = Arrays.asList(trainer1, trainer2);

    @InjectMocks
    TrainerService service;

    @Mock
    TrainerRepository repository;

    @Test
    public void testGetAll() {
        when(repository.findAll()).thenReturn(trainerList);
        List<Trainer> list = service.getAll();
        verify(repository, times(1)).findAll();
        assertNotNull(list);
    }

    @Test
    public void testFindById() throws TrainerNotFoundException {
        when(repository.findById(any())).thenReturn(Optional.of(trainer1));
        Trainer trainerFound = service.getTrainerById(trainer1.getId());
        verify(repository, times(1)).findById(any());
        assertEquals(trainerFound.getId(), trainer1.getId());
        assertNotNull(trainerFound);
    }

    @Test
    public void testFindByIdError() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        TrainerNotFoundException e = assertThrows(TrainerNotFoundException.class,
                () -> service.getTrainerById(trainer1.getId()), "Expected getTrainerById() to throw");

        assertEquals(ErrorCode.TRAINER_NOT_FOUND.getCode(), e.getMessage());
        verify(repository, times(1)).findById(any());
    }

    @Test
    public void testFindByName() throws TrainerNotFoundException {
        when(repository.findByLastNameIgnoreCaseContaining(any())).thenReturn(Optional.of(trainerList));
        List<Trainer> trainers = service.getTrainersByName(trainer1.getLastName());
        verify(repository, times(1)).findByLastNameIgnoreCaseContaining(any());
        assertNotNull(trainers);
    }

    @Test
    public void testSave() throws TrainerDuplicatedException {
        when(repository.findByPhoneNumber(any())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(new Trainer());
        Trainer trainer = service.createNewTrainer(trainer1.getEmail(), trainer1.getPhoneNumber(), trainer1.getFirstName(), trainer1.getFirstName());
        verify(repository, times(1)).findByPhoneNumber(any());
        verify(repository, times(1)).save(any());
        assertNotNull(trainer);
    }

    @Test
    public void testSaveError() {
        when(repository.findByPhoneNumber(any())).thenReturn(Optional.of(trainer1));

        TrainerDuplicatedException e = assertThrows(TrainerDuplicatedException.class, () -> service.createNewTrainer(trainer1.getEmail(), trainer1.getPhoneNumber(), trainer1.getFirstName(), trainer1.getFirstName()), "Expected createNewTrainer() to throw");

        assertEquals(ErrorCode.TRAINER_DUPLICATED.getCode(), e.getMessage());
        verify(repository, times(1)).findByPhoneNumber(any());
        verify(repository, times(0)).save(any());
    }
}
