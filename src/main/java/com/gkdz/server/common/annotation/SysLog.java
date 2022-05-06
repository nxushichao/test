package com.gkdz.server.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author Mark
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}
