package com.easeframe.demo.springmvc.dao;

import org.springframework.stereotype.Component;

import com.easeframe.core.orm.hibernate.HibernateDao;
import com.easeframe.demo.springmvc.entity.User;

/**
 * 用户对象的泛型DAO类.
 * 
 * @author Chris
 * 
 */
@Component
public class UserDao extends HibernateDao<User, Long> {

}
