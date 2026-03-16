package ru.vitaliyefimov.bonusaccount.dto.payment;

public record PaymentRequest(
    String requestId,
    Long amount,
    String currency,
    String accountNumber
) {

}
