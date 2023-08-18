package com.oshovskii.server.factory.dto;

import com.oshovskii.common.dto.TaskDto;
import com.oshovskii.common.dto.enums.TaskType;

public class TaskDtoFactory {
    public static TaskDto createTaskDto(int index) {
        return new TaskDto(
                "title_" + index,
                TaskType.SIMPLE
        );
    }
}
