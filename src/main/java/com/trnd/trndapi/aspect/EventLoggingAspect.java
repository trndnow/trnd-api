package com.trnd.trndapi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Aspect
@Component
@Slf4j
public class EventLoggingAspect {

    @Before("@annotation(org.springframework.context.event.EventListener)")
    public void logEvent(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String eventName = getEventName(joinPoint);

        log.info("Event occurred: {}.{} with arguments: {}", eventName, methodName, args);
    }

    private String getEventName(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Annotation[] annotations = methodSignature.getMethod().getDeclaredAnnotations();

        for (Annotation annotation : annotations) {
            if (annotation instanceof EventListener) {
                Class<?>[] eventTypes = ((EventListener) annotation).value();
                if (eventTypes.length > 0) {
                    return eventTypes[0].getSimpleName();
                }
            }
        }

        return "UnknownEvent";
    }

    @AfterThrowing(pointcut = "@annotation(org.springframework.context.event.EventListener)", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        String eventName = getEventName(joinPoint);

        log.error("Exception occurred in event: {}.{} - Exception: {}", eventName, methodName, ex.getMessage(), ex);
    }
}
