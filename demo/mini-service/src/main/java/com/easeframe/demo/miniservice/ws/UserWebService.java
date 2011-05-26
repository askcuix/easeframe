package com.easeframe.demo.miniservice.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.easeframe.demo.miniservice.ws.dto.UserDTO;
import com.easeframe.demo.miniservice.ws.result.AuthUserResult;
import com.easeframe.demo.miniservice.ws.result.CreateUserResult;
import com.easeframe.demo.miniservice.ws.result.GetAllUserResult;
import com.easeframe.demo.miniservice.ws.result.GetUserResult;

/**
 * JAX-WS2.0的WebService接口定义类.
 * 
 * 使用JAX-WS2.0 annotation设置WSDL中的定义.
 * 使用WSResult及其子类类包裹返回结果.
 * 使用DTO传输对象隔绝系统内部领域对象的修改对外系统的影响.
 * 
 * @author Chris
 *
 */
@WebService(name = "UserService", targetNamespace = WsConstants.NS)
public interface UserWebService {

	/**
	 * 获取所有用户.
	 */
	GetAllUserResult getAllUser();

	/**
	 * 获取用户.
	 */
	GetUserResult getUser(@WebParam(name = "id") Long id);

	/**
	 * 新建用户.
	 */
	CreateUserResult createUser(@WebParam(name = "user") UserDTO user);

	/**
	 * 验证用户名密码.
	 */
	AuthUserResult authUser(@WebParam(name = "loginName") String loginName, @WebParam(name = "password") String password);

}
