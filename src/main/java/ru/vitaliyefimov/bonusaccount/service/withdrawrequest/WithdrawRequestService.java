package ru.vitaliyefimov.bonusaccount.service.withdrawrequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vitaliyefimov.bonusaccount.dto.card.CardEvent;
import ru.vitaliyefimov.bonusaccount.entity.balance.Balance;
import ru.vitaliyefimov.bonusaccount.entity.withdrawrequest.WithdrawRequest;
import ru.vitaliyefimov.bonusaccount.entity.withdrawrequest.WithdrawRequestStatus;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WithdrawRequestService {

    private final WithdrawRequestDbService withdrawRequestDbService;

    @Transactional(readOnly = true)
    public Boolean existsById(UUID id) {
        return withdrawRequestDbService.findById(id).isPresent();
    }

    public void createFromCardEvent(Balance balance, CardEvent event) {
        withdrawRequestDbService.save(
            new WithdrawRequest()
                .setId(event.id())
                .setClientId(balance.getClientId())
                .setStatus(WithdrawRequestStatus.NEW)
                .setAmount(balance.getActiveBalanceAmount())
                .setAccountNumber(event.accountNumber())
        );
    }
}
