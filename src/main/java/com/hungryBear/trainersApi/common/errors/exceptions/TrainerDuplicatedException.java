package com.hungryBear.trainersApi.common.errors.exceptions;

import com.hungryBear.trainersApi.common.errors.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class TrainerDuplicatedException extends TrainerException {

  public TrainerDuplicatedException() {
    super(ErrorCode.TRAINER_DUPLICATED);
  }

}
