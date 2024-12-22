package ru.skillbox.notifications_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
@Tag(name = "NotificationCountDto", description = "Метаданные о кол-ве уведомлений и время когда сделался запрос")
public class NotificationCountDto {

    @Schema(description = "Время когда запросили данные")
    private Instant timestamp;

    @Schema(description = "Данные о кол-ве полученных уведомлений пользователю")
    private NotificationCount data;

}
