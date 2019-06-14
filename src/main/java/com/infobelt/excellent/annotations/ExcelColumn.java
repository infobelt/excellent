package com.infobelt.excellent.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {

    String header() default "";

    boolean ignore() default false;

    String name() default "";

    int order() default 0;

}
