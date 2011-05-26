package com.easeframe.demo.struts2.unit.web;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.easeframe.demo.struts2.entity.User;
import com.easeframe.demo.struts2.web.CrudActionSupport;

public class CrudActionSupportTest {

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

		CrudActionSupport.mergeByIds(srcList, idList, User.class);

		assertEquals(2, srcList.size());
		assertTrue(1L == srcList.get(0).getId());
		assertTrue(3L == srcList.get(1).getId());
	}
}
