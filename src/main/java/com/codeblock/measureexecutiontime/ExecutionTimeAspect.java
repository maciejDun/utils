package com.codeblock.measureexecutiontime;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

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

        Duration executionDuration = Duration.of(stopWatch.getTotalTimeMillis(), ChronoUnit.MILLIS);

        log.atLevel(Level.valueOf(measureExecutionTime.logLevel().name())).log("\"{}\" executed in {} minutes, {} seconds, {} milliseconds",
                measureExecutionTime.businessProcessName(), executionDuration.toMinutesPart(), executionDuration.toSecondsPart(), executionDuration.toMillisPart());
        return proceed;
    }
}

