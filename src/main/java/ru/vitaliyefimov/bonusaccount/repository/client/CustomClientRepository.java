package ru.vitaliyefimov.bonusaccount.repository.client;

import ru.vitaliyefimov.bonusaccount.entity.client.Client;

import java.util.Collection;

public interface CustomClientRepository {

    void upsertAll(Collection<Client> clients);
}
