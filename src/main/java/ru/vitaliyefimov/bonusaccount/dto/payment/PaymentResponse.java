package ru.vitaliyefimov.bonusaccount.dto.payment;

public record PaymentResponse(
    String responseId,
    Integer statusCode,
    String errorMessage
) {

}
