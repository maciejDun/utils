package com.codeblock.measureexecutiontime;

import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MeasureExecutionTime {
    @NotBlank
    String businessProcessName();
    LogLevel logLevel() default LogLevel.DEBUG;
}

