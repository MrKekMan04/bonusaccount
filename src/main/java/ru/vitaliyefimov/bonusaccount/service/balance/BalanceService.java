package ru.vitaliyefimov.bonusaccount.service.balance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vitaliyefimov.bonusaccount.dto.balance.GetBalanceResponse;
import ru.vitaliyefimov.bonusaccount.entity.balance.Balance;
import ru.vitaliyefimov.bonusaccount.exception.UnprocessableEntityException;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceDbService balanceDbService;

    public GetBalanceResponse getBalance(String clientId) {
        Balance balance = balanceDbService.findByClientId(clientId)
            .orElseThrow(() -> new UnprocessableEntityException("Баланс не существует"));

        return new GetBalanceResponse(
            balance.getActiveBalanceAmount().add(balance.getHoldBalanceAmount()),
            balance.getWithdrawBalanceAmount()
        );
    }
}
