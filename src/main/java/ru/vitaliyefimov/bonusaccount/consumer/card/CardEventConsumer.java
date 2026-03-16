package ru.vitaliyefimov.bonusaccount.consumer.card;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.vitaliyefimov.bonusaccount.dto.card.CardEvent;
import ru.vitaliyefimov.bonusaccount.service.card.CardEventProcessor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CardEventConsumer {

    private final CardEventProcessor cardService;

    @KafkaListener(
        topics = "${consumer.card-event.topic}",
        autoStartup = "${consumer.card-event.enabled}",
        concurrency = "${consumer.card-event.threads}",
        containerFactory = "cardEventListenerContainerFactory"
    )
    public void consume(List<CardEvent> cardEvents) {
        cardService.process(cardEvents);
    }
}
