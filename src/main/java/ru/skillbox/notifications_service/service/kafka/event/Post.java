package ru.skillbox.notifications_service.service.kafka.event;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.skillbox.common.events.NotificationDto;
import ru.skillbox.common.events.NotificationType;
import ru.skillbox.notifications_service.entity.Notification;
import ru.skillbox.notifications_service.entity.NotificationSetting;
import ru.skillbox.notifications_service.repository.NotificationSettingRepository;
import ru.skillbox.notifications_service.security.AuthenticationFilter;
import ru.skillbox.notifications_service.service.kafka.KafkaHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Post implements KafkaHandler {

    private final NotificationSettingRepository settingRepository;
    private final WebClient webClient;
    private final AuthenticationFilter authenticationFilter;

    @Override
    public List<Notification> getNotificationFromKafkaConsumer(NotificationDto dto) {

        List<Notification> list = new ArrayList<>();

            Mono<ArrayList<UUID>> uuids = webClient.get()
                    .uri("http://79.174.80.223:8085/api/v1/friends/friendId/{id}", dto.getAuthorId())
                    .header("Authorization", "Bearer " + authenticationFilter.tokenAuth)
                    .retrieve()
                    .bodyToFlux(UUID.class)
                    .collectList()
                    .map(ArrayList::new);

        ArrayList<UUID> uuidsFriends = uuids.block();

        List<UUID> uuidsFriendsFilteredBySetting = new ArrayList<>();
        for (UUID friendId : uuidsFriends) {
            Optional<NotificationSetting> currentSettingsOptional = settingRepository.findByAuthorId(friendId);
            currentSettingsOptional.ifPresent(notificationSetting -> {
                if (notificationSetting.getEnablePost()) {
                    uuidsFriendsFilteredBySetting.add(friendId);
                }
            });
        }

        for (UUID friendId : uuidsFriendsFilteredBySetting) {
            Notification notification = new Notification();
            notification.setId(UUID.randomUUID());
            notification.setAuthorId(dto.getAuthorId());
            notification.setContent(dto.getContent());
            notification.setNotificationType(dto.getNotificationType());
            notification.setSentTime(dto.getSentTime());
            notification.setReceiverId(friendId);
            notification.setServiceType(dto.getServiceType());
            notification.setEventId(dto.getEventId());
            notification.setReaded(dto.getIsReaded());

            list.add(notification);
        }

        return list;
    }

    @Override
    public NotificationType getNotificationTypeFromKafkaConsumer() {
        return NotificationType.POST;
    }
}
