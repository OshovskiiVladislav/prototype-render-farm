package com.oshovskii.server.controllers;

import com.oshovskii.common.dto.TaskDto;
import com.oshovskii.lib.response.wrapper.annotations.EnableResponseWrapper;
import com.oshovskii.server.models.wrapper.Wrapper;
import com.oshovskii.server.services.facades.TaskFacade;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@EnableResponseWrapper(wrapperClass = Wrapper.class)
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskFacade taskFacade;
    private static final JwtParser DEFAULT_PARSER = Jwts.parser();
    public static final String CLAIMS_PREFERRED_USERNAME = "preferred_username";

    @GetMapping
    public List<TaskDto> findAll() {
        List<TaskDto> taskDtoList = taskFacade.findAll();
        log.info("[findAll] List tasks dto {}", taskDtoList);
        return taskDtoList;
    }

    @GetMapping("/{id}")
    public TaskDto findById(@PathVariable("id") Long taskId) {
        log.info("[findById] with id: " + taskId);
        return taskFacade.findById(taskId);
    }

    @GetMapping("/username")
    public List<TaskDto> findAllByUsername(@RequestHeader(name = "Authorization", required = false) String authHeader) {
        if (Objects.isNull(authHeader)) {
            return new ArrayList<>();
        }
        log.info("Header Authorization: {}", authHeader);
        List<TaskDto> taskListDto = taskFacade.findAllTaskDtoByUsername(getAutHeaderClaimsUsername(authHeader));
        log.info("[findAllByUsername] List tasks dto {}", taskListDto);
        return taskListDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@RequestBody TaskDto taskDto, @RequestHeader(name = "Authorization") String authHeader) {
        log.info("[createTask] Received TaskDto: {}", taskDto);
        return taskFacade.createTaskWithUsername(taskDto, getAutHeaderClaimsUsername(authHeader));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long taskId) {
        log.info("[deleteById] with id: " + taskId);
        taskFacade.deleteTaskById(taskId);
    }

    private String getAutHeaderClaimsUsername(String authHeader) {
        String token = authHeader.substring(7);
        String[] splitToken = token.split("\\.");
        String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";
        Jwt<?,?> jwt = DEFAULT_PARSER.parse(unsignedToken);
        Claims claims = (Claims) jwt.getBody();
        String userName = (String) claims.get(CLAIMS_PREFERRED_USERNAME);
        log.info("{}", userName);
        return userName;
    }
}
