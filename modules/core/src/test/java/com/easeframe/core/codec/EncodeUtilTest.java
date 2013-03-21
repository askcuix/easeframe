package com.easeframe.core.codec;

import static org.junit.Assert.*;

import org.junit.Test;

public class EncodeUtilTest {

	@Test
	public void hexEncode() {
		String input = "here is a testing message";
		String result = EncodeUtil.encodeHex(input.getBytes());
		assertEquals(input, new String(EncodeUtil.decodeHex(result)));
	}

	@Test
	public void base64Encode() {
		String input = "here is a testing message";
		String result = EncodeUtil.encodeBase64(input.getBytes());
		assertEquals(input, new String(EncodeUtil.decodeBase64(result)));
	}

	@Test
	public void base64UrlSafeEncode() {
		String input = "here is a url testing message with '+' and '/'";
		String result = EncodeUtil.encodeUrlSafeBase64(input.getBytes());
		System.out.println(result);
		assertEquals(input, new String(EncodeUtil.decodeBase64(result)));
	}

	@Test
	public void urlEncode() {
		String input = "http://locahost/?q=中文&t=1";
		String result = EncodeUtil.urlEncode(input);
		System.out.println(result);

		assertEquals(input, EncodeUtil.urlDecode(result));
	}

	@Test
	public void xmlEncode() {
		String input = "1>2";
		String result = EncodeUtil.escapeXml(input);
		assertEquals("1&gt;2", result);
		assertEquals(input, EncodeUtil.unescapeXml(result));
	}

	@Test
	public void html() {
		String input = "1>2";
		String result = EncodeUtil.escapeHtml(input);
		assertEquals("1&gt;2", result);
		assertEquals(input, EncodeUtil.unescapeHtml(result));
	}
}
