package com.oshovskii.lib.response.wrapper.bpp;

import com.oshovskii.lib.response.wrapper.IWrapperModel;
import com.oshovskii.lib.response.wrapper.IWrapperService;
import com.oshovskii.lib.response.wrapper.annotations.InjectWrapperServiceMap;
import com.oshovskii.lib.response.wrapper.annotations.WrapperService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class InjectWrapperServiceMapBeanPostProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        setFieldInjects(bean);
        setMethodInject(bean);
        return bean;
    }

    @SneakyThrows
    private void setMethodInject(Object bean) {
        Set<Method> methods = Arrays.stream(bean.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(InjectWrapperServiceMap.class))
                .collect(Collectors.toSet());

        for (Method method : methods) {
            method.invoke(bean, getWrapperServiceMap());
        }

    }

    @SneakyThrows
    private void setFieldInjects(Object bean) {
        Set<Field> fields = Arrays.stream(bean.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(InjectWrapperServiceMap.class))
                .collect(Collectors.toSet());

        for (Field field : fields) {
            field.setAccessible(true);
            field.set(bean, getWrapperServiceMap());
        }

    }

    private Map<Class<? extends IWrapperModel<?, ?>>, IWrapperService> getWrapperServiceMap() {
        Map<String, IWrapperService> beansOfType = applicationContext.getBeansOfType(IWrapperService.class);

        return beansOfType.values().stream()
                .collect(Collectors.toMap(
                                (t) -> {
                                    if (!t.getClass().isAnnotationPresent(WrapperService.class)) {
                                        throw new RuntimeException("Не все сервисы, реализующие IWrapperService, аннотированы @WrapperService");
                                    }
                                    return t.getClass().getAnnotation(WrapperService.class).wrapperModel();
                                },
                                (t) -> t
                        )
                );
    }


}
