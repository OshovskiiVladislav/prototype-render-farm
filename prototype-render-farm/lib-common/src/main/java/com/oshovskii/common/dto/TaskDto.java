package com.oshovskii.common.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.oshovskii.common.dto.enums.TaskType;
import lombok.*;

@Data
@JsonRootName("TaskDto")
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    // TODO add validation
    private Long id;
    private String title;
    private TaskType type;
}
