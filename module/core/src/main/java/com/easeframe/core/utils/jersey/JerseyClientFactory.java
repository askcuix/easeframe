package com.easeframe.core.utils.jersey;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * Jersey Client factory.
 * 
 * @author Chris
 *
 */
public final class JerseyClientFactory {

	private JerseyClientFactory() {
	}

	/**
	 * 创建JerseyClient, 设定JSON字符串使用Jackson解析.
	 */
	public static WebResource createClient(String baseUrl) {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		return client.resource(baseUrl);
	}
}
