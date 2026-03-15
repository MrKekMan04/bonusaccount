package ru.vitaliyefimov.bonusaccount.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class RestException extends RuntimeException {

    private final String errorMessage;
}
