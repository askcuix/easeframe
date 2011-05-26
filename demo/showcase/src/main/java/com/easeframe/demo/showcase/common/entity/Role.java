package com.easeframe.demo.showcase.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 角色.
 * 
 * @author Chris
 *
 */

@Entity
@Table(name = "EF_ROLE")
public class Role extends IdEntity {

	private String name;

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
