package com.oshovskii.server.services.wrappers;

import com.oshovskii.common.dto.TaskDto;
import com.oshovskii.lib.response.wrapper.IWrapperService;
import com.oshovskii.lib.response.wrapper.annotations.WrapperService;
import com.oshovskii.server.models.wrapper.Wrapper;
import lombok.NonNull;

@WrapperService(wrapperModel = Wrapper.class)
public class WrapperServiceImpl implements IWrapperService<TaskDto, String> {
    @Override
    public String getData(@NonNull TaskDto taskDto) {
        return "With love, the development team ♡";
    }
}
