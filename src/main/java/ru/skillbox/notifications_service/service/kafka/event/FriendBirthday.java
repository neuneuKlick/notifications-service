package ru.skillbox.notifications_service.service.kafka.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.skillbox.common.events.NotificationDto;
import ru.skillbox.common.events.NotificationType;
import ru.skillbox.notifications_service.entity.*;
import ru.skillbox.notifications_service.repository.NotificationSettingRepository;
import ru.skillbox.notifications_service.security.AuthenticationFilter;
import ru.skillbox.notifications_service.service.kafka.KafkaHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FriendBirthday implements KafkaHandler {

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
        for (int i = 0; i < uuidsFriends.size(); i++) {
            NotificationSetting notificationSetting = settingRepository.findByAuthorId(dto.getReceiverId()).get();
            if (notificationSetting.getEnableFriendBirthday()) {
                uuidsFriendsFilteredBySetting.add(uuidsFriends.get(i));
            }
        }

        for (int i = 0; i < uuidsFriendsFilteredBySetting.size(); i++) {
            Notification notification = new Notification();
            notification.setId(UUID.randomUUID());
            notification.setAuthorId(dto.getAuthorId());
            notification.setContent(dto.getContent());
            notification.setNotificationType(dto.getNotificationType());
            notification.setSentTime(dto.getSentTime());
            notification.setReceiverId(uuidsFriendsFilteredBySetting.get(i));
            notification.setServiceType(dto.getServiceType());
            notification.setEventId(dto.getEventId());
            notification.setReaded(dto.getIsReaded());

            list.add(notification);
        }

        return list;
    }

    @Override
    public NotificationType getNotificationTypeFromKafkaConsumer() {
        return NotificationType.FRIEND_BIRTHDAY;
    }


}
