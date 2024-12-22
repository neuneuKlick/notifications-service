package ru.skillbox.notifications_service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Deserializer;
import ru.skillbox.common.events.NotificationEvent;

import java.io.IOException;
import java.util.Map;

public class NotificationEventDeserializer implements Deserializer<NotificationEvent> {

    private final ObjectMapper objectMapper;

    public NotificationEventDeserializer() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public NotificationEvent deserialize(String topic, byte[] data) {
        try {
            if (topic.equals("common-notifications")) {
                NotificationEvent notificationEvent = objectMapper.readValue(data, NotificationEvent.class);
                return notificationEvent;
            } else {
                throw new IllegalArgumentException("Unknown topic: " + topic);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize NotificationEvent", e);
        }
    }

    @Override
    public void close() {
    }
}
