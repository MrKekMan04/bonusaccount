package ru.vitaliyefimov.bonusaccount.dto.card;

import java.util.UUID;

public record CardEvent(
    UUID id,
    String clientId,
    String accountNumber,
    String loyaltyProgramCode,
    String event
) {

}
