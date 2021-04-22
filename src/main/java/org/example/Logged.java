package org.example;

import org.example.Logged.RepeatableLogged;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@InterceptorBinding
@Retention(RUNTIME)
@Target({METHOD, TYPE})
@Repeatable(RepeatableLogged.class)
public @interface Logged {
    @Nonbinding
    String prefix();

    @Inherited
    @InterceptorBinding
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RUNTIME) @interface RepeatableLogged {
        @Nonbinding
        Logged[] value();
    }
}
