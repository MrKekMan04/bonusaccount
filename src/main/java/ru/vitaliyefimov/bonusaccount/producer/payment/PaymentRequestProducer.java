package ru.vitaliyefimov.bonusaccount.producer.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import ru.vitaliyefimov.bonusaccount.dto.payment.PaymentRequest;
import ru.vitaliyefimov.bonusaccount.entity.withdrawrequest.WithdrawRequest;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class PaymentRequestProducer {

    private static final String RUB_ISO = "643";
    private static final BigDecimal PENNY_MULTIPLIER = BigDecimal.valueOf(100);

    private final KafkaTemplate<String, PaymentRequest> kafkaTemplate;

    @Value("${producer.payment-request.topic}")
    private final String topic;

    public CompletableFuture<SendResult<String, PaymentRequest>> send(
        WithdrawRequest withdrawRequest
    ) {
        return kafkaTemplate.send(
            topic,
            new PaymentRequest(
                withdrawRequest.getId().toString(),
                withdrawRequest.getAmount().multiply(PENNY_MULTIPLIER).longValue(),
                RUB_ISO,
                withdrawRequest.getAccountNumber()
            )
        );
    }
}
