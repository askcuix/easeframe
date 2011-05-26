package com.easeframe.core.unit.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import com.easeframe.core.utils.PropertyUtils;

/**
 * Property Test.
 * 
 * @author Chris
 *
 */
public class PropertyUtilsTest {

	@Test
	public void multiPropertiy() throws IOException {
		Properties p = PropertyUtils.loadProperties("classpath:/test1.properties", "classpath:/test2.properties");

		assertEquals("1", p.getProperty("p1"));
		assertEquals("10", p.getProperty("p2"));
		assertEquals("3", p.getProperty("p3"));
	}

	@Test
	public void notExistPropertiy() throws IOException {
		Properties p = PropertyUtils.loadProperties("classpath:/notexist.properties");
		assertEquals(null, p.getProperty("notexist"));
	}
}
