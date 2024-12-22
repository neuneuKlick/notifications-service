package ru.skillbox.notifications_service.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notification_setting")
public class NotificationSetting {


    @Id
    @Column(columnDefinition = "uuid", updatable = false)
    @Schema(description = "Уникальный идентификатор настроек пользователя")
    private UUID id;

    @Column(name = "enable_post")
    @Schema(description = "Включено/выключено свойство - получать уведомление новостей")
    private Boolean enablePost;

    @Column(name = "enable_post_comment")
    @Schema(description = "Включено/выключено свойство - получать уведомление комментирования новостей")
    private Boolean enablePostComment;

    @Column(name = "enable_comment_comment")
    @Schema(description = "Включено/выключено свойство - получать уведомление ответа на комментарий")
    private Boolean enableCommentComment;

    @Schema(description = "Включено/выключено свойство - получать уведомление запроса в друзья")
    @Column(name = "enable_friend_request")
    private Boolean enableFriendRequest;

    @Column(name = "enable_friend_birthday")
    @Schema(description = "Включено/выключено свойство - получать уведомление день рождения друга")
    private Boolean enableFriendBirthday;

    @Column(name = "enable_message")
    @Schema(description = "Включено/выключено свойство - получать уведомление отправки сообщения")
    private Boolean enableMessage;

    @Column(name = "enable_send_email_message")
    @Schema(description = "Включено/выключено свойство - получать уведомление отправки письма на почту")
    private Boolean enableSendEmailMessage;

    @Column(name = "author_id")
    @Schema(description = "Уникальный идентификатор автора уведомления")
    private UUID authorId;

}
