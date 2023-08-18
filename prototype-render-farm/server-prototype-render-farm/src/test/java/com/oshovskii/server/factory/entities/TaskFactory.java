package com.oshovskii.server.factory.entities;

import com.oshovskii.common.dto.enums.TaskType;
import com.oshovskii.server.models.Task;

import java.util.Collections;

import static com.oshovskii.server.factory.entities.StatusFactory.createStatus;

public class TaskFactory {
    public static Task createTask(int index) {
        return new Task(
                (long) index,
                "title_" + index,
                TaskType.SIMPLE,
                "username_" + index,
                Collections.singletonList(createStatus(index))
        );
    }
}
