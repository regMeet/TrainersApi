package com.hungryBear.trainersApi.common.errors.exceptions;

import com.hungryBear.trainersApi.common.errors.ErrorCode;

public class TrainerException extends Exception {
  private ErrorCode errorCode;

  public TrainerException(ErrorCode errorCode) {
    super(errorCode.getCode());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

}
