package com.easeframe.demo.miniservice.ws.result;

import javax.xml.bind.annotation.XmlType;

import com.easeframe.demo.miniservice.ws.WsConstants;

/**
 * CreateUser方法的返回结果.
 * 
 * @author Chris
 *
 */
@XmlType(name = "CreateUserResult", namespace = WsConstants.NS)
public class CreateUserResult extends WSResult {

	private Long userId;

	/**
	 * 新建用户的ID.
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
