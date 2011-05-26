package com.easeframe.demo.springmvc.tools;

import org.eclipse.jetty.server.Server;

import com.easeframe.core.test.functional.JettyFactory;

/**
 * 使用Jetty运行调试Web应用, 在Console输入回车停止服务.
 * 
 * @author Chris
 * 
 */
public class Start {
	public static final int PORT = 8080;
	public static final String CONTEXT = "/";
	public static final String BASE_URL = "http://localhost:8080/";

	public static void main(String[] args) throws Exception {
		Server server = JettyFactory.buildNormalServer(PORT, CONTEXT);
		server.start();

		System.out.println("Hit Enter in console to stop server");
		if (System.in.read() != 0) {
			server.stop();
			System.out.println("Server stopped");
		}
	}
}
