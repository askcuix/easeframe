package com.easeframe.demo.springmvc.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.easeframe.demo.springmvc.web.command.LoginCommand;

/**
 * 
 * 
 * @author Chris
 *
 */
@Controller
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginForm(Model model, @ModelAttribute LoginCommand command) {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, @ModelAttribute LoginCommand command) throws Exception {
		UsernamePasswordToken token = new UsernamePasswordToken(command.getUsername(), command.getPassword(),
				command.isRememberMe());
		try {
			SecurityUtils.getSubject().login(token);
		} catch (IncorrectCredentialsException e) {
			logger.error("Invalid username or password", e);
			model.addAttribute("message", "login.fail");
			return showLoginForm(model, command);
		} catch (AuthenticationException e) {
			logger.error("Authentication exception", e);
			model.addAttribute("message", "login.fail");
			return showLoginForm(model, command);
		}
		return "redirect:/user/list";
	}

	@RequestMapping("/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/login";
	}

}
