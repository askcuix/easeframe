package com.easeframe.core.unit.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.easeframe.core.unit.utils.ReflectionUtilsTest.TestBean3;
import com.easeframe.core.utils.CollectionUtils;

public class CollecitonUtilsTest {

	@SuppressWarnings("unchecked")
	@Test
	public void convertElementPropertyToString() {
		TestBean3 bean1 = new TestBean3();
		bean1.setId(1);
		TestBean3 bean2 = new TestBean3();
		bean2.setId(2);

		List list = new ArrayList();
		list.add(bean1);
		list.add(bean2);

		assertEquals("1,2", CollectionUtils.extractElementPropertyToString(list, "id", ","));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void convertElementPropertyToList() {
		TestBean3 bean1 = new TestBean3();
		bean1.setId(1);
		TestBean3 bean2 = new TestBean3();
		bean2.setId(2);

		List list = new ArrayList();
		list.add(bean1);
		list.add(bean2);
		;
		List<String> result = CollectionUtils.extractElementPropertyToList(list, "id");
		assertEquals(2, result.size());
		assertEquals(1, result.get(0));
	}
}
