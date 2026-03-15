package ru.vitaliyefimov.bonusaccount.service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vitaliyefimov.bonusaccount.entity.client.Client;
import ru.vitaliyefimov.bonusaccount.repository.client.ClientRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientDbService {

    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Optional<Client> findById(String clientId) {
        return clientRepository.findById(clientId);
    }

    @Transactional
    public void upsertAll(Collection<Client> clients) {
        clientRepository.upsertAll(clients);
    }
}
