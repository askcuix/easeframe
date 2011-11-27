package com.easeframe.demo.showcase.utilities.collection;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.easeframe.demo.showcase.common.entity.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Google collections simple demo.
 * 
 * @author Chris
 *
 */
public class SimpleWayDemo {

	@Test
	public void simple() {
		//无需在等号右边重新定义泛型的创建ArrayList
		List<String> list = Lists.newArrayList();
		assertTrue(list.size() == 0);
		//创建的同时初始化数据
		List<String> list2 = Lists.newArrayList("a", "b", "c");
		assertTrue(list2.size() == 3);
		//无需在等号右边重新定义泛型的创建HashMap
		Map<String, ? extends User> map = Maps.newHashMap();
		assertNotNull(map);
	}

}
