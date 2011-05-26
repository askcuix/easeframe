package com.easeframe.demo.springmvc.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

import com.easeframe.core.orm.Page;
import com.easeframe.core.orm.PageRequest;
import com.easeframe.core.orm.PropertyFilter;
import com.easeframe.core.utils.web.RenderUtils;
import com.easeframe.demo.springmvc.dao.HibernateUtils;
import com.easeframe.demo.springmvc.entity.Group;
import com.easeframe.demo.springmvc.entity.User;
import com.easeframe.demo.springmvc.service.AccountManager;
import com.easeframe.demo.springmvc.service.ServiceException;

/**
 * 用户管理Controller.
 * 
 * 演示带分页的管理界面.
 * 
 * @author Chris
 * 
 */
@Controller
@RequestMapping("/user")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	private AccountManager accountManager;

	@RequestMapping("/list")
	public String list(Page<User> page, HttpServletRequest request, Model model) throws Exception {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);

		page.setPageSize(5);

		int pageNo = page.getPageNo();
		if (pageNo != 0) {
			page.setPageNo(pageNo);
		}

		// 设置默认排序方式
		String orderBy = page.getOrderBy();
		if (StringUtils.isNotBlank(orderBy)) {
			page.setOrderBy(orderBy);
		}
		String order = page.getOrderDir();
		if (StringUtils.isNotBlank(order)) {
			page.setOrderDir(order);
		}
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrderDir(PageRequest.Sort.ASC);
		}
		page = accountManager.searchUser(page, filters);
		model.addAttribute("page", page);

		return "user";
	}

	@ModelAttribute("allGroupList")
	public List<Group> getAllGroupList() {
		return accountManager.getAllGroup();
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() throws Exception {
		User user = new User();

		ModelAndView mv = new ModelAndView("user-input");
		mv.addObject(user);
		return mv;
	}

	@RequestMapping(value = "/input/{id}", method = RequestMethod.GET)
	public ModelAndView input(@PathVariable(value = "id") long id) throws Exception {
		User user = accountManager.getUser(id);

		ModelAndView mv = new ModelAndView("user-input");
		mv.addObject(user);

		return mv;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute User user, @RequestParam(value = "checkedGroupIds") List<String> checkedGroupIds)
			throws Exception {
		// 根据页面上的checkbox选择 整合User的Roles Set
		List<Long> groupIds = new ArrayList<Long>();
		for (String gid : checkedGroupIds) {
			groupIds.add(Long.parseLong(gid));
		}
		HibernateUtils.mergeByIds(user.getGroupList(), groupIds, Group.class);
		accountManager.saveUser(user);

		return "redirect:/user/show/" + user.getId();
	}

	@RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable(value = "id") long id) throws Exception {
		User user = accountManager.getUser(id);

		ModelAndView mv = new ModelAndView("user-show");
		mv.addObject(user);

		return mv;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable(value = "id") long id) throws Exception {
		try {
			accountManager.deleteUser(id);
		} catch (ServiceException e) {
			logger.error("Delete user fail.", e);
		}

		return "redirect:/user/list";
	}

	// -- 其他Controller函数 --//
	/**
	 * 支持使用Jquery.validate Ajax检验用户名是否重复.
	 */
	@RequestMapping(value = "/checkLoginName", method = RequestMethod.GET)
	public void checkLoginName(@RequestParam(value = "loginName") String loginName,
			@RequestParam(value = "oldLoginName") String oldLoginName, HttpServletResponse response) throws Exception {
		if (accountManager.isLoginNameUnique(loginName, oldLoginName)) {
			logger.debug("Login name is unique.");
			RenderUtils.renderText(response, "true");
		} else {
			logger.warn("Login name is exist.");
			RenderUtils.renderText(response, "false");
		}
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

}
