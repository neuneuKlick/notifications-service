package ru.skillbox.notifications_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.notifications_service.entity.Notification;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    Page<Notification> findAllByReceiverId(UUID receiverId, Pageable pageable);

    Integer countByReceiverId(UUID receiverId);

    void deleteByReceiverId(UUID receiverId);

}
