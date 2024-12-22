package ru.skillbox.notifications_service.service.kafka.event;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.skillbox.common.events.NotificationDto;
import ru.skillbox.common.events.NotificationType;
import ru.skillbox.notifications_service.entity.Notification;
import ru.skillbox.notifications_service.entity.NotificationSetting;

import ru.skillbox.notifications_service.mapper.NotificationMapper;
import ru.skillbox.notifications_service.repository.NotificationSettingRepository;
import ru.skillbox.notifications_service.service.kafka.KafkaHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendRequest implements KafkaHandler {

    private final NotificationSettingRepository settingRepository;
    private final NotificationMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(FriendRequest.class);
    @Override
    public List<Notification> getNotificationFromKafkaConsumer(NotificationDto dto) {

        List<Notification> list = new ArrayList<>();
        log.info("getNotificationFromKafkaConsumer FriendRequest start: {}", dto);
        NotificationSetting notificationSetting = settingRepository.findByAuthorId(dto.getReceiverId()).get();
        log.info("getNotificationFromKafkaConsumer FriendRequest after settingRepository: {}", notificationSetting);
        if (notificationSetting.getEnableFriendRequest()) {
            list.add(mapper.toNotification(dto));
        }
        log.info("getNotificationFromKafkaConsumer FriendRequest finish: {}", dto);
        return list;
    }

    @Override
    public NotificationType getNotificationTypeFromKafkaConsumer() {
        return NotificationType.FRIEND_REQUEST;
    }
}
