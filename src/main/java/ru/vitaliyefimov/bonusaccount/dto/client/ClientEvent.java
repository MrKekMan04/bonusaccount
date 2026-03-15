package ru.vitaliyefimov.bonusaccount.dto.client;

import java.util.List;

public record ClientEvent(
    String clientId,
    Boolean tagIsBanned,
    List<ClientAccountRecord> accounts
) {

    public record ClientAccountRecord(
        String accountNumber,
        String loyaltyProgramCode
    ) {

    }
}
