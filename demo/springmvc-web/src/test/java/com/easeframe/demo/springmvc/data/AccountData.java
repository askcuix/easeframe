package com.easeframe.demo.springmvc.data;

import java.util.ArrayList;
import java.util.List;

import com.easeframe.core.test.data.RandomData;
import com.easeframe.demo.springmvc.entity.Group;
import com.easeframe.demo.springmvc.entity.Permission;
import com.easeframe.demo.springmvc.entity.User;

/**
 * 安全相关实体测试数据生成.
 * 
 * @author Chris
 *
 */
public class AccountData {
	public static final String DEFAULT_PASSWORD = "123456";

	private static List<Group> defaultGroupList = null;

	private static List<Permission> defaultPermissionList = null;

	public static User getRandomUser() {
		String userName = RandomData.randomName("User");

		User user = new User();
		user.setLoginName(userName);
		user.setName(userName);
		user.setPassword(DEFAULT_PASSWORD);
		user.setEmail(userName + "@test.com");

		return user;
	}

	public static User getRandomUserWithGroup() {
		User user = getRandomUser();
		user.getGroupList().add(getRandomDefaultGroup());

		return user;
	}

	public static Group getRandomGroup() {
		Group group = new Group();
		group.setName(RandomData.randomName("Group"));

		return group;
	}

	public static Group getRandomGroupWithPermissions() {
		Group group = getRandomGroup();
		group.getPermissionList().addAll(getRandomDefaultPermissionList());
		return group;
	}

	public static List<Group> getDefaultGroupList() {
		if (defaultGroupList == null) {
			defaultGroupList = new ArrayList<Group>();
			defaultGroupList.add(new Group(1L, "管理员"));
			defaultGroupList.add(new Group(2L, "用户"));
		}
		return defaultGroupList;
	}

	public static Group getRandomDefaultGroup() {
		return RandomData.randomOne(getDefaultGroupList());
	}

	public static Permission getRandomAuthority() {
		return RandomData.randomOne(getDefaultPermissionList());
	}

	public static List<Permission> getDefaultPermissionList() {
		if (defaultPermissionList == null) {
			defaultPermissionList = new ArrayList<Permission>();
			defaultPermissionList.add(new Permission(1L, "user:view", "浏览用户"));
			defaultPermissionList.add(new Permission(2L, "user:edit", "修改用户"));
			defaultPermissionList.add(new Permission(3L, "role:view", "浏览角色"));
			defaultPermissionList.add(new Permission(4L, "role:edit", "修改角色"));
		}
		return defaultPermissionList;
	}

	public static List<Permission> getRandomDefaultPermissionList() {
		return RandomData.randomSome(getDefaultPermissionList());
	}
}
