package com.oshovskii.server.kafka;

import com.oshovskii.common.dto.TaskDto;
import com.oshovskii.server.services.facades.TaskFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RenderFarmKafkaListener {
    private final TaskFacade taskFacade;

    @KafkaListener(topics = "done")
    public void listen(TaskDto taskDto) {
        log.info("[KAFKA] message [{}]", taskDto);
        taskFacade.changeStatusAfterWork(taskDto);
    }
}
