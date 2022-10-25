package com.hungryBear.trainersApi.common.errors.exceptions;

import com.hungryBear.trainersApi.common.errors.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TrainerNotFoundException extends TrainerException {

  public TrainerNotFoundException() {
    super(ErrorCode.TRAINER_NOT_FOUND);
  }

}
