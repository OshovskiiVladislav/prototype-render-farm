package com.oshovskii.lib.response.wrapper.advice;

import com.oshovskii.lib.response.wrapper.IWrapperModel;
import com.oshovskii.lib.response.wrapper.IWrapperService;
import com.oshovskii.lib.response.wrapper.MethodInformation;
import com.oshovskii.lib.response.wrapper.annotations.DisableResponseWrapper;
import com.oshovskii.lib.response.wrapper.annotations.EnableResponseWrapper;
import com.oshovskii.lib.response.wrapper.annotations.InjectWrapperServiceMap;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@ControllerAdvice(annotations = EnableResponseWrapper.class)
public class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {

    private Map<Class<? extends IWrapperModel<?,?>>, IWrapperService<?, ?>> wrapperServiceMap;

    @InjectWrapperServiceMap
    public void setWrapperServiceMap(Map<Class<? extends IWrapperModel<?, ?>>, IWrapperService<?, ?>> wrapperServiceMap) {
        this.wrapperServiceMap = wrapperServiceMap;
    }

    @Override
    public boolean supports(MethodParameter returnType, @NonNull Class converterType) {
        for (Annotation a : returnType.getMethodAnnotations()) {
            if (a.annotationType() == DisableResponseWrapper.class) {
                return false;
            }
        }
        return true;
    }

    @SneakyThrows
    @Override
    @Nullable
    public Object beforeBodyWrite(
            @Nullable Object body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response
    ) {
        MethodInformation methodInformation = new MethodInformation(
                returnType, selectedContentType, selectedConverterType, request, response
        );

        if (body == null) {
            return null;
        }

        Class<? extends IWrapperModel<?, ?>> wrapperClass = null;
        for (Annotation annotation : returnType.getContainingClass().getAnnotations()) {
            if (annotation.annotationType() == EnableResponseWrapper.class) {
                wrapperClass = ((EnableResponseWrapper) annotation).wrapperClass();
                break;
            }
        }

        if (wrapperClass == null) {
            return body;
        }

        if (Collection.class.isAssignableFrom(body.getClass())) {
            try {
                Collection<?> bodyCollection = (Collection<?>) body;
                if (bodyCollection.isEmpty()) {
                    return body;
                }
                return generateListOfResponseWrapper(bodyCollection, wrapperClass, methodInformation);
            } catch (Exception e) {
                return body;
            }
        }
        return generateResponseWrapper(body, wrapperClass, methodInformation);
    }

    @NonNull
    private List<Object> generateListOfResponseWrapper(
            @NonNull Collection<?> bodyCollection,
            @NonNull Class<? extends IWrapperModel<?, ?>> wrapperClass,
            @NonNull MethodInformation methodInformation
    ) {
        return bodyCollection.stream()
                .map((t) -> t == null ?
                        null :
                        generateResponseWrapper(t, wrapperClass, methodInformation)
                )
                .collect(Collectors.toList());
    }

    @NonNull
    @SneakyThrows
    private IWrapperModel<?, ?> generateResponseWrapper(
            @NonNull Object body,
            @NonNull Class<? extends IWrapperModel<?, ?>> wrapperClass,
            @NonNull MethodInformation methodInformation
    ) {
        IWrapperModel<?, ?> wrapper = wrapperClass.getDeclaredConstructor().newInstance();
        wrapper.setBodyHelper(body, methodInformation);
        wrapper.setDataHelper(wrapperServiceMap.get(wrapperClass).getDataHelper(body), methodInformation);
        return wrapper;
    }
}
