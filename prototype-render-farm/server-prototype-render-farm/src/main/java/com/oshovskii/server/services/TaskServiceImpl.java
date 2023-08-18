package com.oshovskii.server.services;

import com.oshovskii.common.exceptions.implementations.ResourceNotFoundException;
import com.oshovskii.server.models.Task;
import com.oshovskii.server.repositories.TaskRepository;
import com.oshovskii.server.services.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public Task saveTask(Task task) {
        log.info("[saveTask] Task: {}", task);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findByTitle(String title) {
        log.info("[findByTitle] title: {}", title);
        return taskRepository.findByTitle(title);
    }

    @Override
    public List<Task> findAllByUsername(String username) {
        log.info("[findAllByUsername] username: {}", username);
        return taskRepository.findByUsername(username).orElse(List.of());
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Task with id: " + id + " not found!")
        );
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
