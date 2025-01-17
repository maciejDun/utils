package com.codeblock.measureexecutiontime;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
class ExecutionTimeAspect {

    @Around("@annotation(measureExecutionTime)")
    private Object measureExecutionTime(ProceedingJoinPoint joinPoint, MeasureExecutionTime measureExecutionTime) throws Throwable {
        log.atLevel(Level.valueOf(measureExecutionTime.logLevel().name())).log("Executing: \"{}\"", measureExecutionTime.businessProcessName());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object proceed = joinPoint.proceed();

        stopWatch.stop();

        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        long minutes = totalTimeMillis / 60000;
        long seconds = (totalTimeMillis % 60000) / 1000;
        long millis = totalTimeMillis % 1000;

        log.atLevel(Level.valueOf(measureExecutionTime.logLevel().name())).log("\"{}\" executed in {} minutes, {} seconds, {} milliseconds",
                measureExecutionTime.businessProcessName(), minutes, seconds, millis);
        return proceed;
    }
}

