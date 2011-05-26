package com.easeframe.demo.miniservice.unit.ws;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.dozer.DozerBeanMapper;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.easeframe.demo.miniservice.data.AccountData;
import com.easeframe.demo.miniservice.entity.User;
import com.easeframe.demo.miniservice.service.AccountManager;
import com.easeframe.demo.miniservice.ws.dto.UserDTO;
import com.easeframe.demo.miniservice.ws.impl.UserWebServiceImpl;
import com.easeframe.demo.miniservice.ws.result.AuthUserResult;
import com.easeframe.demo.miniservice.ws.result.GetAllUserResult;
import com.easeframe.demo.miniservice.ws.result.WSResult;

/**
 * User Web Service的单元测试用例,测试WebService操作的返回码.
 * 
 * 使用EasyMock对AccountManager进行模拟.
 * 
 * @author Chris
 *
 */
public class UserWebServiceTest {
	private IMocksControl control = EasyMock.createControl();
	private UserWebServiceImpl userWebService;
	private AccountManager mockAccountManager;

	@Before
	public void setUp() {
		userWebService = new UserWebServiceImpl();
		userWebService.setDozer(new DozerBeanMapper());
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		userWebService.setValidator(factory.getValidator());
		
		//创建mock对象
		mockAccountManager = control.createMock(AccountManager.class);
		userWebService.setAccountManager(mockAccountManager);
	}

	@After
	public void tearDown() {
		//确认的脚本都已执行
		control.verify();
	}

	/**
	 * 测试dozer正确映射.
	 */
	@Test
	public void dozerBinding() {
		User user = AccountData.getRandomUserWithAdminRole();
		List<User> list = Collections.singletonList(user);
		EasyMock.expect(mockAccountManager.getAllInitializedUser()).andReturn(list);
		control.replay();

		GetAllUserResult result = userWebService.getAllUser();
		assertEquals(WSResult.SUCCESS, result.getCode());
		UserDTO dto = result.getUserList().get(0);
		assertEquals(user.getLoginName(), dto.getLoginName());
		assertEquals(user.getRoleList().get(0).getName(), dto.getRoleList().get(0).getName());
	}

	/**
	 * 测试参数校验.
	 */
	@Test
	public void validateParamter() {
		control.replay();
		WSResult result = userWebService.createUser(null);
		assertEquals(WSResult.PARAMETER_ERROR, result.getCode());
		
		User testUser = AccountData.getRandomUserWithAdminRole();
		UserDTO userDTOWithoutLoginName = new DozerBeanMapper().map(testUser, UserDTO.class);
		userDTOWithoutLoginName.setLoginName(null);
		result = userWebService.createUser(userDTOWithoutLoginName);
		assertEquals(WSResult.PARAMETER_ERROR, result.getCode());

		testUser = AccountData.getRandomUserWithAdminRole();
		UserDTO userDTOWitWrongEmail = new DozerBeanMapper().map(testUser, UserDTO.class);
		userDTOWitWrongEmail.setEmail("abc");
		result = userWebService.createUser(userDTOWitWrongEmail);
		assertEquals(WSResult.PARAMETER_ERROR, result.getCode());

		testUser = AccountData.getRandomUserWithAdminRole();
		UserDTO userDTOWithoutRole = new DozerBeanMapper().map(testUser, UserDTO.class);
		userDTOWithoutRole.getRoleList().clear();
		result = userWebService.createUser(userDTOWithoutRole);
		assertEquals(WSResult.PARAMETER_ERROR, result.getCode());
	}

	/**
	 * 测试系统内部抛出异常时的处理.
	 */
	@Test
	public void handleException() {
		EasyMock.expect(mockAccountManager.getAllInitializedUser()).andThrow(new RuntimeException("Expected exception.."));
		control.replay();

		GetAllUserResult result = userWebService.getAllUser();
		assertEquals(WSResult.SYSTEM_ERROR, result.getCode());
		assertEquals(WSResult.SYSTEM_ERROR_MESSAGE, result.getMessage());
	}

	/**
	 * 用户认证测试.
	 * 分别测试正确用户名与正确,错误密码,无密码三种情况的返回码.
	 */
	@Test
	public void authUser() {
		//准备数据,录制脚本
		EasyMock.expect(mockAccountManager.authenticate("admin", "admin")).andReturn(true);
		EasyMock.expect(mockAccountManager.authenticate("admin", "errorPasswd")).andReturn(false);
		control.replay();

		//执行输入正确的测试
		AuthUserResult result = userWebService.authUser("admin", "admin");
		assertEquals(WSResult.SUCCESS, result.getCode());
		assertEquals(true, result.isValid());

		//执行输入错误的测试
		result = userWebService.authUser("admin", "errorPasswd");
		assertEquals(WSResult.SUCCESS, result.getCode());
		assertEquals(false, result.isValid());

		result = userWebService.authUser("admin", "");
		assertEquals(WSResult.PARAMETER_ERROR, result.getCode());
		assertEquals(false, result.isValid());
	}
}
