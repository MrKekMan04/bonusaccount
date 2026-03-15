package ru.vitaliyefimov.bonusaccount.service.balance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vitaliyefimov.bonusaccount.repository.balance.BalanceRepository;

@Service
@RequiredArgsConstructor
public class BalanceDbService {

    private final BalanceRepository balanceRepository;
}
