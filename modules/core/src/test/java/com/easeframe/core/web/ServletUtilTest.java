package com.easeframe.core.web;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class ServletUtilTest {

	@Test
	public void checkIfModified() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		assertEquals(
				true,
				ServletUtil.checkIfModifiedSince(request, response,
						(new Date().getTime() - 2000)));

		request.addHeader("If-Modified-Since", new Date().getTime());
		assertEquals(
				false,
				ServletUtil.checkIfModifiedSince(request, response,
						(new Date().getTime() - 2000)));
		assertEquals(
				true,
				ServletUtil.checkIfModifiedSince(request, response,
						(new Date().getTime() + 2000)));
	}

	@Test
	public void checkIfNoneMatch() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		assertEquals(true,
				ServletUtil.checkIfNoneMatchEtag(request, response, "V1.0"));

		request.addHeader("If-None-Match", "V1.0,V1.1");
		assertEquals(false,
				ServletUtil.checkIfNoneMatchEtag(request, response, "V1.0"));
		assertEquals(true,
				ServletUtil.checkIfNoneMatchEtag(request, response, "V2.0"));
	}

	@Test
	public void getParametersStartingWith() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("pre_a", "aa");
		request.addParameter("pre_b", "bb");
		request.addParameter("c", "c");
		Map<String, Object> result = ServletUtil.getParametersStartingWith(
				request, "pre_");
		assertEquals(2, result.size());
		assertTrue(result.keySet().contains("a"));
		assertTrue(result.keySet().contains("b"));
		assertTrue(result.values().contains("aa"));
		assertTrue(result.values().contains("bb"));

		result = ServletUtil.getParametersStartingWith(request, "error_");
		assertEquals(0, result.size());

		result = ServletUtil.getParametersStartingWith(request, null);
		assertEquals(3, result.size());
	}

	@Test
	public void setParameterWithPrefix() {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("name", "foo");
		params.put("age", "1");

		String queryString = ServletUtil.setParameterWithPrefix(params,
				"search_");
		assertEquals("search_name=foo&search_age=1", queryString);

		params.clear();
		params.put("name", "foo");
		params.put("age", 1);
		queryString = ServletUtil.setParameterWithPrefix(params, "search_");
		assertEquals("search_name=foo&search_age=1", queryString);

		queryString = ServletUtil.setParameterWithPrefix(params, null);
		assertEquals("name=foo&age=1", queryString);

		queryString = ServletUtil.setParameterWithPrefix(params, "");
		assertEquals("name=foo&age=1", queryString);

		queryString = ServletUtil.setParameterWithPrefix(null, "search_");
		assertEquals("", queryString);

		params.clear();
		queryString = ServletUtil.setParameterWithPrefix(params, "search_");
		assertEquals("", queryString);
	}
}
