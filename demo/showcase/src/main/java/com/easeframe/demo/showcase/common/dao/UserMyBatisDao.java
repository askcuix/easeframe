package com.easeframe.demo.showcase.common.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;

import com.easeframe.demo.showcase.common.entity.User;

@Component
public class UserMyBatisDao extends SqlSessionDaoSupport {

	public User getUser(Long id) {
		return (User) getSqlSession().selectOne("Account.getUser", id);
	}
}
