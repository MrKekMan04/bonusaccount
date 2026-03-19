package ru.vitaliyefimov.bonusaccount.config.kafka;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.RETRIES_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;
import static org.springframework.kafka.support.serializer.ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS;
import static org.springframework.kafka.support.serializer.JacksonJsonDeserializer.USE_TYPE_INFO_HEADERS;
import static org.springframework.kafka.support.serializer.JacksonJsonDeserializer.VALUE_DEFAULT_TYPE;
import static org.springframework.kafka.support.serializer.JacksonJsonSerializer.ADD_TYPE_INFO_HEADERS;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;
import org.springframework.util.backoff.FixedBackOff;
import ru.vitaliyefimov.bonusaccount.dto.card.CardEvent;
import ru.vitaliyefimov.bonusaccount.dto.client.ClientEvent;
import ru.vitaliyefimov.bonusaccount.dto.payment.PaymentRequest;
import ru.vitaliyefimov.bonusaccount.dto.payment.PaymentResponse;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({KafkaConsumers.class, KafkaProducers.class})
public class KafkaConfig {

    private final KafkaConsumers kafkaConsumers;
    private final KafkaProducers kafkaProducers;
    @Value("${spring.kafka.bootstrap-servers}")
    private final String bootstrapServers;

    @Bean
    public ConsumerFactory<String, ClientEvent> clientContainerFactory() {
        return new DefaultKafkaConsumerFactory<>(getJsonConsumerProperties(
            kafkaConsumers.getClient(),
            ClientEvent.class
        ));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, ClientEvent>>
    clientListenerContainerFactory(
        ConsumerFactory<String, ClientEvent> factory
    ) {
        KafkaConsumerProperties client = kafkaConsumers.getClient();
        ConcurrentKafkaListenerContainerFactory<String, ClientEvent> listener =
            getKafkaListenerContainerFactory(client, factory);
        listener.setBatchListener(true);
        return listener;
    }

    @Bean
    public ConsumerFactory<String, CardEvent> cardEventContainerFactory() {
        return new DefaultKafkaConsumerFactory<>(getJsonConsumerProperties(
            kafkaConsumers.getCardEvent(),
            CardEvent.class
        ));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, CardEvent>>
    cardEventListenerContainerFactory(
        ConsumerFactory<String, CardEvent> factory
    ) {
        KafkaConsumerProperties cardEvent = kafkaConsumers.getCardEvent();
        ConcurrentKafkaListenerContainerFactory<String, CardEvent> listener =
            getKafkaListenerContainerFactory(cardEvent, factory);
        listener.setBatchListener(true);
        return listener;
    }

    @Bean
    public ConsumerFactory<String, PaymentResponse> paymentResponseContainerFactory() {
        return new DefaultKafkaConsumerFactory<>(getJsonConsumerProperties(
            kafkaConsumers.getPaymentResponse(),
            PaymentResponse.class
        ));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, PaymentResponse>>
    paymentResponseListenerContainerFactory(
        ConsumerFactory<String, PaymentResponse> factory
    ) {
        KafkaConsumerProperties cardEvent = kafkaConsumers.getPaymentResponse();
        ConcurrentKafkaListenerContainerFactory<String, PaymentResponse> listener =
            getKafkaListenerContainerFactory(cardEvent, factory);
        listener.setBatchListener(true);
        return listener;
    }

    @Bean
    public KafkaTemplate<String, PaymentRequest> paymentRequestKafkaTemplate() {
        return new KafkaTemplate<>(
            new DefaultKafkaProducerFactory<>(
                getJsonProducerProperties(kafkaProducers.getPaymentRequest())
            )
        );
    }

    private <T> ConcurrentKafkaListenerContainerFactory<String, T> getKafkaListenerContainerFactory(
        KafkaConsumerProperties consumerProperties,
        ConsumerFactory<String, T> consumerFactory
    ) {
        var listener = new ConcurrentKafkaListenerContainerFactory<String, T>();
        listener.setConcurrency(consumerProperties.getThreads());
        listener.setConsumerFactory(consumerFactory);
        listener.setCommonErrorHandler(getDefaultErrorHandler(consumerProperties.getRetryCount()));
        listener.getContainerProperties().setIdleBetweenPolls(consumerProperties.getIdleBetweenPolls());
        return listener;
    }

    private Map<String, Object> getJsonConsumerProperties(
        KafkaConsumerProperties consumerProperties,
        Class<?> deserializerValueClass
    ) {
        Map<String, Object> config = new HashMap<>();
        config.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(GROUP_ID_CONFIG, consumerProperties.getGroupId());
        config.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(VALUE_DESERIALIZER_CLASS, JacksonJsonDeserializer.class);
        config.put(VALUE_DEFAULT_TYPE, deserializerValueClass);
        config.put(USE_TYPE_INFO_HEADERS, false);
        return config;
    }

    private Map<String, Object> getJsonProducerProperties(
        KafkaProducerProperties producerProperties
    ) {
        Map<String, Object> config = new HashMap<>();
        config.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
        config.put(ADD_TYPE_INFO_HEADERS, false);
        config.put(RETRIES_CONFIG, producerProperties.getRetryCount());
        return config;
    }

    private DefaultErrorHandler getDefaultErrorHandler(Integer retryCount) {
        return new DefaultErrorHandler(null, new FixedBackOff(FixedBackOff.DEFAULT_INTERVAL, retryCount));
    }
}
