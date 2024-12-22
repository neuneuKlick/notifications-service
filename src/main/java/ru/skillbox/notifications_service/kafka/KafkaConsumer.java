package ru.skillbox.notifications_service.kafka;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.skillbox.common.events.CommonEvent;
import ru.skillbox.common.events.NotificationEvent;
import ru.skillbox.common.events.RegUserEvent;
import ru.skillbox.notifications_service.mapper.NotificationMapper;
import ru.skillbox.notifications_service.service.kafka.KafkaService;
import ru.skillbox.notifications_service.service.setting.NotificationSettingService;


@Service
@AllArgsConstructor
public class KafkaConsumer {

    private final KafkaService kafkaService;
    private final NotificationSettingService settingService;

    private final NotificationMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topics}", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactoryNotification")
    public void listenNotificationEvent(NotificationEvent event) {

        kafkaService.doProcessIncoming(mapper.toNotificationDto(event));

        try {
            log.debug("Deserialized NotificationEvent: {}", event);
            if (event.getNotificationData() != null) {
                log.info("Birthday notification for account: {}", event.getNotificationData().getReceiverId());
            } else {
                log.warn("No notification data found in the event.");
            }
        } catch (Exception e) {
            log.error("Error processing birthday notification event", e);
        }
    }

    @KafkaListener(topics = "registration_sending", groupId = "notification-reg-event-group", containerFactory = "regUserEventKafkaListenerContainerFactory")
    public void listenRegistrationEvent(CommonEvent<RegUserEvent> commonEvent) {
        log.info("Received message on topic 'registration_sending': {}", commonEvent);

        try {
            RegUserEvent regUserEvent = commonEvent.getData();

            settingService.createSetting(String.valueOf(regUserEvent.getId()));
            log.info("Create new setting for registration user: {}", regUserEvent);

        } catch (Exception e) {
            log.error("Error processing message from topic 'registration_sending'", e);
        }
    }
}
