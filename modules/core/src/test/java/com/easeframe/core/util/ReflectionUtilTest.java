package com.easeframe.core.util;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class ReflectionUtilTest {
	@Test
	public void getAndSetFieldValue() {
		TestBean bean = new TestBean();
		// no getter
		assertEquals(1, ReflectionUtil.getFieldValue(bean, "privateField"));
		// skip getter and read value directly
		assertEquals(1, ReflectionUtil.getFieldValue(bean, "publicField"));

		bean = new TestBean();
		// no setter
		ReflectionUtil.setFieldValue(bean, "privateField", 2);
		assertEquals(2, bean.inspectPrivateField());

		// skip setter and set value directly
		ReflectionUtil.setFieldValue(bean, "publicField", 2);

		assertEquals(2, bean.inspectPublicField());

		try {
			ReflectionUtil.getFieldValue(bean, "notExist");
			fail("should throw exception here");
		} catch (IllegalArgumentException e) {

		}

		try {
			ReflectionUtil.setFieldValue(bean, "notExist", 2);
			fail("should throw exception here");
		} catch (IllegalArgumentException e) {

		}

	}

	@Test
	public void invokeGetterAndSetter() {
		TestBean bean = new TestBean();
		assertEquals(bean.inspectPublicField() + 1,
				ReflectionUtil.invokeGetter(bean, "publicField"));

		bean = new TestBean();
		// by setter
		ReflectionUtil.invokeSetter(bean, "publicField", 10);
		assertEquals(10 + 1, bean.inspectPublicField());
	}

	@Test
	public void invokeMethod() {
		TestBean bean = new TestBean();
		// by method name and parameters
		assertEquals("hello calvin", ReflectionUtil.invokeMethod(bean,
				"privateMethod", new Class[] { String.class },
				new Object[] { "calvin" }));

		// by method name
		assertEquals("hello calvin", ReflectionUtil.invokeMethodByName(bean,
				"privateMethod", new Object[] { "calvin" }));

		// wrong method name
		try {
			ReflectionUtil.invokeMethod(bean, "notExistMethod",
					new Class[] { String.class }, new Object[] { "calvin" });
			fail("should throw exception here");
		} catch (IllegalArgumentException e) {

		}

		// wrong parameter
		try {
			ReflectionUtil.invokeMethod(bean, "privateMethod",
					new Class[] { Integer.class }, new Object[] { "calvin" });
			fail("should throw exception here");
		} catch (RuntimeException e) {

		}

		// wrong method name
		try {
			ReflectionUtil.invokeMethodByName(bean, "notExistMethod",
					new Object[] { "calvin" });
			fail("should throw exception here");
		} catch (IllegalArgumentException e) {

		}

	}

	@Test
	public void getSuperClassGenricType() {
		assertEquals(String.class,
				ReflectionUtil.getClassGenricType(TestBean.class));
		assertEquals(Long.class,
				ReflectionUtil.getClassGenricType(TestBean.class, 1));

		// no generic type in parent
		assertEquals(Object.class,
				ReflectionUtil.getClassGenricType(TestBean2.class));

		// no parent
		assertEquals(Object.class,
				ReflectionUtil.getClassGenricType(TestBean3.class));
	}

	@Test
	public void convertReflectionExceptionToUnchecked() {
		IllegalArgumentException iae = new IllegalArgumentException();
		// ReflectionException,normal
		RuntimeException e = ReflectionUtil
				.convertReflectionExceptionToUnchecked(iae);
		assertEquals(iae, e.getCause());

		// InvocationTargetException,extract it's target exception.
		Exception ex = new Exception();
		e = ReflectionUtil
				.convertReflectionExceptionToUnchecked(new InvocationTargetException(
						ex));
		assertEquals(ex, e.getCause());

		// UncheckedException, ignore it.
		RuntimeException re = new RuntimeException("abc");
		e = ReflectionUtil.convertReflectionExceptionToUnchecked(re);
		assertEquals("abc", e.getMessage());

		// Unexcepted Checked exception.
		e = ReflectionUtil.convertReflectionExceptionToUnchecked(ex);
		assertEquals("Unexpected Checked Exception.", e.getMessage());

	}

	public static class ParentBean<T, ID> {
	}

	public static class TestBean extends ParentBean<String, Long> {

		private int privateField = 1;
		private int publicField = 1;

		public int getPublicField() {
			return publicField + 1;
		}

		public void setPublicField(int publicField) {
			this.publicField = publicField + 1;
		}

		public int inspectPrivateField() {
			return privateField;
		}

		public int inspectPublicField() {
			return publicField;
		}

		@SuppressWarnings("unused")
		private String privateMethod(String text) {
			return "hello " + text;
		}
	}

	@SuppressWarnings("rawtypes")
	public static class TestBean2 extends ParentBean {
	}

	public static class TestBean3 {

		private int id;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
	}
}
