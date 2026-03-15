package ru.vitaliyefimov.bonusaccount.entity.client;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client")
public class Client {

    @Id
    private String clientId;

    private Boolean tagIsBanned;
}
