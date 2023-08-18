package com.oshovskii.server.kafka;

import com.oshovskii.common.dto.TaskDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, TaskDto> kafkaTemplate;

    public void send(String topic, String key, TaskDto taskDto) {
        log.info("[KAFKA] {}", taskDto);
        kafkaTemplate.send(topic, key, taskDto);
    }
}
