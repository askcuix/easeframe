package com.easeframe.core.unit.mapper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.BeforeClass;
import org.junit.Test;

import com.easeframe.core.mapper.JaxbMapper;
import com.easeframe.core.mapper.JaxbMapper.CollectionWrapper;

/**
 * 演示基于JAXB2.0的Java对象-XML转换及Dom4j的使用.
 *  
 * 演示用xml如下:
 * 
 * <?xml version="1.0" encoding="UTF-8"?>
 * <user id="1">
 * 	<name>chris</name>
 * 	<interests>
 * 		<interest>movie</interest>
 * 		<interest>sports</interest>
 * 	</interests>
 * </user>
 * 
 * @author Chris
 *
 */

public class JaxbMapperTest {

	private static JaxbMapper binder;

	@BeforeClass
	public static void setUp() {
		binder = new JaxbMapper(User.class, CollectionWrapper.class);
	}

	@Test
	public void objectToXml() {
		User user = new User();
		user.setId(1L);
		user.setName("chris");

		user.getInterests().add("movie");
		user.getInterests().add("sports");

		String xml = binder.toXml(user, "UTF-8");
		System.out.println("Jaxb Object to Xml result:\n" + xml);
		assertXmlByDom4j(xml);
	}

	@Test
	public void xmlToObject() {
		String xml = generateXmlByDom4j();
		User user = binder.fromXml(xml);

		System.out.println("Jaxb Xml to Object result:\n" + user);

		assertEquals(Long.valueOf(1L), user.getId());
		assertEquals(2, user.getInterests().size());
		assertEquals("movie", user.getInterests().get(0));
	}

	/**
	 * 测试以List对象作为根节点时的XML输出
	 */
	@Test
	public void toXmlWithListAsRoot() {
		User user1 = new User();
		user1.setId(1L);
		user1.setName("chris");

		User user2 = new User();
		user2.setId(2L);
		user2.setName("kimbo");

		List<User> userList = new ArrayList<User>();
		userList.add(user1);
		userList.add(user2);

		String xml = binder.toXml(userList, "userList", "UTF-8");
		System.out.println("Jaxb Object List to Xml result:\n" + xml);
	}

	/**
	 * 使用Dom4j生成测试用的XML文档字符串.
	 */
	private static String generateXmlByDom4j() {
		Document document = DocumentHelper.createDocument();

		Element root = document.addElement("user").addAttribute("id", "1");

		root.addElement("name").setText("chris");

		//List<String>
		Element interests = root.addElement("interests");
		interests.addElement("interest").addText("movie");
		interests.addElement("interest").addText("sports");

		return document.asXML();
	}

	/**
	 * 使用Dom4j验证Jaxb所生成XML的正确性.
	 */
	private static void assertXmlByDom4j(String xml) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			fail(e.getMessage());
		}
		Element user = doc.getRootElement();
		assertEquals("1", user.attribute("id").getValue());

		Element interests = (Element) doc.selectSingleNode("//interests");
		assertEquals(2, interests.elements().size());
		assertEquals("movie", ((Element) interests.elements().get(0)).getText());
	}

	@XmlRootElement
	//指定子节点的顺序
	@XmlType(propOrder = { "name", "interests" })
	private static class User {

		private Long id;
		private String name;
		private String password;

		private List<String> interests = new ArrayList<String>();

		//设置转换为xml节点中的属性
		@XmlAttribute
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		//设置不转换为xml
		@SuppressWarnings("unused")
		@XmlTransient
		public String getPassword() {
			return password;
		}

		@SuppressWarnings("unused")
		public void setPassword(String password) {
			this.password = password;
		}

		//设置对List<String>的映射, xml为<interests><interest>movie</interest></interests>
		@XmlElementWrapper(name = "interests")
		@XmlElement(name = "interest")
		public List<String> getInterests() {
			return interests;
		}

		@SuppressWarnings("unused")
		public void setInterests(List<String> interests) {
			this.interests = interests;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}
	}
}
