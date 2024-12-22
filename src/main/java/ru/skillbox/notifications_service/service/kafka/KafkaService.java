package ru.skillbox.notifications_service.service.kafka;

import ru.skillbox.common.events.NotificationDto;

public interface KafkaService {

    void doProcessIncoming(NotificationDto dtoFromKafka);

}
