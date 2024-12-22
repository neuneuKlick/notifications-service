package ru.skillbox.notifications_service.service.kafka;

import ru.skillbox.common.events.NotificationDto;
import ru.skillbox.common.events.NotificationType;
import ru.skillbox.notifications_service.entity.Notification;

import java.util.List;


public interface KafkaHandler {

    List<Notification> getNotificationFromKafkaConsumer(NotificationDto dtoFromKafkaConsumer);

    NotificationType getNotificationTypeFromKafkaConsumer();

}
