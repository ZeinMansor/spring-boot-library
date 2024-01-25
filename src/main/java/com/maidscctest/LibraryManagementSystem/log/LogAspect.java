package com.maidscctest.LibraryManagementSystem.log;


import com.maidscctest.LibraryManagementSystem.interfaces.LogExecutionTime;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Around("@annotation(com.maidscctest.LibraryManagementSystem.interfaces.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getName();
        String methodName = methodSignature.getMethod().getName();
        Instant startTime = Instant.now();
        Object result = proceedingJoinPoint.proceed();
        String additionalMessage = methodSignature
                .getMethod()
                .getAnnotation(LogExecutionTime.class)
                .additionalMessage();

        long elapsedTime = Duration.between(startTime, Instant.now()).toMillis();

        log.info("Class Name: {}, Method Name: {}, Additional Message: {}, Elapsed Time: {}ms",
                className, methodName, additionalMessage, elapsedTime);
        log.info("Result: {}", result);
        return result;

    }

}
