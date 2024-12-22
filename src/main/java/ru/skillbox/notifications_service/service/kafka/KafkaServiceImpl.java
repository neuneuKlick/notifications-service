package ru.skillbox.notifications_service.service.kafka;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.common.events.NotificationDto;
import ru.skillbox.common.events.NotificationType;
import ru.skillbox.notifications_service.repository.NotificationRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class KafkaServiceImpl implements KafkaService {

    private NotificationRepository notificationRepository;
    private List<KafkaHandler> kafkaHandlerList;
    private Map<NotificationType, KafkaHandler> notificationTypeKafkaHandlerMap;

    public KafkaServiceImpl(NotificationRepository notificationRepository, List<KafkaHandler> kafkaHandlerList) {
        this.notificationRepository = notificationRepository;
        this.kafkaHandlerList = kafkaHandlerList;
        this.notificationTypeKafkaHandlerMap = kafkaHandlerList.stream()
                .collect(Collectors.toMap(KafkaHandler::getNotificationTypeFromKafkaConsumer, k -> k));
    }

    @Override
    public void doProcessIncoming(NotificationDto dtoFromKafka) {

        notificationRepository.saveAll(notificationTypeKafkaHandlerMap.get(dtoFromKafka.getNotificationType())
                .getNotificationFromKafkaConsumer(dtoFromKafka));

    }
}
