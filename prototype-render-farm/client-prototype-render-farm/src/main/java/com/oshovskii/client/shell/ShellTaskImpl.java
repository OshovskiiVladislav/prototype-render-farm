package com.oshovskii.client.shell;

import com.oshovskii.client.model.TaskTypeManager;
import com.oshovskii.client.shell.interfaces.ShellAuth;
import com.oshovskii.client.shell.interfaces.ShellTask;
import com.oshovskii.client.tasks.TaskTypeHandler;
import com.oshovskii.common.dto.TaskDto;
import com.oshovskii.common.dto.enums.TaskType;
import com.oshovskii.common.exceptions.implementations.ResourceNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class ShellTaskImpl implements ShellTask {
    private final WebClient.Builder webClientBuilder;

    private final ShellAuth shellAuth;
    private final TaskTypeManager taskTypeManager;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    @Value("${prototype.render.farm.endpoints.tasks}")
    private String prototypeRenderFarmTasksEndpoints;

    @Value("${prototype.render.farm.endpoints.tasks-by-username}")
    private String prototypeRenderFarmTasksByUsernameEndpoints;

    public ShellTaskImpl(WebClient.Builder webClientBuilder,
                         ShellAuth shellAuth, List<TaskTypeHandler> listTaskTypeHandlers) {
        this.webClientBuilder = webClientBuilder;
        this.shellAuth = shellAuth;
        this.typeTaskTypeHandlerMap = listTaskTypeHandlers.stream()
                .collect(Collectors.toMap(TaskTypeHandler::getType, Function.identity()));
    }

    @Override
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    @ShellMethod(value = "Create Task command", key = {"createTask", "cTask", "ct"})
    public TaskDto createTask(@ShellOption(value = "--t") String title,
                              @ShellOption(value = "--type") String taskType) {

        TaskTypeHandler taskTypeHandler = taskTypeManager.getByType(taskType);

        TaskDto taskDto = taskTypeHandler.addTaskType(new TaskDto(null, title, null));

        log.info("[createTask] title: {}, type: {}", title, taskDto.getType());

        return webClientBuilder.build().post()
                .uri(prototypeRenderFarmTasksEndpoints)
                .bodyValue(taskDto)
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER_PREFIX + shellAuth.getCurrentAccessToken())
                .retrieve()
                .bodyToMono(TaskDto.class)
                .block();
    }

    @Override
    @CircuitBreaker(name = "render-farm", fallbackMethod = "fallbackMethod")
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    @ShellMethod(value = "Find all tasks by username command",
            key = {"findAllTaskByUsername", "AllTaskByUsername", "fltu"}
    )
    public List<TaskDto> findAllByUsername() {
        log.info("findAllByUsername() method called");

        List<TaskDto> taskDtoList = webClientBuilder.build().get()
                .uri(prototypeRenderFarmTasksByUsernameEndpoints)
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER_PREFIX + shellAuth.getCurrentAccessToken())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TaskDto>>() {
                })
                .block();
        log.info("[findAllByNickname] List tasks dto {}", taskDtoList);
        return taskDtoList;
    }

    public List<TaskDto> fallbackMethod(RuntimeException runtimeException) {
        log.info("Couldn't get a response... Executing Fallback logic");
        return List.of();
    }

    private Availability isPublishEventCommandAvailable() {
        return shellAuth.getCurrentAccessToken() == null ?
                Availability.unavailable("Requires authentication performed first") : Availability.available();
    }
}
