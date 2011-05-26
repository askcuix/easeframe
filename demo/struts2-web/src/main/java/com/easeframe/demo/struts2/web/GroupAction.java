package com.easeframe.demo.struts2.web;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.easeframe.demo.struts2.entity.Group;
import com.easeframe.demo.struts2.entity.Permission;
import com.easeframe.demo.struts2.service.AccountManager;

/**
 * 角色管理Action.
 * 
 * 演示不分页的简单管理界面.
 * 
 * @author Chris
 *
 */
@Namespace("/account")
@Results({ @Result(name = CrudActionSupport.RELOAD, location = "group.action", type = "redirect") })
public class GroupAction extends CrudActionSupport<Group> {

	private static final long serialVersionUID = 6479688879824666075L;

	private AccountManager accountManager;

	//-- 页面属性 --//
	private Long id;
	private Group entity;
	private List<Group> allGroupList;//权限组列表
	private List<Long> checkedPermissionIds;//页面中钩选的权限列表

	//-- ModelDriven 与 Preparable函数 --//

	@Override
	public Group getModel() {
		return entity;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = accountManager.getGroup(id);
		} else {
			entity = new Group();
		}
	}

	//-- CRUD Action 函数 --//
	@Override
	public String list() throws Exception {
		allGroupList = accountManager.getAllGroup();
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		checkedPermissionIds = entity.getPermissionIds();
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		//根据页面上的checkbox 整合Group的Permission Set.
		CrudActionSupport.mergeByIds(entity.getPermissionList(), checkedPermissionIds, Permission.class);
		//保存用户并放入成功信息.
		accountManager.saveGroup(entity);
		addActionMessage("Save group success");
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		accountManager.deleteGroup(id);
		addActionMessage("Delete group success");
		return RELOAD;
	}

	//-- 页面属性访问函数 --//
	/**
	 * list页面显示所有角色列表.
	 */
	public List<Group> getAllGroupList() {
		return allGroupList;
	}

	/**
	 * input页面显示所有授权列表.
	 */
	public List<Permission> getAllPermissionList() {
		return accountManager.getAllPermission();
	}

	/**
	 * input页面显示角色拥有的授权.
	 */
	public List<Long> getCheckedPermissionIds() {
		return checkedPermissionIds;
	}

	/**
	 * input页面提交角色拥有的授权.
	 */
	public void setCheckedPermissionIds(List<Long> checkedPermissionIds) {
		this.checkedPermissionIds = checkedPermissionIds;
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
}
