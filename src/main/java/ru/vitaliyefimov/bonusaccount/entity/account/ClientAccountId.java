package ru.vitaliyefimov.bonusaccount.entity.account;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ClientAccountId implements Serializable {

    private String clientId;

    private String accountNumber;
}
