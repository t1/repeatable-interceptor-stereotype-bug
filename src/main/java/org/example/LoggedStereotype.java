package org.example;

import javax.enterprise.inject.Stereotype;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Stereotype
@Logged(prefix = "s1")
@Logged(prefix = "s2")
public @interface LoggedStereotype {
}
