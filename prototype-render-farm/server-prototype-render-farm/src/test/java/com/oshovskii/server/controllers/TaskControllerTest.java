package com.oshovskii.server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oshovskii.server.services.facades.TaskFacade;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.oshovskii.server.factory.dto.TaskDtoFactory.createTaskDto;
import static com.oshovskii.server.jwt.JwtUtils.HEADER_AUTHORIZATION;
import static com.oshovskii.server.jwt.JwtUtils.createToken;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@DisplayName("TaskController test")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskFacade taskFacadeMock;

    private static final String TOKEN_PREFIX_BEARER = "Bearer ";

    @Test
    @DisplayName("findAllByUsername() " +
            "with valid nickname and Principal " +
            "should return List taskDto test")
    void findAllByUsername_validNickname_shouldReturnExpectedListTasks() throws Exception {
        // Config
        val sourceUsername = "source_username";

        val sourceTaskDto1 = createTaskDto(1);
        val sourceTaskDto2 = createTaskDto(2);

        val targetListTaskDto = List.of(sourceTaskDto1, sourceTaskDto2);

        when(taskFacadeMock.findAllTaskDtoByUsername(sourceUsername))
                .thenReturn(targetListTaskDto);

        val targetJson = objectMapper.writeValueAsString(targetListTaskDto);

        // Call and Verify
        mockMvc.perform(get("/api/v1/tasks/username")
                        .header(HEADER_AUTHORIZATION, TOKEN_PREFIX_BEARER + createToken(sourceUsername)))
                .andExpect(content().string(targetJson))
                .andExpect(status().isOk());

        verify(taskFacadeMock).findAllTaskDtoByUsername(sourceUsername);
    }

    @Test
    @DisplayName("createTask() " +
            "with valid TaskDto and Authorization token " +
            "should return List taskDto test")
    void createTask_validTaskDtoAndAuthorizationToken_shouldCreateTask() throws Exception {
        // Config
        val sourceTaskDto = createTaskDto(1);
        val targetTaskDto = createTaskDto(2);
        val sourceUsername = "source_username";

        when(taskFacadeMock.createTaskWithUsername(sourceTaskDto, sourceUsername))
                .thenReturn(targetTaskDto);

        val sourceJson = objectMapper.writeValueAsString(sourceTaskDto);
        val targetJson = objectMapper.writeValueAsString(targetTaskDto);

        // Call and verify
        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceJson)
                        .header(HEADER_AUTHORIZATION, TOKEN_PREFIX_BEARER + createToken(sourceUsername)))
                .andExpect(content().string(targetJson))
                .andExpect(status().isCreated());

        verify(taskFacadeMock).createTaskWithUsername(sourceTaskDto,  sourceUsername);
    }

}
