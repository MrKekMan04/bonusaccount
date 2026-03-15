package ru.vitaliyefimov.bonusaccount.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vitaliyefimov.bonusaccount.entity.account.ClientAccount;
import ru.vitaliyefimov.bonusaccount.entity.account.ClientAccountId;

import java.util.List;

@Repository
public interface ClientAccountRepository extends JpaRepository<ClientAccount, ClientAccountId> {

    List<ClientAccount> findAllByIdClientId(String clientId);
}
