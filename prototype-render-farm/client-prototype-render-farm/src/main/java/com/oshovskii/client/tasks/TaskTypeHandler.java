package com.oshovskii.client.tasks;

import com.oshovskii.common.dto.TaskDto;
import com.oshovskii.common.dto.enums.TaskType;

public interface TaskTypeHandler {
    TaskDto addTaskType(TaskDto taskDto);
    TaskType getType();
}
