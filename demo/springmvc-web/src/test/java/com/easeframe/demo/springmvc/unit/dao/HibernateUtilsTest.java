package com.easeframe.demo.springmvc.unit.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.easeframe.demo.springmvc.dao.HibernateUtils;
import com.easeframe.demo.springmvc.entity.User;

public class HibernateUtilsTest {

	@Test
	public void mergeByCheckedIds() {
		User a = new User();
		a.setId(1L);

		User b = new User();
		b.setId(1L);

		List<User> srcList = new ArrayList<User>();
		srcList.add(a);
		srcList.add(b);
		List<Long> idList = new ArrayList<Long>();
		idList.add(1L);
		idList.add(3L);

		HibernateUtils.mergeByIds(srcList, idList, User.class);

		assertEquals(2, srcList.size());
		assertTrue(1L == srcList.get(0).getId());
		assertTrue(3L == srcList.get(1).getId());

	}
}
