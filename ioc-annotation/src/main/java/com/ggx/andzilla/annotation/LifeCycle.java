package com.ggx.andzilla.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jerry.guan on 5/11/2017.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface LifeCycle {

    Class value();
}
