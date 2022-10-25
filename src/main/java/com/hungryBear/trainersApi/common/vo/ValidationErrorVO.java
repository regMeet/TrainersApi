package com.hungryBear.trainersApi.common.vo;

import lombok.Data;

import java.util.List;

@Data
public class ValidationErrorVO {
    private List<ValidationErrorDataVO> data;

    @Data
    public static class ValidationErrorDataVO {
        private String field;
        private String message;
    }
}