package ru.vitaliyefimov.bonusaccount.repository.account;

import ru.vitaliyefimov.bonusaccount.entity.account.ClientAccount;

import java.util.Collection;

public interface CustomClientAccountRepository {

    void upsertAll(Collection<ClientAccount> accounts);
}
