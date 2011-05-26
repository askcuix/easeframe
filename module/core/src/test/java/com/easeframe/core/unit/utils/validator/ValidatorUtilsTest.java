package com.easeframe.core.unit.utils.validator;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.easeframe.core.test.spring.SpringContextTestCase;
import com.easeframe.core.utils.validator.ValidatorUtils;

@ContextConfiguration(locations = { "/applicationContext-core-test.xml" })
public class ValidatorUtilsTest extends SpringContextTestCase {

	@Autowired
	Validator validator;

	@Test
	public void validate() {
		Customer customer = new Customer();
		customer.setEmail("aaa");

		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		assertEquals(2, violations.size());
		String result = ValidatorUtils.convertMessage(violations, ",");
		assertTrue(StringUtils.indexOf(result, "邮件地址格式不正确") != -1);
		assertTrue(StringUtils.indexOf(result, "姓名不能为空") != -1);
	}

	@Test
	public void validateWithException() {
		Customer customer = new Customer();
		customer.setEmail("aaa");

		try {
			ValidatorUtils.validateWithException(validator, customer);
			Assert.fail("should throw excepion");
		} catch (ConstraintViolationException e) {
			String result = ValidatorUtils.convertMessage(e, ",");
			assertTrue(StringUtils.indexOf(result, "邮件地址格式不正确") != -1);
			assertTrue(StringUtils.indexOf(result, "姓名不能为空") != -1);
		}

	}

	private static class Customer {

		String name;

		String email;

		@SuppressWarnings("unused")
		@NotBlank(message = "姓名不能为空")
		public String getName() {
			return name;
		}

		@SuppressWarnings("unused")
		public void setName(String name) {
			this.name = name;
		}

		@SuppressWarnings("unused")
		@Email(message = "邮件地址格式不正确")
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	}
}
