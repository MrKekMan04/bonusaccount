package ru.vitaliyefimov.bonusaccount.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vitaliyefimov.bonusaccount.dto.http.Response;
import ru.vitaliyefimov.bonusaccount.exception.UnprocessableEntityException;

import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UnprocessableEntityException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public Response<Void> handleUnprocessableEntityException(UnprocessableEntityException e) {
        return Response.fail(List.of(e.getErrorMessage()));
    }
}
