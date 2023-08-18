package com.oshovskii.task.manager.kafka;

import com.oshovskii.common.dto.TaskDto;
import com.oshovskii.task.manager.services.MainTaskHandler;
import com.oshovskii.task.manager.services.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskManagerKafkaListener {
    private final MainTaskHandler mainTaskHandler;

    @KafkaListener(topics = "task")
    public void listen(TaskDto taskDto) {
        log.info("[KAFKA] message [{}]", taskDto);
        mainTaskHandler.workTask(taskDto);
    }
}
