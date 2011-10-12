package com.easeframe.demo.showcase.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.easeframe.demo.showcase.common.entity.Role;
import com.easeframe.demo.showcase.common.entity.User;
import com.easeframe.demo.showcase.common.service.AccountManager;

/**
 * 
 * 
 * @author Chris
 *
 */
public class ShiroDbRealm extends AuthorizingRealm {

	protected AccountManager accountManager;

	public ShiroDbRealm() {
		setCredentialsMatcher(new HashedCredentialsMatcher("SHA-1"));
	}

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = accountManager.findUserByLoginName(token.getUsername());
		if (user != null) {
			return new SimpleAuthenticationInfo(user.getLoginName(), user.getShaPassword(), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.fromRealm(getName()).iterator().next();
		User user = accountManager.findUserByLoginName(username);
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			for (Role role : user.getRoleList()) {
				info.addRole(role.getName());
			}
			return info;
		} else {
			return null;
		}
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

}
