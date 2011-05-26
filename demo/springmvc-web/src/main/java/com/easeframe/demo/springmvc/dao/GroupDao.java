package com.easeframe.demo.springmvc.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.easeframe.core.orm.hibernate.HibernateDao;
import com.easeframe.demo.springmvc.entity.Group;
import com.easeframe.demo.springmvc.entity.User;

/**
 * 角色对象的泛型DAO.
 * 
 * @author Chris
 * 
 */
@Component
public class GroupDao extends HibernateDao<Group, Long> {
	private static final String QUERY_USER_BY_ROLEID = "select u from User u left join u.groupList r where r.id=?";

	/**
	 * 重载函数,因为Group中没有建立与User的关联,因此需要以较低效率的方式进行删除User与Group的多对多中间表中的数据.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void delete(Long id) {
		Group group = get(id);
		// 查询出拥有该角色的用户,并删除该用户的角色.
		List<User> users = createQuery(QUERY_USER_BY_ROLEID, group.getId()).list();
		for (User u : users) {
			u.getGroupList().remove(group);
		}

		super.delete(group);
	}
}
