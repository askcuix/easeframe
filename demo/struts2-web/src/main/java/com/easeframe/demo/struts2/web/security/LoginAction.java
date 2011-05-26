package com.easeframe.demo.struts2.web.security;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.convention.annotation.Namespace;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * 
 * @author Chris
 *
 */

//定义URL映射对应/login.action
@Namespace("/")
public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = 7632047026509408595L;

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String logout() {
		SecurityUtils.getSubject().logout();
		return SUCCESS;
	}

}