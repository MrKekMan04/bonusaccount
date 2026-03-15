package ru.vitaliyefimov.bonusaccount.service.clientaccount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vitaliyefimov.bonusaccount.entity.account.ClientAccount;
import ru.vitaliyefimov.bonusaccount.repository.account.ClientAccountRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientAccountDbService {

    private final ClientAccountRepository clientAccountRepository;

    @Transactional(readOnly = true)
    public List<ClientAccount> findAllByClientId(String clientId) {
        return clientAccountRepository.findAllByIdClientId(clientId);
    }
}
