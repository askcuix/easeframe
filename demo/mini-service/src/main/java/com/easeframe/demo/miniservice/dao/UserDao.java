package com.easeframe.demo.miniservice.dao;

import org.springframework.stereotype.Component;

import com.easeframe.core.orm.hibernate.HibernateDao;
import com.easeframe.demo.miniservice.entity.User;

/**
 * 用户对象的泛型DAO.
 * 
 * @author Chris
 *
 */
@Component
public class UserDao extends HibernateDao<User, Long> {
	//-- 统一定义所有以用户为主体的HQL --//
	private static final String COUNT_BY_LNAME_PASSWD = "select count(u) from User u where u.loginName=? and u.password=?";

	public Long countUserByLoginNamePassword(String loginName, String password) {
		return (Long) findUnique(COUNT_BY_LNAME_PASSWD, loginName, password);
	}

	/**
	 * 初始化User的延迟加载关联roleList.
	 */
	public void initUser(User user) {
		initProxyObject(user.getRoleList());
	}

}
