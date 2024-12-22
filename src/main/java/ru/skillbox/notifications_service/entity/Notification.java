package ru.skillbox.notifications_service.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.common.events.NotificationServiceType;
import ru.skillbox.common.events.NotificationType;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @Column(columnDefinition = "uuid", updatable = false)
    @Schema(description = "Уникальный идентификатор уведомления")
    private UUID id;

    @Column(name = "author_id")
    @Schema(description = "Автор создания уведомления")
    private UUID authorId;

    @Schema(description = "Текст содержания уведомления")
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "notification_type")
    @Schema(description = "Тип уведомления")
    private NotificationType notificationType;

    @Column(name = "sent_time")
    @Schema(description = "Время создания уведомления")
    private Instant sentTime;

    @Column(name = "receiver_id")
    @Schema(description = "Уникальный идентификатор получателя уведомления")
    private UUID receiverId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "service_type")
    @Schema(description = "Тип микро-сервиса приславшего уведомление")
    private NotificationServiceType serviceType;

    @Column(name = "event_id")
    @Schema(description = "Уникальный идентификатор события уведомления")
    private UUID eventId;

    @Column(name = "is_read")
    @Schema(description = "Включено/выключено свойство прочитано")
    private boolean isReaded;

}
