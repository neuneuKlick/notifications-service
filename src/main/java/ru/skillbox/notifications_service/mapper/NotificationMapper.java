package ru.skillbox.notifications_service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import ru.skillbox.common.events.NotificationDto;
import ru.skillbox.common.events.NotificationEvent;
import ru.skillbox.notifications_service.dto.NotificationsDto;
import ru.skillbox.notifications_service.entity.Notification;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

    public NotificationsDto entityToNotificationDto(Notification notification) {

        NotificationsDto notificationsDto = new NotificationsDto();
        notificationsDto.setTimeStamp(notification.getSentTime());

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setId(notification.getId());
        notificationDto.setAuthorId(notification.getAuthorId());
        notificationDto.setContent(notification.getContent());
        notificationDto.setNotificationType(notification.getNotificationType());
        notificationDto.setSentTime(notification.getSentTime());
        notificationDto.setReceiverId(notification.getReceiverId());
        notificationDto.setServiceType(notification.getServiceType());
        notificationDto.setEventId(notification.getEventId());
        notificationDto.setIsReaded(notification.isReaded());

        notificationsDto.setData(notificationDto);

        return notificationsDto;
    }

    public Notification toNotification(NotificationDto dto) {
        Notification n = new Notification();
        n.setId(UUID.randomUUID());
        n.setAuthorId(dto.getAuthorId());
        n.setContent(dto.getContent());
        n.setNotificationType(dto.getNotificationType());
        n.setSentTime(dto.getSentTime());
        n.setReceiverId(dto.getReceiverId());
        n.setServiceType(dto.getServiceType());
        n.setEventId(dto.getEventId());
        n.setReaded(dto.getIsReaded());
        return n;
    }

    public NotificationDto toNotificationDto(NotificationEvent event) {
        NotificationDto dto = new NotificationDto();
        dto.setId(event.getNotificationData().getId());;
        dto.setAuthorId(event.getNotificationData().getAuthorId());
        dto.setContent(event.getNotificationData().getContent());
        dto.setNotificationType(event.getNotificationData().getNotificationType());
        dto.setSentTime(event.getNotificationData().getSentTime());
        dto.setReceiverId(event.getNotificationData().getReceiverId());
        dto.setServiceType(event.getNotificationData().getServiceType());
        dto.setEventId(event.getNotificationData().getEventId());
        dto.setIsReaded(event.getNotificationData().getIsReaded());
        return dto;
    }


}
