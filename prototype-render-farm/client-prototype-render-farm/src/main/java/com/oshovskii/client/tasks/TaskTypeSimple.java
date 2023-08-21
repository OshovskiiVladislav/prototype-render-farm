package com.oshovskii.client.tasks;

import com.oshovskii.common.dto.TaskDto;
import com.oshovskii.common.dto.enums.TaskType;
import org.springframework.stereotype.Component;

@Component
public class TaskTypeSimple implements TaskTypeHandler {
    @Override
    public TaskDto addTaskType(TaskDto taskDto) {
         taskDto.setType(TaskType.SIMPLE);
         return taskDto;
    }

    @Override
    public TaskType getType() {
        return TaskType.SIMPLE;
    }
}
