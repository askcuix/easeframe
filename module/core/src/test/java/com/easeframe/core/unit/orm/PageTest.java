package com.easeframe.core.unit.orm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.easeframe.core.orm.Page;
import com.easeframe.core.orm.PageRequest;

/**
 * Page Test.
 */
public class PageTest {
	private Page<Object> page;
	private PageRequest request;

	@Before
	public void setUp() {
		request = new PageRequest();
		page = new Page<Object>(request);
	}

	@Test
	public void getTotalPages() {

		page.setTotalItems(1);
		assertEquals(1, page.getTotalPages());

		page.setTotalItems(10);
		assertEquals(1, page.getTotalPages());

		page.setTotalItems(11);
		assertEquals(2, page.getTotalPages());
	}

	@Test
	public void hasNextOrPre() {

		page.setTotalItems(9);
		assertEquals(false, page.isHasNextPage());

		page.setTotalItems(11);
		assertEquals(true, page.isHasNextPage());

		page.setPageNo(1);
		assertEquals(false, page.isHasPrePage());

		page.setPageNo(2);
		assertEquals(true, page.isHasPrePage());
	}

	@Test
	public void getNextOrPrePage() {
		page.setPageNo(1);
		assertEquals(1, page.getPrePage());

		page.setPageNo(2);
		assertEquals(1, page.getPrePage());

		page.setTotalItems(11);
		page.setPageNo(1);
		assertEquals(2, page.getNextPage());

		page.setPageNo(2);
		assertEquals(2, page.getNextPage());
	}
}
