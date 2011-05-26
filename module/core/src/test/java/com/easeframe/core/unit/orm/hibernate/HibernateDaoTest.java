package com.easeframe.core.unit.orm.hibernate;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.easeframe.core.orm.Page;
import com.easeframe.core.orm.PageRequest;
import com.easeframe.core.orm.PageRequest.Sort;
import com.easeframe.core.orm.PropertyFilter;
import com.easeframe.core.orm.hibernate.HibernateDao;
import com.easeframe.core.test.data.Fixtures;
import com.easeframe.core.test.spring.SpringTxTestCase;
import com.easeframe.core.unit.orm.hibernate.data.User;

/**
 * 
 * @author Chris
 */
@ContextConfiguration(locations = { "/applicationContext-core-test.xml" })
public class HibernateDaoTest extends SpringTxTestCase {
	private HibernateDao<User, Long> dao;

	@Autowired
	private SessionFactory sessionFactory;

	@Before
	public void setUp() throws Exception {
		simpleJdbcTemplate.update("drop all objects");

		executeSqlScript("classpath:/schema.sql", false);

		Fixtures.loadData((DataSource) applicationContext.getBean("dataSource"), "/test-data.xml");

		dao = new HibernateDao<User, Long>(User.class);
		dao.setSessionFactory(sessionFactory);
	}

	@Test
	public void getAll() {
		//初始化数据中共有4个用户
		PageRequest pageRequest = new PageRequest(1, 3);
		Page<User> page = dao.getAll(pageRequest);
		assertEquals(3, page.getResult().size());

		//自动统计总数
		assertEquals(4L, page.getTotalItems());

		pageRequest.setPageNo(2);
		page = dao.getAll(pageRequest);
		assertEquals(1, page.getResult().size());
	}

	@Test
	public void findByHQL() {
		//初始化数据中共有4个email为@test.com的用户
		PageRequest pageRequest = new PageRequest(1, 3);
		Page<User> page = dao.findPage(pageRequest, "from User u where email like ?", "%test.com%");
		assertEquals(3, page.getResult().size());

		//自动统计总数
		assertEquals(4L, page.getTotalItems());

		//翻页
		pageRequest.setPageNo(2);
		page = dao.findPage(pageRequest, "from User u where email like ?", "%test.com%");
		assertEquals(1, page.getResult().size());

		//命名参数版本
		Map<String, String> paraMap = Collections.singletonMap("email", "%test.com%");
		pageRequest = new PageRequest(1, 3);
		page = dao.findPage(pageRequest, "from User u where email like :email", paraMap);
		assertEquals(3, page.getResult().size());

		//自动统计总数
		assertEquals(4L, page.getTotalItems());

		//翻页
		pageRequest.setPageNo(2);
		page = dao.findPage(pageRequest, "from User u where email like :email", paraMap);
		assertEquals(1, page.getResult().size());

	}

	@Test
	public void findByCriterion() {
		//初始化数据中共有4个email为@test.com的用户
		PageRequest pageRequest = new PageRequest(1, 3);
		Criterion c = Restrictions.like("email", "test.com", MatchMode.ANYWHERE);
		Page<User> page = dao.findPage(pageRequest, c);
		assertEquals(3, page.getResult().size());

		//自动统计总数
		assertEquals(4L, page.getTotalItems());

		//翻页
		pageRequest.setPageNo(2);
		page = dao.findPage(pageRequest, c);
		assertEquals(1, page.getResult().size());
	}

	@Test
	public void findByCriterionWithOrder() {
		//初始化数据中共有4个email为@test.com的用户
		PageRequest pageRequest = new PageRequest(1, 3);
		pageRequest.setOrderBy("name,loginName");
		pageRequest.setOrderDir(Sort.DESC + "," + Sort.ASC);

		Criterion c = Restrictions.like("email", "test.com", MatchMode.ANYWHERE);
		Page<User> page = dao.findPage(pageRequest, c);

		assertEquals("Test", page.getResult().get(0).getName());
	}

	@Test
	public void findByProperty() {
		List<User> users = dao.findBy("loginName", "admin", PropertyFilter.MatchType.EQ);
		assertEquals(1, users.size());
		assertEquals("admin", users.get(0).getLoginName());

		users = dao.findBy("email", "test.com", PropertyFilter.MatchType.LIKE);
		assertEquals(4, users.size());
		assertTrue(users.get(0).getEmail().indexOf("test.com") != -1);
	}

	@Test
	public void findByFilters() {
		List<PropertyFilter> filters;
		//EQ filter
		PropertyFilter eqFilter = new PropertyFilter("EQS_loginName", "admin");
		filters = new ArrayList<PropertyFilter>();
		filters.add(eqFilter);

		List<User> users = dao.find(filters);
		assertEquals(1, users.size());
		assertEquals("admin", users.get(0).getLoginName());

		//LIKE filter and OR
		PropertyFilter likeAndOrFilter = new PropertyFilter("LIKES_email_OR_loginName", "test.com");
		filters = new ArrayList<PropertyFilter>();
		filters.add(likeAndOrFilter);

		users = dao.find(filters);
		assertEquals(4, users.size());
		assertTrue(StringUtils.contains(users.get(0).getEmail(), "test.com"));

		//Filter with Page
		PageRequest pageRequest = new PageRequest(1, 3);
		Page<User> page = dao.findPage(pageRequest, filters);
		assertEquals(3, page.getResult().size());
		assertEquals(4L, page.getTotalItems());

		pageRequest.setPageNo(2);
		page = dao.findPage(pageRequest, filters);
		assertEquals(1, page.getResult().size());

		//Date and LT/GT filter
		PropertyFilter dateLtFilter = new PropertyFilter("LTD_createTime", "2046-01-01");
		filters = new ArrayList<PropertyFilter>();
		filters.add(dateLtFilter);
		users = dao.find(filters);
		assertEquals(4, users.size());

		PropertyFilter dateGtFilter = new PropertyFilter("GTD_createTime", "2046-01-01 10:00:22");
		filters = new ArrayList<PropertyFilter>();
		filters.add(dateGtFilter);
		users = dao.find(filters);
		assertEquals(0, users.size());
	}

	@Test
	public void findPageByHqlAutoCount() {
		PageRequest pageRequest = new PageRequest(1, 3);
		Page<User> page = dao.findPage(pageRequest, "from User user");
		assertEquals(4L, page.getTotalItems());

		page = dao.findPage(pageRequest, "select user from User user");
		assertEquals(4L, page.getTotalItems());

		page = dao.findPage(pageRequest, "select user from User user order by id");
		assertEquals(4L, page.getTotalItems());

		page = dao.findPage(pageRequest, "select user from User user where user.id=1 order by id");
		assertEquals(1L, page.getTotalItems());
	}
}
