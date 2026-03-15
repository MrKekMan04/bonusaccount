package ru.vitaliyefimov.bonusaccount.entity.balance;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "balance")
public class Balance {

    @Id
    private String clientId;

    private BigDecimal activeBalanceAmount;

    private BigDecimal holdBalanceAmount;

    private BigDecimal withdrawBalanceAmount;
}
