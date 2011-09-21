package com.easeframe.core.utils.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.commons.lang.StringUtils;

public class ValidatorUtils {

	/**
	 * 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
	 */
	@SuppressWarnings("unchecked")
	public static void validateWithException(Validator validator, Object object, Class<?>... groups)
			throws ConstraintViolationException {
		Set constraintViolations = validator.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			throw new ConstraintViolationException(constraintViolations);
		}
	}

	/**
	 * 辅助方法, 转换Set<ConstraintViolation>为字符串, 以separator分割.
	 */
	public static String convertMessage(Set<? extends ConstraintViolation> constraintViolations, String separator) {
		List<String> errorMessages = new ArrayList<String>();
		for (ConstraintViolation violation : constraintViolations) {
			errorMessages.add(violation.getMessage());
		}
		return StringUtils.join(errorMessages, separator);
	}

	/**
	 * 辅助方法, 转换ConstraintViolationException中的Set<ConstraintViolations>为字符串, 以separator分割.
	 */
	public static String convertMessage(ConstraintViolationException e, String separator) {
		return convertMessage(e.getConstraintViolations(), separator);
	}
}
