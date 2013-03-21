package com.easeframe.showcase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easeframe.showcase.entity.User;

public class AccountService {
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	public User findUserByLoginName(String loginName) {
		return null;
	}
}
