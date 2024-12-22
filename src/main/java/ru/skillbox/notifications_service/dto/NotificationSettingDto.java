package ru.skillbox.notifications_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@Tag(name = "NotificationSettingDto", description = "Свойства Включено/выключено настроек уведомлений")
public class NotificationSettingDto {

    @Schema(description = "Уникальный идентификатор настроек пользователя")
    private UUID id;

    @Schema(description = "Включено/выключено свойство - получать уведомление новостей")
    private boolean enablePost;

    @Schema(description = "Включено/выключено свойство - получать уведомление комментирования новостей")
    private boolean enablePostComment;

    @Schema(description = "Включено/выключено свойство - получать уведомление ответа на комментарий")
    private boolean enableCommentComment;

    @Schema(description = "Включено/выключено свойство - получать уведомление запроса в друзья")
    private boolean enableFriendRequest;

    @Schema(description = "Включено/выключено свойство - получать уведомление день рождения друга")
    private boolean enableFriendBirthday;

    @Schema(description = "Включено/выключено свойство - получать уведомление отправки сообщения")
    private boolean enableMessage;

    @Schema(description = "Включено/выключено свойство - получать уведомление отправки сообщения")
    private boolean enableSendEmailMessage;

}
