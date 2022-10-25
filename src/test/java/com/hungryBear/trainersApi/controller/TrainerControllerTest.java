package com.hungryBear.trainersApi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungryBear.trainersApi.common.errors.ErrorCode;
import com.hungryBear.trainersApi.common.vo.NewTrainerRequest;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class TrainerControllerTest {

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetAll() throws Exception {

        this.mockMvc.perform(get("/trainers/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.[0].email", is("trainer@campgladiator.com")))
                .andExpect(jsonPath("$.[0].phoneNumber", is(5125125120L)))
                .andExpect(jsonPath("$.[0].firstName", is("Fearless")))
                .andExpect(jsonPath("$.[0].lastName", is("Contender")));
    }


    private NewTrainerRequest createNewTrainerRequest(String email, String phoneNumber, String firstName, String lastName) {
        NewTrainerRequest request = new NewTrainerRequest();
        request.setEmail(email);
        request.setPhoneNumber(phoneNumber);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        return request;
    }

    @Test
    @Order(1)
    public void testSave() throws Exception {
        String email = "trainer@gmail.com";
        String phoneNumber = "1234567890";
        String firstName = "trainer name";
        String lastName = "trainer name";
        this.mockMvc.perform(post("/trainers/").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(createNewTrainerRequest(email, phoneNumber, firstName, lastName))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.email", is(email)));
    }

    @Test
    @Order(2)
    public void testSaveDuplicatedError() throws Exception {
        String email = "trainer2@gmail.com";
        String phoneNumber = "1234567890";
        String firstName = "trainer name2";
        String lastName = "trainer name2";
        this.mockMvc.perform(post("/trainers/").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(createNewTrainerRequest(email, phoneNumber, firstName, lastName))))
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.message", is(ErrorCode.TRAINER_DUPLICATED.getCode())));
    }

    @Test
    @Order(3)
    public void testSaveNoNameError() throws Exception {
        String email = "trainer3@gmail.com";
        String phoneNumber = "1234567890";
        String firstName = null;
        String lastName = "trainer lastname3";
        this.mockMvc.perform(post("/trainers/").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(createNewTrainerRequest(email, phoneNumber, firstName, lastName))))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.data.[0].field", is("firstName")))
                .andExpect(jsonPath("$.data.[0].message", is("trainers.new.user.first-name.null")));
    }

    @Test
    @Order(4)
    public void testSaveNoPhoneNumberError() throws Exception {
        String email = "trainer3@gmail.com";
        String phoneNumber = null;
        String firstName = "trainer firstName3";
        String lastName = "trainer lastname3";
        this.mockMvc.perform(post("/trainers/").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(createNewTrainerRequest(email, phoneNumber, firstName, lastName))))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.data.[0].field", is("phoneNumber")))
                .andExpect(jsonPath("$.data.[0].message", is("trainers.new.user.phone-number.null")));
    }

    @Test
    @Order(5)
    public void testSaveWrongPhoneNumberError() throws Exception {
        String email = "trainer3@gmail.com";
        String phoneNumber = "123456789"; // number should be 10 digits
        String firstName = "trainer firstName3";
        String lastName = "trainer lastname3";
        this.mockMvc.perform(post("/trainers/").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(createNewTrainerRequest(email, phoneNumber, firstName, lastName))))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.data.[0].field", is("phoneNumber")))
                .andExpect(jsonPath("$.data.[0].message", is("trainers.new.user.phone-number.lenght")));
    }

    @Test
    @Order(6)
    public void testGetTrainerById() throws Exception {
        this.mockMvc.perform(get("/trainers/{id}", 1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.*", hasSize(5)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("trainer@campgladiator.com")));
    }

    @Test
    @Order(7)
    public void testGetTrainerByLastName() throws Exception {
        this.mockMvc.perform(get("/trainers/name/Contender"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].email", is("trainer@campgladiator.com")));
    }
}
