package ru.skillbox.notifications_service.service.notification;

import org.springframework.data.domain.Page;
import ru.skillbox.notifications_service.dto.NotificationCountDto;
import ru.skillbox.notifications_service.dto.NotificationsDto;

import java.util.Map;

public interface NotificationService {

    Page<NotificationsDto> getPageNotifications(Map<String, String> allParams);

    NotificationCountDto getCount();

    String updateReaded();

}
