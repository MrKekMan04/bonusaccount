package ru.vitaliyefimov.bonusaccount.service.withdrawrequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vitaliyefimov.bonusaccount.dto.card.CardEvent;
import ru.vitaliyefimov.bonusaccount.entity.balance.Balance;
import ru.vitaliyefimov.bonusaccount.entity.withdrawrequest.WithdrawRequest;
import ru.vitaliyefimov.bonusaccount.entity.withdrawrequest.WithdrawRequestStatus;
import ru.vitaliyefimov.bonusaccount.producer.payment.PaymentRequestProducer;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WithdrawRequestService {

    private static final Duration SEARCH_GAP = Duration.ofDays(2);

    private final WithdrawRequestDbService withdrawRequestDbService;
    private final PaymentRequestProducer paymentRequestProducer;

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

    public void sendWithdrawRequests() {
        List<WithdrawRequest> requestsToSent =
            withdrawRequestDbService.findAllByCreatedDateTimeAndStatus(SEARCH_GAP, WithdrawRequestStatus.NEW);

        requestsToSent.stream()
            .map(paymentRequestProducer::send)
            .forEach(future ->
                future.whenComplete((sr, e) -> {
                    if (e == null) {
                        withdrawRequestDbService.setSentById(
                            UUID.fromString(sr.getProducerRecord().value().requestId())
                        );
                    }
                })
            );
    }
}
