package ru.skillbox.notifications_service.service.setting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.common.events.NotificationType;
import ru.skillbox.notifications_service.dto.NotificationSettingDto;
import ru.skillbox.notifications_service.dto.NotificationUpdateDto;
import ru.skillbox.notifications_service.entity.NotificationSetting;
import ru.skillbox.notifications_service.repository.NotificationSettingRepository;
import ru.skillbox.notifications_service.security.SecurityUtils;

import java.util.Optional;
import java.util.UUID;

import static ru.skillbox.notifications_service.security.SecurityUtils.getPrincipalUserId;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSettingServiceImpl implements NotificationSettingService {

    private final NotificationSettingRepository repository;

    public NotificationSettingDto getSettings() {

        NotificationSetting n = repository.findByAuthorId(getPrincipalUserId()).get();

        return NotificationSettingDto.builder()
                .id(n.getId())
                .enablePost(n.getEnablePost())
                .enablePostComment(n.getEnablePostComment())
                .enableCommentComment(n.getEnableCommentComment())
                .enableMessage(n.getEnableMessage())
                .enableFriendRequest(n.getEnableFriendRequest())
                .enableFriendBirthday(n.getEnableFriendBirthday())
                .enableSendEmailMessage(n.getEnableSendEmailMessage())
                .build();
    }

    public boolean createSetting(String authorId) {

        Optional<NotificationSetting> n = repository.findByAuthorId(UUID.fromString(authorId));
        if (n.isEmpty()) {
            repository.save(NotificationSetting.builder()
                    .id(UUID.randomUUID())
                    .enablePost(true)
                    .enablePostComment(true)
                    .enableCommentComment(true)
                    .enableFriendRequest(true)
                    .enableFriendBirthday(true)
                    .enableMessage(true)
                    .enableSendEmailMessage(true)
                    .authorId(UUID.fromString(authorId))
                    .build());
            return true;
        }

        return true;
    }

    public void updateSettings(NotificationUpdateDto dto) {

        NotificationType type = dto.getNotificationType();
        Boolean value = dto.getEnable();

        NotificationSetting oldSetting = repository.findByAuthorId(getPrincipalUserId()).get();
        NotificationSetting newSetting = searchField(oldSetting, value, type);

        repository.save(newSetting);
    }

    private NotificationSetting searchField(NotificationSetting n, Boolean newValue, NotificationType type) {

        switch (type) {
            case POST -> n.setEnablePost(newValue);
            case POST_COMMENT -> n.setEnablePostComment(newValue);
            case COMMENT_COMMENT -> n.setEnableCommentComment(newValue);
            case FRIEND_REQUEST -> n.setEnableFriendRequest(newValue);
            case FRIEND_BIRTHDAY -> n.setEnableFriendBirthday(newValue);
            case MESSAGE -> n.setEnableMessage(newValue);
            case SEND_EMAIL_MESSAGE -> n.setEnableSendEmailMessage(newValue);
        }
        return n;
    }

}
