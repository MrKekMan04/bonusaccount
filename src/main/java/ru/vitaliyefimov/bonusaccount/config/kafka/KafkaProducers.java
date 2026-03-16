package ru.vitaliyefimov.bonusaccount.config.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "producer")
public class KafkaProducers {

    private KafkaProducerProperties paymentRequest;
}
