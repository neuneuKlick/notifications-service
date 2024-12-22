package ru.skillbox.notifications_service.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.skillbox.notifications_service.AbstractControllerTest;
import ru.skillbox.notifications_service.service.setting.NotificationSettingService;

public class NotificationControllerTest extends AbstractControllerTest {

    @MockBean
    private NotificationSettingService notificationSettingService;

    @Test
    public void whenGetSettings_thenReturnNotificationSetting() throws Exception {}

}
