package ru.vitaliyefimov.bonusaccount.config.kafka;

import lombok.Data;

@Data
public class KafkaProducerProperties {

    private String topic;

    private Integer retryCount = 0;
}
