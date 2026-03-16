package ru.vitaliyefimov.bonusaccount.service.balance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vitaliyefimov.bonusaccount.dto.balance.AccrueBonusesRequest;
import ru.vitaliyefimov.bonusaccount.dto.balance.GetBalanceResponse;
import ru.vitaliyefimov.bonusaccount.dto.card.CardEvent;
import ru.vitaliyefimov.bonusaccount.entity.balance.Balance;
import ru.vitaliyefimov.bonusaccount.exception.UnprocessableEntityException;
import ru.vitaliyefimov.bonusaccount.service.client.ClientService;
import ru.vitaliyefimov.bonusaccount.service.withdrawrequest.WithdrawRequestService;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceDbService balanceDbService;
    private final ClientService clientService;
    private final WithdrawRequestService withdrawRequestService;

    @Transactional
    public void accrueBonuses(AccrueBonusesRequest request) {
        clientService.validateHeavyClient(request.clientId());
        Balance balance = balanceDbService.findByClientId(request.clientId())
            .orElseGet(() -> new Balance()
                .setClientId(request.clientId())
                .setActiveBalanceAmount(BigDecimal.ZERO)
                .setHoldBalanceAmount(BigDecimal.ZERO)
                .setWithdrawBalanceAmount(BigDecimal.ZERO)
            );
        balance.setActiveBalanceAmount(
            balance.getActiveBalanceAmount().add(request.amount())
        );
        balanceDbService.save(balance);
    }

    public GetBalanceResponse getBalance(String clientId) {
        Balance balance = balanceDbService.findByClientId(clientId)
            .orElseThrow(() -> new UnprocessableEntityException("Баланс не существует"));

        return new GetBalanceResponse(
            balance.getActiveBalanceAmount().add(balance.getHoldBalanceAmount()),
            balance.getWithdrawBalanceAmount()
        );
    }

    @Transactional
    public void withdraw(CardEvent event) {
        Optional<Balance> balanceOptional = balanceDbService.findByClientId(event.clientId());
        if (balanceOptional.isEmpty() || withdrawRequestService.existsById(event.id())) {
            return;
        }
        Balance balance = balanceOptional.get();
        withdrawRequestService.createFromCardEvent(balance, event);
        balanceDbService.save(
            balance
                .setHoldBalanceAmount(
                    balance.getHoldBalanceAmount().add(balance.getActiveBalanceAmount())
                )
                .setActiveBalanceAmount(BigDecimal.ZERO)
        );
    }
}
