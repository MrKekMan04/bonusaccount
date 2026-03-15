package ru.vitaliyefimov.bonusaccount.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vitaliyefimov.bonusaccount.entity.account.ClientAccount;
import ru.vitaliyefimov.bonusaccount.entity.client.Client;
import ru.vitaliyefimov.bonusaccount.exception.UnprocessableEntityException;
import ru.vitaliyefimov.bonusaccount.service.clientaccount.ClientAccountDbService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientDbService clientDbService;
    private final ClientAccountDbService clientAccountDbService;

    @Transactional(readOnly = true)
    public void validateHeavyClient(String clientId) {
        Boolean isBanned = clientDbService.findById(clientId)
            .map(Client::getTagIsBanned)
            .orElse(false);
        if (isBanned) {
            throw new UnprocessableEntityException("Клиент забанен");
        }
        List<ClientAccount> accounts = clientAccountDbService.findAllByClientId(clientId);
        if (!accounts.isEmpty()) {
            throw new UnprocessableEntityException("Клиент тяжелый");
        }
    }
}
