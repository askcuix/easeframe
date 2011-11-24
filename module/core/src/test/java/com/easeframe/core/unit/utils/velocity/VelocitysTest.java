package com.easeframe.core.unit.utils.velocity;

import static org.junit.Assert.*;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.springframework.ui.velocity.VelocityEngineFactory;

import com.easeframe.core.utils.velocity.Velocitys;
import com.google.common.collect.Maps;

public class VelocitysTest {

	private static final String TEMPLATE = "hello $userName";
	private static final String ERROR_TEMPLATE = "hello $";

	@Test
	public void renderContent() {
		Map<String, String> model = Maps.newHashMap();
		model.put("userName", "chris");
		String result = Velocitys.renderTemplateContent(TEMPLATE, model);
		assertEquals("hello chris", result);
	}

	@Test(expected = Exception.class)
	public void renderContentWithErrorTemplate() {
		Map<String, String> model = Maps.newHashMap();
		model.put("userName", "chris");
		Velocitys.renderTemplateContent(ERROR_TEMPLATE, model);
	}

	@Test
	public void renderTemplateFile() throws Exception {
		VelocityEngineFactory factory = new VelocityEngineFactory();
		factory.setResourceLoaderPath("classpath:/");
		VelocityEngine engine = factory.createVelocityEngine();

		Map<String, String> model = Maps.newHashMap();
		model.put("userName", "chris");
		String result = Velocitys.renderFile("testTemplate.vm", engine, "UTF-8", model);
		assertEquals("hello chris", result);
	}
}
