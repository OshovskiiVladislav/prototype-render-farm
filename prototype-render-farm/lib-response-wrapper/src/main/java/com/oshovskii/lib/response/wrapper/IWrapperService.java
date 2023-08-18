package com.oshovskii.lib.response.wrapper;

import lombok.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public interface IWrapperService<Body, Data> {

    @Nullable
    Data getData(@NonNull Body body);

    default <BodyHelper> Data getDataHelper (@NonNull BodyHelper body) {
        @SuppressWarnings("unchecked")
        Body body1 = (Body) body;

        return getData(body1);
    }
}
