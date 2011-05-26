package com.easeframe.demo.struts2.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.easeframe.core.orm.hibernate.HibernateDao;
import com.easeframe.demo.struts2.entity.Group;
import com.easeframe.demo.struts2.entity.User;

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
	 * 重载函数,因为Role中没有建立与User的关联,因此需要以较低效率的方式进行删除User与Role的多对多中间表.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void delete(Long id) {
		Group role = get(id);
		//查询出拥有该角色的用户,并删除该用户的角色.
		List<User> users = createQuery(QUERY_USER_BY_ROLEID, role.getId()).list();
		for (User u : users) {
			u.getGroupList().remove(role);
		}
		super.delete(role);
	}
}
