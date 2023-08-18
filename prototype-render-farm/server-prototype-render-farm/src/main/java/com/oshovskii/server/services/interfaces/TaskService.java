package com.oshovskii.server.services.interfaces;

import com.oshovskii.server.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task saveTask(Task task);
    List<Task> findAll();
    Optional<Task> findByTitle(String title);
    List<Task> findAllByUsername(String nickname);

    Task findById(Long id);

    void deleteById(Long id);
}
