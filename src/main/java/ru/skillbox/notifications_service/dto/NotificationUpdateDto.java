package ru.skillbox.notifications_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import ru.skillbox.common.events.NotificationType;

@Data
@Tag(name = "NotificationUpdateDto", description = "Изменение Включено/выключено свойства уведомления")
public class NotificationUpdateDto {

    @Schema(description = "Включено/выключено свойство - получать уведомление конкретного типа")
    private Boolean enable;

    @Schema(description = "Конкретный тип уведомления")
    private NotificationType notificationType;

}
