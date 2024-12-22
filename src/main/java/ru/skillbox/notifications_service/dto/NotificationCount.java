package ru.skillbox.notifications_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Tag(name = "NotificationCount", description = "Отвечает за число (кол-во) присланных уведомлений пользователю")
public class NotificationCount {

    @Schema(description = "Кол-во полученных уведомлений текущему пользователю")
    private int count;

}
