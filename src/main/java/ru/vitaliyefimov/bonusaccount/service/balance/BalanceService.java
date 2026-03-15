package ru.vitaliyefimov.bonusaccount.service.balance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceDbService balanceDbService;
}
