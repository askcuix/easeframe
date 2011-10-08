package com.easeframe.demo.showcase.common.dao;

import org.springframework.stereotype.Component;

import com.easeframe.core.orm.hibernate.HibernateDao;
import com.easeframe.demo.showcase.common.entity.Role;

/**
 * 角色对象的泛型Hibernate Dao.
 * 
 * @author Chris
 *
 */
@Component
public class RoleHibernateDao extends HibernateDao<Role, String> {

}
