package com.easeframe.core.unit.mapper;

import static org.junit.Assert.*;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

import com.easeframe.core.mapper.StringMapper;

/**
 * Unit test for StringMapper.
 * 
 * @author Chris
 *
 */
public class StringMapperTest {

	@Test
	public void convertStringToObject() {
		assertEquals(1, StringMapper.fromString("1", Integer.class));

		Date date = (Date) StringMapper.fromString("2010-06-01", Date.class);
		assertEquals(2010, new DateTime(date).getYear());

		Date dateTime = (Date) StringMapper.fromString("2010-06-01 12:00:04", Date.class);
		assertEquals(12, new DateTime(dateTime).getHourOfDay());
	}
}
