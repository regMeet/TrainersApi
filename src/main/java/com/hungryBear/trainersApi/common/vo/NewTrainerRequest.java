package com.hungryBear.trainersApi.common.vo;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class NewTrainerRequest {

    @NotNull(message = "trainers.new.user.email.null")
    @Email(message = "trainers.new.user.email.invalid")
    private String email;

    @NotNull(message = "trainers.new.user.phone-number.null")
    @Pattern(regexp = "[\\d]{10}", message = "trainers.new.user.phone-number.lenght")
    private String phoneNumber;

    @NotNull(message = "trainers.new.user.first-name.null")
    @Size(min = 1, max = 20, message = "trainers.new.user.first-name.lenght")
    private String firstName;

    @NotNull(message = "trainers.new.user.last-name.null")
    @Size(min = 1, max = 20, message = "trainers.new.user.last-name.lenght")
    private String lastName;

}
