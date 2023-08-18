package com.oshovskii.lib.response.wrapper;

import lombok.NonNull;
import org.springframework.lang.Nullable;

public interface IWrapperModel<Body, Data> {

    void setData(@Nullable Data data, @NonNull MethodInformation methodInformation);

    default <DataHelper> void setDataHelper(@Nullable DataHelper data, @NonNull MethodInformation methodInformation){
        @SuppressWarnings("unchecked")
        Data data1 = (Data) data;

        setData(data1, methodInformation);
    }

    void setBody(@NonNull Body body, @NonNull MethodInformation methodInformation);

    default <BodyHelper> void setBodyHelper(@NonNull BodyHelper body, @NonNull MethodInformation methodInformation) {
        @SuppressWarnings("unchecked")
        Body body1 = (Body) body;

        setBody(body1, methodInformation);
    }
}
