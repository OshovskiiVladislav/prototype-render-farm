package com.oshovskii.server.mappers;

import com.oshovskii.common.dto.TaskDto;
import com.oshovskii.server.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target="type", source="taskDto.type")
    Task toEntity(TaskDto taskDto);

    @Mapping(target="type", source="task.type")
    TaskDto toDto(Task task);
}
