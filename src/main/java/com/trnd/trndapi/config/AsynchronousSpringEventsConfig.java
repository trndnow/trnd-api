package com.trnd.trndapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

@Configuration
public class AsynchronousSpringEventsConfig {

    @Bean(name = "applicationEventMulticaster")
    public SimpleApplicationEventMulticaster simpleApplicationEventMulticaster(){
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(delegatingSecurityContextAsyncTaskExecutor());
        return eventMulticaster;
    }

    @Bean
    public AsyncTaskExecutor asyncTaskExecutor(){
        return new SimpleAsyncTaskExecutor("asyncEvent-");
    }

    @Bean
    public AsyncTaskExecutor delegatingSecurityContextAsyncTaskExecutor() {
        return new DelegatingSecurityContextAsyncTaskExecutor(asyncTaskExecutor());
    }
}
