package com.easeframe.demo.miniservice.data;

import com.easeframe.core.test.utils.DataUtils;
import com.easeframe.demo.miniservice.entity.Role;
import com.easeframe.demo.miniservice.entity.User;

/**
 * 用户测试数据生成.
 * 
 * @author Chris
 *
 */
public class AccountData {

	public static User getRandomUser() {
		String userName = DataUtils.randomName("User");

		User user = new User();
		user.setLoginName(userName);
		user.setName(userName);
		user.setPassword("123456");
		user.setEmail(userName + "@test.com");

		return user;
	}

	public static User getRandomUserWithAdminRole() {
		User user = AccountData.getRandomUser();
		Role adminRole = AccountData.getAdminRole();
		user.getRoleList().add(adminRole);
		return user;
	}

	public static Role getAdminRole() {
		Role role = new Role();
		role.setId(1L);
		role.setName("Admin");

		return role;
	}
}
