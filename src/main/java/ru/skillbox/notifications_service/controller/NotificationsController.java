package ru.skillbox.notifications_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.notifications_service.dto.*;
import ru.skillbox.notifications_service.service.notification.NotificationService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "NotificationsController", description = "Контроллер уведомлений")
public class NotificationsController {

    private final NotificationService service;

    @Operation(
            summary = "Получение страниц уведомлений",
            description = "Получает часть (страницу) присланных уведомлений пользователю"
    )
    @GetMapping("/page")
    public Object getPageNotifications(@RequestParam Map<String, String> allParams) {
        return service.getPageNotifications(allParams);
    }

    @Operation(
            summary = "Получение кол-ва присланных уведомлений пользователю",
            description = "Получает актуальное кол-во уведомлений присланные текущему пользователю"
    )
    @GetMapping("/count")
    public NotificationCountDto getCount() {
        return service.getCount();
    }

    @Operation(
            summary = "Изменяет свойство Включено/выключено для уведомления"
    )
    @PutMapping("/readed")
    public String updateReaded() {
        return service.updateReaded();
    }


}
