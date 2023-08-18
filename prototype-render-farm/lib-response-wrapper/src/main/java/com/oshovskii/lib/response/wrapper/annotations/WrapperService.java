package com.oshovskii.lib.response.wrapper.annotations;

import com.oshovskii.lib.response.wrapper.IWrapperModel;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Service
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WrapperService {
    Class<? extends IWrapperModel<?,?>> wrapperModel();
}
