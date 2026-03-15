package ru.vitaliyefimov.bonusaccount.entity.account;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client_account")
public class ClientAccount {

    @EmbeddedId
    private ClientAccountId id;

    private String loyaltyProgramCode;
}
