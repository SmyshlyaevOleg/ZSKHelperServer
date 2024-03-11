package ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Kafka;

import com.launchdarkly.eventsource.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KafkaTelegramBotAPIProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTelegramBotAPIProducer.class);

    private KafkaTemplate<String,Object> kafkaTemplate;

    public KafkaTelegramBotAPIProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    public void sendMessage(Map<String,Object> responseMessage)  {

        Message<Map<String,Object>> message= MessageBuilder
                .withPayload(responseMessage)
                .setHeader(KafkaHeaders.TOPIC,topic)
                .build();

        kafkaTemplate.send(message);
    }

}
