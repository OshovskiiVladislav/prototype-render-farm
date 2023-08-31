package com.oshovskii.client.model;

import com.oshovskii.client.tasks.TaskTypeHandler;
import com.oshovskii.common.dto.enums.TaskType;
import com.oshovskii.common.exceptions.implementations.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Locale;
import java.util.Map;

import static java.lang.String.format;

@RequiredArgsConstructor
public class TaskTypeManager {

    private final Map<TaskType, TaskTypeHandler> map;

    public TaskTypeHandler getByType(String type) {
        return map.get(getValidOrThrow(type));
    }

    private TaskType getValidOrThrow(String type) {
        try {
            return TaskType.valueOf(type.toUpperCase(Locale.ENGLISH));
        } catch (Exception e) {
            throw new ResourceNotFoundException(format("Task type: %s not supported", type));
        }
    }

}
