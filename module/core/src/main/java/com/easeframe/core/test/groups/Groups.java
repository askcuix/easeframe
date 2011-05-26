package com.easeframe.core.test.groups;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模拟TestNG Groups分组执行用例功能的annotation.
 * 
 * @author Chris
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Documented
public @interface Groups {
	/**
	 * 执行所有组别的测试.
	 */
	String ALL = "all";

	/**
	 * 组别定义,默认为ALL.
	 */
	String value() default ALL;
}
