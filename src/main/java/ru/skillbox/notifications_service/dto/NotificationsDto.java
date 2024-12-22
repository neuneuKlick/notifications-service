package ru.skillbox.notifications_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.common.events.NotificationDto;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "NotificationsDto", description = "Метаданные уведомлений и время создания")
public class NotificationsDto {

    @Schema(description = "Время когда запросили данные")
    private Instant timeStamp;

    @Schema(description = "Данные полученных уведомлений пользователю")
    private NotificationDto data;

}
