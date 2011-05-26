package com.easeframe.demo.springmvc.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.easeframe.demo.springmvc.dao.HibernateUtils;
import com.easeframe.demo.springmvc.entity.Group;
import com.easeframe.demo.springmvc.entity.Permission;
import com.easeframe.demo.springmvc.service.AccountManager;

/**
 * 角色管理Controller.
 * 
 * 演示不分页的简单管理界面.
 * 
 * @author Chris
 * 
 */
@Controller
@RequestMapping("/group")
public class GroupController {
	private Logger logger = LoggerFactory.getLogger(GroupController.class);

	private AccountManager accountManager;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) throws Exception {
		List<Group> roleList = accountManager.getAllGroup();

		model.addAttribute(roleList);
		return "role";
	}

	@ModelAttribute("allAuthorityList")
	public List<Permission> getAllPermissionList() {
		return accountManager.getAllPermission();
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() throws Exception {
		Group group = new Group();

		ModelAndView mv = new ModelAndView("group-input");
		mv.addObject(group);
		return mv;
	}

	@RequestMapping(value = "/input/{id}", method = RequestMethod.GET)
	public ModelAndView input(@PathVariable(value = "id") long id) throws Exception {
		Group group = accountManager.getGroup(id);

		ModelAndView mv = new ModelAndView("group-input");
		mv.addObject(group);
		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute Group group,
			@RequestParam(value = "checkedPermissionIds") List<String> checkedPermissionIds) throws Exception {
		// 根据页面上的checkbox 整合Group的Permission Set.
		List<Long> permitIds = new ArrayList<Long>();
		for (String pid : checkedPermissionIds) {
			permitIds.add(Long.parseLong(pid));
		}
		HibernateUtils.mergeByIds(group.getPermissionList(), permitIds, Permission.class);
		// 保存用户并放入成功信息.
		accountManager.saveGroup(group);

		logger.debug("Save group success.");

		return "redirect:/group/show/" + group.getId();
	}

	@RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable(value = "id") long id) throws Exception {
		Group group = accountManager.getGroup(id);

		ModelAndView mv = new ModelAndView("group-show");
		mv.addObject(group);

		return mv;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value = "id") long id) throws Exception {
		accountManager.deleteGroup(id);

		logger.debug("Delete group success.");

		return "redirect:/group/list";
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

}
