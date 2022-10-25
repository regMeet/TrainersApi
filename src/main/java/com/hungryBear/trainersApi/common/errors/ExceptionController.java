package com.hungryBear.trainersApi.common.errors;

import com.hungryBear.trainersApi.common.errors.exceptions.TrainerDuplicatedException;
import com.hungryBear.trainersApi.common.errors.exceptions.TrainerNotFoundException;
import com.hungryBear.trainersApi.common.vo.ErrorVO;
import com.hungryBear.trainersApi.common.vo.ValidationErrorVO;
import com.hungryBear.trainersApi.common.vo.ValidationErrorVO.ValidationErrorDataVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(TrainerDuplicatedException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody ErrorVO userDuplicated(TrainerDuplicatedException e) {
        return new ErrorVO(e.getMessage());
    }

    @ExceptionHandler(TrainerNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorVO userDuplicated(TrainerNotFoundException e) {
        return new ErrorVO(e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ValidationErrorVO validations(MethodArgumentNotValidException e) {
        ValidationErrorVO validationErrorVO = new ValidationErrorVO();

        List<ValidationErrorDataVO> errorList = new ArrayList<ValidationErrorDataVO>();

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            ValidationErrorDataVO error = new ValidationErrorDataVO();
            error.setField(fieldError.getField());
            error.setMessage(fieldError.getDefaultMessage());
            errorList.add(error);
        }

        ObjectError globalError = e.getBindingResult().getGlobalError();
        if (globalError != null) {
            ValidationErrorDataVO error = new ValidationErrorDataVO();
            error.setField(globalError.getObjectName());
            error.setMessage(globalError.getDefaultMessage());
            errorList.add(error);
        }

        validationErrorVO.setData(errorList);
        return validationErrorVO;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorVO catchRestOfExceptions(Exception e) {
        log.error("An exception has occurred", e);
        return new ErrorVO(ErrorCode.INTERNAL_ERROR.getCode());
    }

}
