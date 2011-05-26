package com.easeframe.demo.miniservice.functional.ws;

import static org.junit.Assert.*;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.easeframe.demo.miniservice.data.AccountData;
import com.easeframe.demo.miniservice.entity.User;
import com.easeframe.demo.miniservice.functional.BaseFunctionalTestCase;
import com.easeframe.demo.miniservice.ws.UserWebService;
import com.easeframe.demo.miniservice.ws.dto.RoleDTO;
import com.easeframe.demo.miniservice.ws.dto.UserDTO;
import com.easeframe.demo.miniservice.ws.result.AuthUserResult;
import com.easeframe.demo.miniservice.ws.result.CreateUserResult;
import com.easeframe.demo.miniservice.ws.result.GetAllUserResult;
import com.easeframe.demo.miniservice.ws.result.WSResult;

/**
 * UserService Web服务的功能测试, 测试主要的接口调用.
 * 
 * 一般使用在Spring applicaitonContext.xml中用<jaxws:client/>创建的Client, 也可以用JAXWS的API自行创建.
 * 
 * @author Chris
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-ws-client.xml" })
public class UserWebServiceTest extends BaseFunctionalTestCase {

	@Autowired
	private UserWebService userWebService;

	/**
	 * 测试认证用户,在Spring applicaitonContext.xml中用<jaxws:client/>创建Client.
	 */
	@Test
	public void authUser() {
		AuthUserResult result = userWebService.authUser("admin", "admin");
		assertEquals(true, result.isValid());
	}

	/**
	 * 测试创建用户,在Spring applicaitonContext.xml中用<jaxws:client/>创建Client.
	 */
	@Test
	public void createUser() {
		User user = AccountData.getRandomUser();

		UserDTO userDTO = new UserDTO();
		userDTO.setLoginName(user.getLoginName());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());

		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setId(1L);
		userDTO.getRoleList().add(roleDTO);

		CreateUserResult result = userWebService.createUser(userDTO);

		assertEquals(WSResult.SUCCESS, result.getCode());
		assertNotNull(result.getUserId());
	}

	/**
	 * 测试获取全部用户,使用CXF的API自行动态创建Client.
	 */
	@Test
	public void getAllUser() {
		String address = BASE_URL + "/ws/userservice";

		JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
		proxyFactory.setAddress(address);
		proxyFactory.setServiceClass(UserWebService.class);
		UserWebService userWebServiceCreated = (UserWebService) proxyFactory.create();

		//(可选)重新设定endpoint address.
		((BindingProvider) userWebServiceCreated).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);

		GetAllUserResult result = userWebServiceCreated.getAllUser();

		assertTrue(result.getUserList().size() > 0);
		UserDTO adminUser = result.getUserList().get(0);
		assertEquals("admin", adminUser.getLoginName());
		assertEquals(2, adminUser.getRoleList().size());
	}
}
