package com.oshovskii.lib.response.wrapper.config;

import com.oshovskii.lib.response.wrapper.advice.ResponseWrapperAdvice;
import com.oshovskii.lib.response.wrapper.bpp.InjectWrapperServiceMapBeanPostProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@RequiredArgsConstructor
@ComponentScan("com.oshovskii.lib.response.wrapper")
public class ResponseWrapperAutoConfiguration {

    private final ApplicationContext applicationContext;

    @Bean
    @ConditionalOnMissingBean
    @DependsOn(value = "responseWrapperBeanPostProcessor")
    public ResponseWrapperAdvice responseWrapperAdvice() {
        return new ResponseWrapperAdvice();
    }

    @Bean
    @ConditionalOnMissingBean
    public InjectWrapperServiceMapBeanPostProcessor responseWrapperBeanPostProcessor() {
        return new InjectWrapperServiceMapBeanPostProcessor(applicationContext);
    }
}
