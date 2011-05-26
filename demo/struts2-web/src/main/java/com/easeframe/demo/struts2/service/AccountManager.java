package com.easeframe.demo.struts2.service;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easeframe.core.orm.Page;
import com.easeframe.core.orm.PageRequest;
import com.easeframe.core.orm.PropertyFilter;
import com.easeframe.demo.struts2.dao.GroupDao;
import com.easeframe.demo.struts2.dao.PermissionDao;
import com.easeframe.demo.struts2.dao.UserDao;
import com.easeframe.demo.struts2.entity.Group;
import com.easeframe.demo.struts2.entity.Permission;
import com.easeframe.demo.struts2.entity.User;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 * 
 * @author Chris
 *
 */
//Spring Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class AccountManager {

	private static Logger logger = LoggerFactory.getLogger(AccountManager.class);

	private UserDao userDao;
	private GroupDao groupDao;
	private PermissionDao permissionDao;
	private SecurityRealm shiroRealm;

	//-- User Manager --//
	@Transactional(readOnly = true)
	public User getUser(Long id) {
		return userDao.get(id);
	}

	public void saveUser(User entity) {
		userDao.save(entity);
		shiroRealm.clearCachedAuthorizationInfo(entity.getLoginName());
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("Delete super administrator by {}", SecurityUtils.getSubject().getPrincipal());
			throw new ServiceException("Can not delete super administrator");
		}
		userDao.delete(id);
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	/**
	 * 使用属性过滤条件查询用户.
	 */
	@Transactional(readOnly = true)
	public Page<User> searchUser(final PageRequest page, final List<PropertyFilter> filters) {
		return userDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public User findUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}

	/**
	 * 检查用户名是否唯一.
	 *
	 * @return loginName在数据库中唯一或等于oldLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String newLoginName, String oldLoginName) {
		return userDao.isPropertyUnique("loginName", newLoginName, oldLoginName);
	}

	//-- Group Manager --//
	public Group getGroup(Long id) {
		return groupDao.get(id);
	}

	@Transactional(readOnly = true)
	public List<Group> getAllGroup() {
		return groupDao.getAll("id", true);
	}

	public void saveGroup(Group entity) {
		groupDao.save(entity);
		shiroRealm.clearAllCachedAuthorizationInfo();
	}

	public void deleteGroup(Long id) {
		groupDao.delete(id);
		shiroRealm.clearAllCachedAuthorizationInfo();
	}

	// -- Permission Manager --//
	@Transactional(readOnly = true)
	public List<Permission> getAllPermission() {
		return permissionDao.getAll();
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	@Autowired
	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}

	@Autowired(required = false)
	public void setShiroRealm(SecurityRealm shiroRealm) {
		this.shiroRealm = shiroRealm;
	}
}
