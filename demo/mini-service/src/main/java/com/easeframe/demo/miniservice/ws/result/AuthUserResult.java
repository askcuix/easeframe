package com.easeframe.demo.miniservice.ws.result;

import javax.xml.bind.annotation.XmlType;

import com.easeframe.demo.miniservice.ws.WsConstants;

/**
 * AuthUser方法的返回结果.
 * 
 * @author Chris
 *
 */
@XmlType(name = "AuthUserResult", namespace = WsConstants.NS)
public class AuthUserResult extends WSResult {

	private boolean valid;

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
