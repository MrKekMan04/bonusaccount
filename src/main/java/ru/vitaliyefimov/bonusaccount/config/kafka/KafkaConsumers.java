package ru.vitaliyefimov.bonusaccount.config.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "consumer")
public class KafkaConsumers {

    private KafkaConsumerProperties client;
}
