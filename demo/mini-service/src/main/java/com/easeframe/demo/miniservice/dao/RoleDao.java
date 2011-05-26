package com.easeframe.demo.miniservice.dao;

import org.springframework.stereotype.Component;

import com.easeframe.core.orm.hibernate.HibernateDao;
import com.easeframe.demo.miniservice.entity.Role;

/**
 * 角色对象的泛型DAO.
 * 
 * @author Chris
 *
 */
@Component
public class RoleDao extends HibernateDao<Role, Long> {

}
