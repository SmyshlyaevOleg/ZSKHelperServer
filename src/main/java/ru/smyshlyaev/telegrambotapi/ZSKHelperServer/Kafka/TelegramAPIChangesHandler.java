package ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Kafka;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TelegramAPIChangesHandler implements EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramAPIChangesHandler.class);
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    @Autowired
    public TelegramAPIChangesHandler(KafkaTemplate < String, String > kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void onOpen () throws Exception {

    }

    @Override
    public void onClosed () throws Exception {

    }

    @Override
    public void onMessage (String s, MessageEvent messageEvent) throws Exception {
       // LOGGER.info(String.format("event data -> %s", messageEvent.getData()));
        kafkaTemplate.send(topic, messageEvent.getData());
    }

    @Override
    public void onComment (String s) throws Exception {

    }

    @Override
    public void onError (Throwable throwable){

    }
}
