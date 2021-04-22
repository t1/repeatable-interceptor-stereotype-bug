package org.example;

import javax.annotation.Priority;
import javax.enterprise.inject.Stereotype;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static javax.ws.rs.Priorities.USER;

@Interceptor
@Priority(USER)
@Logged(prefix = "")
public class LoggedInterceptor {
    @AroundInvoke
    public Object logMethodCall(InvocationContext context) throws Exception {
        var log = new LogContext(context.getMethod());
        log.log(" : >");
        var result = context.proceed();
        log.log(" : <");
        return result;
    }

    private static class LogContext {
        private final Logged[] configs;

        public LogContext(Method method) {
            this.configs = findConfigs(method);
        }

        private Logged[] findConfigs(Method method) {
            Logged[] configs = resolveAnnotations(method).toArray(Logged[]::new);
            if (configs.length == 0)
                configs = resolveAnnotations(method.getDeclaringClass()).toArray(Logged[]::new);
            return configs;
        }

        private static Stream<Logged> resolveAnnotations(AnnotatedElement annotatedElement) {
            return Stream.concat(
                Stream.of(annotatedElement.getAnnotationsByType(Logged.class)),
                resolveStereotypes(annotatedElement.getAnnotations()));
        }

        private static Stream<Logged> resolveStereotypes(Annotation[] annotations) {
            return Stream.of(annotations)
                .map(Annotation::annotationType)
                .filter(annotation -> annotation.isAnnotationPresent(Stereotype.class))
                .flatMap(LogContext::resolveAnnotations);
        }

        private void log(String text) {
            if (configs.length == 0)
                System.out.println("-0" + text);
            else for (Logged annotation : configs) {
                System.out.println(annotation.prefix() + text);
            }
        }
    }
}
