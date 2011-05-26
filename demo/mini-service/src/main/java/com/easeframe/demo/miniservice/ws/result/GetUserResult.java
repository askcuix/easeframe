package com.easeframe.demo.miniservice.ws.result;

import javax.xml.bind.annotation.XmlType;

import com.easeframe.demo.miniservice.ws.WsConstants;
import com.easeframe.demo.miniservice.ws.dto.UserDTO;

/**
 * GetUser方法的返回结果.
 * 
 * @author Chris
 *
 */
@XmlType(name = "GetUserResult", namespace = WsConstants.NS)
public class GetUserResult extends WSResult {

	private UserDTO user;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
}
