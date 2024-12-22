package ru.skillbox.notifications_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.notifications_service.dto.NotificationSettingDto;
import ru.skillbox.notifications_service.dto.NotificationUpdateDto;
import ru.skillbox.notifications_service.service.setting.NotificationSettingService;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "NotificationSettingController", description = "Контроллер настроек уведомлений")
public class NotificationSettingController {

    private final NotificationSettingService service;

    @Operation(
            summary = "Получение настроек уведомлений пользователя",
            description = "Получает актуальные настройки пользователя"
    )
    @GetMapping("/settings")
    public NotificationSettingDto getSettings() {
        return service.getSettings();
    }

    @Operation(
            summary = "Создание настроек уведомлений пользователя",
            description = "Создаёт настройки для нового пользователя со всеми включенными уведомлениями по умолчанию"
    )
    @PostMapping("/settings")
    public boolean createSettings(@RequestParam("id") String authorId) {
        return service.createSetting(authorId);
    }

    @Operation(
            summary = "Изменение настроек уведомлений пользователя",
            description = "Изменяет свойство Включено/выключено для контректного типа уведомления"
    )
    @PutMapping(value = "/settings", produces = APPLICATION_JSON_VALUE)
    public void updateSettings(@RequestBody NotificationUpdateDto dto) {
        service.updateSettings(dto);
    }

}
