package ru.skillbox.notifications_service.service.setting;

import ru.skillbox.notifications_service.dto.NotificationSettingDto;
import ru.skillbox.notifications_service.dto.NotificationUpdateDto;

public interface NotificationSettingService {

    NotificationSettingDto getSettings();
    boolean createSetting(String authorId);
    void updateSettings(NotificationUpdateDto dto);

}
