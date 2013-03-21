package com.easeframe.core.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CollectionUtilTest {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void convertElementPropertyToString() {
		TestBean3 bean1 = new TestBean3();
		bean1.setId(1);
		TestBean3 bean2 = new TestBean3();
		bean2.setId(2);

		List list = new ArrayList();
		list.add(bean1);
		list.add(bean2);

		assertEquals("1,2", CollectionUtil.extractToString(list, "id", ","));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void convertElementPropertyToList() {
		TestBean3 bean1 = new TestBean3();
		bean1.setId(1);
		TestBean3 bean2 = new TestBean3();
		bean2.setId(2);

		List list = new ArrayList();
		list.add(bean1);
		list.add(bean2);
		List<String> result = CollectionUtil.extractToList(list, "id");
		assertEquals(2, result.size());
		assertEquals(1, result.get(0));
	}

	@Test
	public void convertCollectionToString() {
		List<String> list = new ArrayList<String>();
		list.add("aa");
		list.add("bb");
		String result = CollectionUtil.convertToString(list, ",");
		assertEquals("aa,bb", result);
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
