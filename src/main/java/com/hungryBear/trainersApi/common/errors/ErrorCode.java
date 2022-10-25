package com.hungryBear.trainersApi.common.errors;

public enum ErrorCode {
  TRAINER_DUPLICATED("trainers.new-trainer.duplicated"),
  TRAINER_NOT_FOUND("trainers.trainer.not.found"),
  INTERNAL_ERROR("trainers.internal.error");

  private final String code;

  ErrorCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

}
