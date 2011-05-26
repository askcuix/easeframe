package com.easeframe.demo.springmvc.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件Controller.
 * 
 * 演示Spring MVC中的文件上传。
 * 
 * @author Chris
 * 
 */
@Controller
@RequestMapping("/fileupload")
public class FileUploadController {
	private Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	@RequestMapping(method = RequestMethod.GET)
	public void fileUploadForm() {
		logger.debug("Inovke fileupload form!");
	}

	@RequestMapping(method = RequestMethod.POST)
	public void processUpload(@RequestParam MultipartFile file, Model model) throws Exception {
		String message = "File '" + file.getOriginalFilename() + "' uploaded successfully";
		// prepare model for rendering success message in this request
		model.addAttribute("message", message);
		logger.debug(message);
	}
}
