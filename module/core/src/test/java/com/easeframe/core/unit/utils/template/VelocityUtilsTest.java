package com.easeframe.core.unit.utils.template;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.springframework.ui.velocity.VelocityEngineFactory;

import com.easeframe.core.utils.template.VelocityUtils;

public class VelocityUtilsTest {
	private String TEMPLATE = "hello $userName";
	private String ERROR_TEMPLATE = "hello $";

	@Test
	public void renderContent() {
		Map<String, String> model = new HashMap<String, String>();
		model.put("userName", "chris");
		String result = VelocityUtils.renderTemplateContent(TEMPLATE, model);
		assertEquals("hello chris", result);
	}

	@Test(expected = Exception.class)
	public void renderContentWithErrorTemplate() {
		Map<String, String> model = new HashMap<String, String>();
		model.put("userName", "chris");
		VelocityUtils.renderTemplateContent(ERROR_TEMPLATE, model);
	}

	@Test
	public void renderTemplateFile() throws Exception {
		VelocityEngineFactory factory = new VelocityEngineFactory();
		factory.setResourceLoaderPath("classpath:/");
		VelocityEngine engine = factory.createVelocityEngine();

		Map<String, String> model = new HashMap<String, String>();
		model.put("userName", "chris");
		String result = VelocityUtils.renderFile("testTemplate.vm", engine, "UTF-8", model);
		assertEquals("hello chris", result);
	}
}
