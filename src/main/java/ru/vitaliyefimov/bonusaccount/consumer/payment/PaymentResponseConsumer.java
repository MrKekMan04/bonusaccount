package ru.vitaliyefimov.bonusaccount.consumer.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.vitaliyefimov.bonusaccount.dto.payment.PaymentResponse;
import ru.vitaliyefimov.bonusaccount.service.payment.PaymentService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentResponseConsumer {

    private final PaymentService paymentService;

    @KafkaListener(
        topics = "${consumer.payment-response.topic}",
        autoStartup = "${consumer.payment-response.enabled}",
        concurrency = "${consumer.payment-response.threads}",
        containerFactory = "paymentResponseListenerContainerFactory"
    )
    public void process(List<PaymentResponse> paymentResponses) {
        paymentService.process(paymentResponses);
    }
}
