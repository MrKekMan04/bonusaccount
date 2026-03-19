package ru.vitaliyefimov.bonusaccount.service.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vitaliyefimov.bonusaccount.dto.payment.PaymentResponse;
import ru.vitaliyefimov.bonusaccount.entity.withdrawrequest.WithdrawRequest;
import ru.vitaliyefimov.bonusaccount.entity.withdrawrequest.WithdrawRequestStatus;
import ru.vitaliyefimov.bonusaccount.service.balance.BalanceService;
import ru.vitaliyefimov.bonusaccount.service.withdrawrequest.WithdrawRequestService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final WithdrawRequestService withdrawRequestService;
    private final BalanceService balanceService;

    @Transactional
    public void process(List<PaymentResponse> paymentResponses) {
        List<WithdrawRequest> unprocessedWithdrawRequests = withdrawRequestService.findAllUnprocessedByIdIn(
            paymentResponses.stream()
                .map(PaymentResponse::responseId)
                .map(UUID::fromString)
                .toList()
        );

        Map<String, PaymentResponse> paymentResponseById = paymentResponses.stream()
            .collect(Collectors.toMap(
                PaymentResponse::responseId,
                Function.identity(),
                (_, v2) -> v2
            ));

        unprocessedWithdrawRequests
            .forEach(withdrawRequest -> {
                if (paymentResponseById.get(withdrawRequest.getId().toString()).statusCode() == 0) {
                    withdrawRequest.setStatus(WithdrawRequestStatus.SUCCESS);
                    balanceService.withdraw(withdrawRequest);
                } else {
                    withdrawRequest.setStatus(WithdrawRequestStatus.FAILED);
                    balanceService.refund(withdrawRequest);
                }
                withdrawRequestService.save(withdrawRequest);
            });
    }
}
