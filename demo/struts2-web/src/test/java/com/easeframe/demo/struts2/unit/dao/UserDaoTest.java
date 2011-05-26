package com.easeframe.demo.struts2.unit.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.easeframe.core.test.data.Fixtures;
import com.easeframe.core.test.spring.SpringTxTestCase;
import com.easeframe.demo.struts2.dao.UserDao;
import com.easeframe.demo.struts2.data.AccountData;
import com.easeframe.demo.struts2.entity.User;

/**
 * UserDao的测试用例, 测试ORM映射及特殊的DAO操作.
 * 
 * 默认在每个测试函数后进行回滚.
 * 
 * @author Chris
 *
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class UserDaoTest extends SpringTxTestCase {

	@Autowired
	private UserDao entityDao;

	@Before
	public void reloadSampleData() throws Exception {
		Fixtures.reloadAllTable(dataSource, "/data/sample-data.xml");
	}

	@Test
	//如果你需要真正插入数据库,将Rollback设为false
	//@Rollback(false) 
	public void crudEntityWithRole() {
		//新建并保存带权限组的用户
		User user = AccountData.getRandomUserWithGroup();
		entityDao.save(user);
		//强制执行sql语句
		entityDao.flush();

		//获取用户
		user = entityDao.findUniqueBy("id", user.getId());
		assertEquals(1, user.getGroupList().size());

		//删除用户的权限组
		user.getGroupList().remove(0);
		entityDao.flush();
		user = entityDao.findUniqueBy("id", user.getId());
		assertEquals(0, user.getGroupList().size());

		//删除用户
		entityDao.delete(user.getId());
		entityDao.flush();
		user = entityDao.findUniqueBy("id", user.getId());
		assertNull(user);
	}

	//期望抛出ConstraintViolationException的异常.
	@Test(expected = org.hibernate.exception.ConstraintViolationException.class)
	public void savenUserNotUnique() {
		User user = AccountData.getRandomUser();
		user.setLoginName("admin");
		entityDao.save(user);
		entityDao.flush();
	}
}
