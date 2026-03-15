package ru.vitaliyefimov.bonusaccount.config.kafka;

import lombok.Data;

@Data
public class KafkaConsumerProperties {

    private String topic;

    private String groupId;

    private Boolean enabled;

    private Integer threads = 1;

    private Integer retryCount = 0;

    private Integer idleBetweenPolls = 0;
}
