package com.vending.machine.api;

import com.vending.machine.api.model.ErrorResponse;
import com.vending.machine.application.exception.CoinNotAcceptedException;
import com.vending.machine.application.exception.OldPasswordNotValidException;
import com.vending.machine.application.exception.UserAlreadyExistsException;
import com.vending.machine.application.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(RuntimeException ex) {
        return ErrorResponse.anErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(RuntimeException ex) {
        return ErrorResponse.anErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(value = {OldPasswordNotValidException.class, CoinNotAcceptedException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(RuntimeException ex) {
        return ErrorResponse.anErrorResponse(ex.getMessage());
    }
}
