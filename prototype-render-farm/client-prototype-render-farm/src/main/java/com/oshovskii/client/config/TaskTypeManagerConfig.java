package com.oshovskii.client.config;

import com.oshovskii.client.model.TaskTypeManager;
import com.oshovskii.client.tasks.TaskTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class TaskTypeManagerConfig {

    @Bean
    public TaskTypeManager holder(List<TaskTypeHandler> handlers) {
        log.debug("[{}]", handlers);

        return new TaskTypeManager(handlers.stream()
                .collect(Collectors.toMap(TaskTypeHandler::getType, Function.identity())));
    }

}
