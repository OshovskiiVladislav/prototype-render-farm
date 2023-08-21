package com.oshovskii.client.tasks;

import com.oshovskii.common.dto.TaskDto;
import com.oshovskii.common.dto.enums.TaskType;
import org.springframework.stereotype.Component;

@Component
public class TaskTypeHard implements TaskTypeHandler {
    @Override
    public TaskDto addTaskType(TaskDto taskDto) {
        taskDto.setType(TaskType.HARD);
        return taskDto;
    }

    @Override
    public TaskType getType() {
        return TaskType.HARD;
    }
}
