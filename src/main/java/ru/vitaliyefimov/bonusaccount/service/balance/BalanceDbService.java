package ru.vitaliyefimov.bonusaccount.service.balance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vitaliyefimov.bonusaccount.entity.balance.Balance;
import ru.vitaliyefimov.bonusaccount.repository.balance.BalanceRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BalanceDbService {

    private final BalanceRepository balanceRepository;

    @Transactional(readOnly = true)
    public Optional<Balance> findByClientId(String clientId) {
        return balanceRepository.findById(clientId);
    }
}
