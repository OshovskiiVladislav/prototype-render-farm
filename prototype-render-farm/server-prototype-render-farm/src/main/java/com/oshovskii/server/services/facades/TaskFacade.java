package com.oshovskii.server.services.facades;

import com.oshovskii.common.dto.TaskDto;
import com.oshovskii.common.exceptions.implementations.ResourceNotFoundException;
import com.oshovskii.server.kafka.KafkaProducer;
import com.oshovskii.server.mappers.TaskMapper;
import com.oshovskii.server.models.Status;
import com.oshovskii.server.models.Task;

import com.oshovskii.server.models.enums.StatusTitle;
import com.oshovskii.server.services.StatusServiceImpl;
import com.oshovskii.server.services.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskFacade {
    private final TaskService taskService;
    private final KafkaProducer kafkaProducer;

    private final TaskMapper taskMapper;

    private final StatusServiceImpl statusServiceImpl;
    private static final String KAFKA_TOPIC_NAME = "task";
    private static final String KAFKA_TOPIC_KEY = "key";

    @Transactional(readOnly = true)
    public List<TaskDto> findAll() {
        return taskService.findAll()
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskDto findById(Long id) {
        log.info("[findById] with id: {}", id);
        return taskMapper.toDto(taskService.findById(id));
    }

    @Transactional
    public TaskDto createTaskWithUsername(TaskDto taskDto, String username) {
        log.info("[createTaskWithUsername] taskDto: {}, name: {}", taskDto, username);

        Task task = taskMapper.toEntity(taskDto);
        task.setUsername(username);

        Status renderingStatus = statusServiceImpl.findByTitle(StatusTitle.RENDERING);
        task.setStatuses(Collections.singletonList(renderingStatus));

        Task savedTask = taskService.saveTask(task);

        kafkaProducer.send(KAFKA_TOPIC_NAME, KAFKA_TOPIC_KEY, taskMapper.toDto(task));
        return taskMapper.toDto(savedTask);
    }

    @Transactional
    public void changeStatusAfterWork(TaskDto taskDto) {
        Task task = taskService.findByTitle(taskDto.getTitle()).orElseThrow(
                () -> new ResourceNotFoundException("Task with title [" + taskDto.getTitle() + "] not found")
        );
        Status completeStatus = statusServiceImpl.findByTitle(StatusTitle.COMPLETE);
        task.getStatuses().add(completeStatus);

        taskService.saveTask(task);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> findAllTaskDtoByUsername(String username) {
        log.info("[findAllByUsername] username: {}", username);
        List<TaskDto> taskDtos = taskService.findAllByUsername(username)
                .stream()
                .map(taskMapper::toDto)
                .toList();
        log.info("List<TaskDto> findAllTaskDtoByUsername(String username) {} " + taskDtos);
        return taskDtos;
    }

    @Transactional
    public void deleteTaskById(Long id) {
        taskService.deleteById(id);
    }
}
