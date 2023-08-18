package com.oshovskii.server.models.wrapper;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.oshovskii.common.dto.TaskDto;
import com.oshovskii.lib.response.wrapper.IWrapperModel;
import com.oshovskii.lib.response.wrapper.MethodInformation;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Wrapper implements IWrapperModel<TaskDto, String> {
    @JsonUnwrapped
    TaskDto taskDto;

    String someInfo;

    @Override
    public void setBody(@NonNull TaskDto body, @NonNull MethodInformation methodInformation) {
        taskDto = body;
    }

    @Override
    public void setData( String data, @NonNull MethodInformation methodInformation) {
        someInfo = data;
    }

}
