package com.easeframe.demo.springmvc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.easeframe.core.utils.CollectionUtils;

/**
 * 角色.
 * 
 * @author Chris
 * 
 */
@Entity
@Table(name = "DSMVC_GROUP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Group extends IdEntity {

	private String name;
	private List<Permission> permissionList = new ArrayList<Permission>();

	public Group() {
		// do nothing
	}

	public Group(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany
	@JoinTable(name = "DSMVC_GROUP_PERMISSION", joinColumns = { @JoinColumn(name = "GROUP_ID") }, inverseJoinColumns = { @JoinColumn(name = "PERMISSION_ID") })
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}

	@SuppressWarnings("unchecked")
	@Transient
	public List<String> getPermissions() {
		return CollectionUtils.extractElementPropertyToList(permissionList, "name");
	}

	@Transient
	public String getPermissionNames() {
		List<String> permissionNameList = new ArrayList<String>();
		for (Permission permission : permissionList) {
			permissionNameList.add(permission.getDisplayName());
		}
		return StringUtils.join(permissionNameList, ",");
	}

	@SuppressWarnings("unchecked")
	@Transient
	public List<Long> getPermissionIds() {
		return CollectionUtils.extractElementPropertyToList(permissionList, "id");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
