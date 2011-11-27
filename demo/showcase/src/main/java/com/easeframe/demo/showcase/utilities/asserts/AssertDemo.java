package com.easeframe.demo.showcase.utilities.asserts;

import static org.junit.Assert.*;

import org.apache.commons.lang3.Validate;
import org.junit.Assert;
import org.junit.Test;

/**
 * commons-lang3 Validate demo. 
 * 
 * @author Chris
 *
 */
public class AssertDemo {

	@Test
	public void asserts() {

		//not null
		try {
			String parameter = "abc";

			//not null后返回值到等式左边
			String result = Validate.notNull(parameter);
			assertEquals("abc", result);

			Validate.notNull(null);
			Assert.fail();
		} catch (NullPointerException e) {

		}

		//notBlank blank
		try {
			String parameter = "abc";
			String result = Validate.notBlank(parameter);
			assertEquals("abc", result);

			Validate.notBlank("");
			Assert.fail();

		} catch (IllegalArgumentException e) {

		}

		//is true
		try {
			Validate.isTrue(false);
		} catch (IllegalArgumentException e) {

		}

	}
}
