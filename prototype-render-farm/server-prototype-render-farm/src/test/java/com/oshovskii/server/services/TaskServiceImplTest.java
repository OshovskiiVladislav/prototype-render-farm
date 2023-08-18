package com.oshovskii.server.services;

import com.oshovskii.server.repositories.TaskRepository;
import com.oshovskii.server.services.interfaces.TaskService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.oshovskii.server.factory.entities.TaskFactory.createTask;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(TaskServiceImpl.class)
@DisplayName("TaskServiceImpl test")
class TaskServiceImplTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepositoryMock;

    @Test
    @DisplayName("saveTask() " +
            "with valid Task " +
            "should return saved Task test")
    void saveTask_validTask_shouldReturnSavedTask() {
        // Config
        val sourceTask = createTask(1);
        val targetTask = createTask(2);

        when(taskRepositoryMock.save(sourceTask))
                .thenReturn(targetTask);

        // Call
        val expectedTask = taskService.saveTask(sourceTask);

        // Verify
        verify(taskRepositoryMock).save(sourceTask);
        assertEquals(expectedTask, targetTask);
    }

    @Test
    @DisplayName("findByTitle() " +
            "with valid title " +
            "should return expected task test")
    void findByTitle_validTitle_shouldReturnExpectedTask() {
        // Config
        val sourceTitle = "title";

        val sourceTask = createTask(1);
        sourceTask.setTitle(sourceTitle);

        val targetTask = createTask(2);
        sourceTask.setTitle(sourceTitle);

        when(taskRepositoryMock.findByTitle(sourceTitle))
                .thenReturn(Optional.of(targetTask));

        // Call
        val expectedTask = taskService.findByTitle(sourceTitle);

        // Verify
        verify(taskRepositoryMock).findByTitle(sourceTitle);
        assertTrue(expectedTask.isPresent());
        assertEquals(expectedTask.get(), targetTask);
    }

    @Test
    @DisplayName("findAllByUsername() " +
            "with valid nickname " +
            "should return expected list of tasks test")
    void findAllByNickname_validNickname_shouldReturnTask() {
        // Config
        val sourceUsername = "username_test";

        val sourceTask1 = createTask(1);
        sourceTask1.setUsername(sourceUsername);

        val sourceTask2 = createTask(2);
        sourceTask2.setUsername(sourceUsername);

        val targetListTasks = List.of(sourceTask1, sourceTask2);

        when(taskRepositoryMock.findByUsername(sourceUsername))
                .thenReturn(Optional.of(targetListTasks));

        // Call
        val expectedTask = taskService.findAllByUsername(sourceUsername);

        // Verify
        verify(taskRepositoryMock).findByUsername(sourceUsername);
        assertEquals(expectedTask, targetListTasks);
    }
}
