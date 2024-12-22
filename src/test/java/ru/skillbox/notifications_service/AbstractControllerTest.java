package ru.skillbox.notifications_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.skillbox.common.events.NotificationServiceType;
import ru.skillbox.common.events.NotificationType;
import ru.skillbox.notifications_service.entity.Notification;
import ru.skillbox.notifications_service.entity.NotificationSetting;

import java.time.Instant;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected Notification createNotification() {
        Notification notification = new Notification(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "content",
                NotificationType
                        .valueOf("LIKE, POST, POST_COMMENT, COMMENT_COMMENT, MESSAGE, " +
                                "FRIEND_REQUEST, FRIEND_BIRTHDAY, FRIEND_APPROVE, FRIEND_BLOCKED, FRIEND_UNBLOCKED, " +
                                "FRIEND_SUBSCRIBE, USER_BIRTHDAY, SEND_EMAIL_MESSAGE"),
                Instant.now(),
                UUID.randomUUID(),
                NotificationServiceType
                        .valueOf("AUTH, ACCOUNT, POST, FRIEND, DIALOG, NOTIFICATION, COUNTRY"),
                UUID.randomUUID(),
                false
        );
        return notification;
    }

    protected NotificationSetting createNotificationSetting() {
        NotificationSetting notificationSetting = new NotificationSetting(
                UUID.randomUUID(),
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                UUID.randomUUID()
        );
        return notificationSetting;
    }



}
