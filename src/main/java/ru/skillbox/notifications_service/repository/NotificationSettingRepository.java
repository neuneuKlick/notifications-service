package ru.skillbox.notifications_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.notifications_service.entity.NotificationSetting;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, UUID> {

    Optional<NotificationSetting> findById(UUID id);
    Optional<NotificationSetting> findByAuthorId(UUID authorId);

}
