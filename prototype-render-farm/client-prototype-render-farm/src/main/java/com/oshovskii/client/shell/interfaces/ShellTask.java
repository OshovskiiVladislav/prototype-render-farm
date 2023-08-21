package com.oshovskii.client.shell.interfaces;

import com.oshovskii.common.dto.TaskDto;

import java.util.List;

public interface ShellTask {
    TaskDto createTask(String title, String type);
    List<TaskDto> findAllByUsername();
}
