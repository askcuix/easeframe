package com.easeframe.demo.miniservice.ws.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.dozer.DozerBeanMapper;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.easeframe.demo.miniservice.entity.User;
import com.easeframe.demo.miniservice.service.AccountManager;
import com.easeframe.demo.miniservice.ws.UserWebService;
import com.easeframe.demo.miniservice.ws.WsConstants;
import com.easeframe.demo.miniservice.ws.dto.UserDTO;
import com.easeframe.demo.miniservice.ws.result.AuthUserResult;
import com.easeframe.demo.miniservice.ws.result.CreateUserResult;
import com.easeframe.demo.miniservice.ws.result.GetAllUserResult;
import com.easeframe.demo.miniservice.ws.result.GetUserResult;
import com.easeframe.demo.miniservice.ws.result.WSResult;

/**
 * WebService服务端实现类.
 * 
 * 客户端实现见功能测试用例.
 * 
 * @author Chris
 *
 */
//serviceName与portName属性指明WSDL中的名称, endpointInterface属性指向Interface定义类.
@WebService(serviceName = "UserService", portName = "UserServicePort", endpointInterface = "com.easeframe.demo.miniservice.ws.UserWebService", targetNamespace = WsConstants.NS)
public class UserWebServiceImpl implements UserWebService {

	private static Logger logger = LoggerFactory.getLogger(UserWebServiceImpl.class);

	private AccountManager accountManager;

	private DozerBeanMapper dozer;
	
	private Validator validator;

	/**
	 * @see UserWebService#getAllUser()
	 */
	@Override
	public GetAllUserResult getAllUser() {
		GetAllUserResult result = new GetAllUserResult();

		//获取User列表并转换为UserDTO列表.
		try {
			List<User> userEntityList = accountManager.getAllInitializedUser();
			List<UserDTO> userDTOList = new ArrayList<UserDTO>();

			for (User userEntity : userEntityList) {
				userDTOList.add(dozer.map(userEntity, UserDTO.class));
			}
			result.setUserList(userDTOList);
			return result;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return result.buildDefaultErrorResult();
		}
	}

	/**
	 * @see UserWebService#getUser()
	 */
	@Override
	public GetUserResult getUser(Long id) {
		GetUserResult result = new GetUserResult();

		//校验请求参数
		try {
			Assert.notNull(id, "id is required");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return result.buildResult(WSResult.PARAMETER_ERROR, e.getMessage());
		}

		//获取用户
		try {
			User entity = accountManager.getInitedUser(id);
			UserDTO dto = dozer.map(entity, UserDTO.class);

			result.setUser(dto);
			return result;
		} catch (ObjectNotFoundException e) {
			String message = "User is not exist (id: " + id + ")";
			logger.error(message, e);
			return result.buildResult(WSResult.PARAMETER_ERROR, message);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return result.buildDefaultErrorResult();
		}
	}

	/**
	 * @see UserWebService#createUser(UserDTO)
	 */
	@Override
	public CreateUserResult createUser(UserDTO user) {
		CreateUserResult result = new CreateUserResult();

		//校验请求参数
		try {
			Assert.notNull(user, "User is required");
			Assert.isNull(user.getId(), "User's id is required");
			
			//校验User内容
			Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(user);
			if (!constraintViolations.isEmpty()) {
				ConstraintViolation<UserDTO> violation = constraintViolations.iterator().next();
				String message = violation.getPropertyPath() + " " + violation.getMessage();
				throw new IllegalArgumentException(message);
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return result.buildResult(WSResult.PARAMETER_ERROR, e.getMessage());
		}

		//保存用户
		try {
			User userEntity = dozer.map(user, User.class);
			accountManager.saveUser(userEntity);
			result.setUserId(userEntity.getId());
			return result;
		} catch (ConstraintViolationException e) {
			String message = "Create user occur unique constraint violation (User: " + user + ")";
			logger.error(message, e);
			return result.buildResult(WSResult.PARAMETER_ERROR, message);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return result.buildDefaultErrorResult();
		}
	}

	/**
	 * @see UserWebService#authUser(String, String)
	 */
	@Override
	public AuthUserResult authUser(String loginName, String password) {
		AuthUserResult result = new AuthUserResult();

		//校验请求参数
		try {
			Assert.hasText(loginName, "loginName is required");
			Assert.hasText(password, "password is required");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return result.buildResult(WSResult.PARAMETER_ERROR, e.getMessage());
		}

		//认证
		try {
			if (accountManager.authenticate(loginName, password)) {
				result.setValid(true);
			} else {
				result.setValid(false);
			}
			return result;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return result.buildDefaultErrorResult();
		}
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	@Autowired
	public void setDozer(DozerBeanMapper dozer) {
		this.dozer = dozer;
	}
	
	@Autowired
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

}
