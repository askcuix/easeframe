package com.easeframe.demo.miniservice.rs.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Web Service传输User信息的DTO.
 * 
 * @author Chris
 *
 */
@XmlRootElement(name="User")
public class UserDTO {

	private Long id;
	private String loginName;
	private String name;
	private String email;

	private List<RoleDTO> roleList = new ArrayList<RoleDTO>();

	public Long getId() {
		return id;
	}

	public void setId(Long value) {
		id = value;
	}

	@NotNull
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String value) {
		loginName = value;
	}

	@NotNull
	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String value) {
		email = value;
	}

	@NotEmpty
	@XmlElementWrapper(name = "roles")
	@XmlElement(name = "Role")
	public List<RoleDTO> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleDTO> roleList) {
		this.roleList = roleList;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
