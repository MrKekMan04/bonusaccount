package ru.vitaliyefimov.bonusaccount.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vitaliyefimov.bonusaccount.dto.client.ClientEvent;
import ru.vitaliyefimov.bonusaccount.entity.account.ClientAccount;
import ru.vitaliyefimov.bonusaccount.entity.account.ClientAccountId;
import ru.vitaliyefimov.bonusaccount.entity.client.Client;
import ru.vitaliyefimov.bonusaccount.exception.UnprocessableEntityException;
import ru.vitaliyefimov.bonusaccount.service.account.ClientAccountDbService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientDbService clientDbService;
    private final ClientAccountDbService clientAccountDbService;

    @Transactional
    public void process(List<ClientEvent> clientEvents) {
        clientDbService.upsertAll(fetchClients(clientEvents));
        clientAccountDbService.upsertAll(fetchClientAccounts(clientEvents));
    }

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

    private List<Client> fetchClients(List<ClientEvent> clientEvents) {
        return clientEvents.stream()
            .map(event -> new Client()
                .setClientId(event.clientId())
                .setTagIsBanned(event.tagIsBanned())
            )
            .toList();
    }

    private List<ClientAccount> fetchClientAccounts(List<ClientEvent> clientEvents) {
        return clientEvents.stream()
            .flatMap(clientEvent -> clientEvent.accounts().stream()
                .map(accountEvent -> new ClientAccount()
                    .setId(
                        new ClientAccountId()
                            .setClientId(clientEvent.clientId())
                            .setAccountNumber(accountEvent.accountNumber())
                    )
                    .setLoyaltyProgramCode(accountEvent.loyaltyProgramCode())
                )
            )
            .toList();
    }
}
