package ru.skillbox.notifications_service.service.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.notifications_service.dto.NotificationCount;
import ru.skillbox.notifications_service.dto.NotificationCountDto;
import ru.skillbox.notifications_service.dto.NotificationsDto;
import ru.skillbox.notifications_service.entity.Notification;
import ru.skillbox.notifications_service.mapper.NotificationMapper;
import ru.skillbox.notifications_service.repository.NotificationRepository;
import ru.skillbox.notifications_service.security.SecurityUtils;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static ru.skillbox.notifications_service.security.SecurityUtils.getPrincipalUserId;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper mapper;

    @Override
    @Transactional
    public Page<NotificationsDto> getPageNotifications(Map<String, String> allParams) {

        int size = 5;
        if (allParams.containsKey("size")) {
            size = Integer.parseInt(allParams.get("size"));}
        int page = 0;
        if (allParams.containsKey("page")) {
            page = Integer.parseInt(allParams.get("page"));}

        String currentUserId = SecurityUtils.getCurrentUserId();
        Sort sort = Sort.by(Sort.Direction.DESC, "sentTime");

        Page<Notification> notificationPage = notificationRepository
                .findAllByReceiverId(getPrincipalUserId(), PageRequest.of(page, size, sort));

        notificationRepository.deleteByReceiverId(UUID.fromString(currentUserId));

        return notificationPage.map(mapper::entityToNotificationDto);
    }

    @Override
    public NotificationCountDto getCount() {

        return new NotificationCountDto(Instant.now(),
                new NotificationCount(notificationRepository
                        .countByReceiverId(getPrincipalUserId())));
    }

    @Override
    public String updateReaded() {
        return null;
    }

}
