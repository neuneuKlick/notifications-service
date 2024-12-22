package ru.skillbox.notifications_service.kafka;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.skillbox.common.events.NotificationEvent;

@Service
@AllArgsConstructor
public class KafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplateNotification;

    private static final String BIRTHDAY_NOTIFICATION_TOPIC = "common-notifications";

    public void sendBirthdayNotification(NotificationEvent event) {
        log.info("Sending birthday notification with id: {}", event);
        System.out.println("отправил");
        kafkaTemplateNotification.send(BIRTHDAY_NOTIFICATION_TOPIC, event.getEventType(), event);
    }


}


