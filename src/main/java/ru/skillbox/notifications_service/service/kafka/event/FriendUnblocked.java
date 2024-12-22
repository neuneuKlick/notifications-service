package ru.skillbox.notifications_service.service.kafka.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skillbox.common.events.NotificationDto;
import ru.skillbox.common.events.NotificationType;
import ru.skillbox.notifications_service.entity.Notification;
import ru.skillbox.notifications_service.mapper.NotificationMapper;
import ru.skillbox.notifications_service.service.kafka.KafkaHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendUnblocked implements KafkaHandler {

    private final NotificationMapper mapper;

    @Override
    public List<Notification> getNotificationFromKafkaConsumer(NotificationDto dto) {
        List<Notification> list = new ArrayList<>();

        list.add(mapper.toNotification(dto));

        return list;
    }

    @Override
    public NotificationType getNotificationTypeFromKafkaConsumer() {
        return NotificationType.FRIEND_UNBLOCKED;
    }
}
