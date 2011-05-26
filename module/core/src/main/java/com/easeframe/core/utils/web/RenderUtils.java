package com.easeframe.core.utils.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.easeframe.core.mapper.JsonMapper;
import com.easeframe.core.utils.ExceptionUtils;

/**
 * 渲染输出结果的帮助类。
 * 
 * 实现绕过jsp/freemaker直接输出文本的简化函数。
 * 
 * @author Chris
 *
 */
public class RenderUtils {
	// -- header 常量定义 --//
	protected static final String HEADER_ENCODING = "encoding";
	protected static final String HEADER_NOCACHE = "no-cache";
	protected static final String DEFAULT_ENCODING = "UTF-8";
	protected static final boolean DEFAULT_NOCACHE = true;

	protected static JsonMapper mapper = JsonMapper.buildNormalMapper();

	// -- 绕过jsp/freemaker直接输出文本的函数 --//
	/**
	 * 直接输出内容的简便函数.
	 * 
	 * eg. 
	 * render(response, "text/plain", "hello", "encoding:GBK"); 
	 * render(response, "text/plain", "hello", "no-cache:false"); 
	 * render(response, "text/plain", "hello", "encoding:GBK", "no-cache:false");
	 * 
	 * @param response
	 * @param contentType
	 * @param content
	 * 
	 * @param headers
	 *            可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
	public static void render(HttpServletResponse response, final String contentType, final String content,
			final String... headers) {
		initResponseHeader(response, contentType, headers);
		try {
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			throw ExceptionUtils.unchecked(e);
		}
	}

	/**
	 * 直接输出文本.
	 * 
	 * @param response
	 * @param text
	 * @param headers
	 * @see #render(HttpServletResponse, String, String, String...)
	 */
	public static void renderText(HttpServletResponse response, final String text, final String... headers) {
		render(response, ServletUtils.TEXT_TYPE, text, headers);
	}

	/**
	 * 直接输出HTML.
	 * 
	 * @param response
	 * @param html
	 * @param headers
	 * @see #render(HttpServletResponse, String, String, String...)
	 */
	public static void renderHtml(HttpServletResponse response, final String html, final String... headers) {
		render(response, ServletUtils.HTML_TYPE, html, headers);
	}

	/**
	 * 直接输出XML.
	 * 
	 * @param response
	 * @param xml
	 * @param headers
	 * @see #render(HttpServletResponse, String, String, String...)
	 */
	public static void renderXml(HttpServletResponse response, final String xml, final String... headers) {
		render(response, ServletUtils.XML_TYPE, xml, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param response
	 * @param jsonString
	 *            json字符串.
	 * @param headers
	 * @see #render(HttpServletResponse, String, String, String...)
	 */
	public static void renderJson(HttpServletResponse response, final String jsonString, final String... headers) {
		render(response, ServletUtils.JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON,使用Jackson转换Java对象.
	 * 
	 * @param response
	 * @param data
	 *            可以是List<POJO>, POJO[], POJO, 也可以Map名值对.
	 * @param headers
	 * @see #render(HttpServletResponse, String, String, String...)
	 */
	public static void renderJson(HttpServletResponse response, final Object data, final String... headers) {
		initResponseHeader(response, ServletUtils.JSON_TYPE, headers);

		try {
			mapper.getMapper().writeValue(response.getWriter(), data);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 直接输出支持跨域Mashup的JSONP.
	 * 
	 * @param response
	 * @param callbackName
	 *            callback函数名.
	 * @param object
	 *            Java对象,可以是List<POJO>, POJO[], POJO ,也可以Map名值对, 将被转化为json字符串.
	 * @param headers
	 */
	public static void renderJsonp(HttpServletResponse response, final String callbackName, final Object object,
			final String... headers) {
		String jsonPString = mapper.toJsonP(callbackName, object);

		//渲染Content-Type为javascript的返回内容,输出结果为javascript语句, 如callback197("{html:'Hello World!!!'}");
		render(response, ServletUtils.JS_TYPE, jsonPString, headers);
	}

	/**
	 * 分析并设置contentType与headers.
	 * 
	 * @param contentType
	 * @param headers
	 * @return HttpServletResponse
	 */
	private static HttpServletResponse initResponseHeader(HttpServletResponse response, final String contentType,
			final String... headers) {
		// 分析headers参数
		String encoding = DEFAULT_ENCODING;
		boolean noCache = DEFAULT_NOCACHE;
		for (String header : headers) {
			String headerName = StringUtils.substringBefore(header, ":");
			String headerValue = StringUtils.substringAfter(header, ":");

			if (StringUtils.equalsIgnoreCase(headerName, HEADER_ENCODING)) {
				encoding = headerValue;
			} else if (StringUtils.equalsIgnoreCase(headerName, HEADER_NOCACHE)) {
				noCache = Boolean.parseBoolean(headerValue);
			} else {
				throw new IllegalArgumentException(headerName + "Illegal header type");
			}
		}

		// 设置headers参数
		String fullContentType = contentType + ";charset=" + encoding;
		response.setContentType(fullContentType);
		if (noCache) {
			ServletUtils.setDisableCacheHeader(response);
		}

		return response;
	}
}
