package com.easeframe.demo.struts2.dao;

import org.springframework.stereotype.Component;

import com.easeframe.core.orm.hibernate.HibernateDao;
import com.easeframe.demo.struts2.entity.Permission;

/**
 * 授权对象的泛型DAO.
 * 
 * @author Chris
 *
 */
@Component
public class PermissionDao extends HibernateDao<Permission, Long> {

}
