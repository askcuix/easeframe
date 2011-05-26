package com.easeframe.demo.miniservice.tools;

import org.eclipse.jetty.server.Server;

import com.easeframe.core.test.utils.JettyUtils;

/**
 * 使用Jetty运行调试Web应用,在Console输入回车停止服务.
 * 
 * @author Chris
 *
 */
public class Start {

	public static final int PORT = 8080;
	public static final String CONTEXT = "/mini-service";
	public static final String BASE_URL = "http://localhost:8080/mini-service";

	public static void main(String[] args) throws Exception {
		Server server = JettyUtils.buildNormalServer(PORT, CONTEXT);
		server.start();

		System.out.println("Hit Enter in console to stop server");
		if (System.in.read() != 0) {
			server.stop();
			System.out.println("Server stopped");
		}
	}
}
