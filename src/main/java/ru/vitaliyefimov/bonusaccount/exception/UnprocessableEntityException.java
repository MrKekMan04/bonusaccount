package ru.vitaliyefimov.bonusaccount.exception;

public class UnprocessableEntityException extends RestException {

    public UnprocessableEntityException(String errorMessage) {
        super(errorMessage);
    }
}
