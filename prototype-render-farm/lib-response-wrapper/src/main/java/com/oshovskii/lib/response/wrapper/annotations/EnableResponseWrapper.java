package com.oshovskii.lib.response.wrapper.annotations;

import com.oshovskii.lib.response.wrapper.IWrapperModel;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RestController
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableResponseWrapper {
    Class<? extends IWrapperModel<?, ?>> wrapperClass();
}
