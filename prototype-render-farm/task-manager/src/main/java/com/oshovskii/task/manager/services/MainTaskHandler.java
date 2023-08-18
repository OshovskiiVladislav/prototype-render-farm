package com.oshovskii.task.manager.services;

import com.oshovskii.common.dto.TaskDto;
import com.oshovskii.common.exceptions.implementations.IncorrectTaskDataException;
import com.oshovskii.task.manager.services.interfaces.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Slf4j
@Service
public class MainTaskHandler {

    @Autowired
    private List<TaskService> tasks;

    public void workTask(TaskDto taskDto) {
        if (taskDto.getType() == null) {
            throw new IncorrectTaskDataException(format("Incorrect TaskDTO type: %s", taskDto.getType()));
        }
        log.info("[workTask()] TASK received {}", taskDto);
        tasks.forEach(taskService -> taskService.workTask(taskDto));
    }
}
