package com.easeframe.demo.showcase.log.trace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识一个方法将通过AOP进行Traced.
 * 
 * @see com.easeframe.demo.showcase.log.trace.TraceAspect
 * 
 * @author Chris
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Traced {

}
