package ru.vitaliyefimov.bonusaccount.consumer.client;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.vitaliyefimov.bonusaccount.dto.client.ClientEvent;
import ru.vitaliyefimov.bonusaccount.service.client.ClientService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientConsumer {

    private final ClientService clientService;

    @KafkaListener(
        topics = "${consumer.client.topic}",
        autoStartup = "${consumer.client.enabled}",
        concurrency = "${consumer.client.threads}",
        containerFactory = "clientListenerContainerFactory"
    )
    public void consume(List<ClientEvent> clientEvents) {
        clientService.process(clientEvents);
    }
}
