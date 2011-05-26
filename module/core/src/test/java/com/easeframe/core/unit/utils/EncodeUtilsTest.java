package com.easeframe.core.unit.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.easeframe.core.utils.EncodeUtils;

/**
 * EncodeUtils Test.
 */
public class EncodeUtilsTest {

	@Test
	public void hexEncode() {
		String input = "haha,i am a very long message";
		String result = EncodeUtils.encodeHex(input.getBytes());
		assertEquals(input, new String(EncodeUtils.decodeHex(result)));
	}

	@Test
	public void base64Encode() {
		String input = "haha,i am a very long message";
		String result = EncodeUtils.encodeBase64(input.getBytes());
		assertEquals(input, new String(EncodeUtils.decodeBase64(result)));
	}

	@Test
	public void base64UrlSafeEncode() {
		String input = "haha,i am a very long message";
		String result = EncodeUtils.encodeUrlSafeBase64(input.getBytes());
		assertEquals(input, new String(EncodeUtils.decodeBase64(result)));
	}

	@Test
	public void base62Encode() {
		long num = 63;

		String result = EncodeUtils.encodeBase62(num);
		assertEquals("11", result);
		assertEquals(num, EncodeUtils.decodeBase62(result));
	}

	@Test
	public void urlEncode() {
		String input = "http://locahost/?q=中文&t=1";
		String result = EncodeUtils.urlEncode(input);
		System.out.println(result);

		assertEquals(input, EncodeUtils.urlDecode(result));
	}

	@Test
	public void xmlEncode() {
		String input = "1>2";
		String result = EncodeUtils.xmlEscape(input);
		assertEquals("1&gt;2", result);
		assertEquals(input, EncodeUtils.xmlUnescape(result));
	}

	@Test
	public void html() {
		String input = "1>2";
		String result = EncodeUtils.htmlEscape(input);
		assertEquals("1&gt;2", result);
		assertEquals(input, EncodeUtils.htmlUnescape(result));
	}
}
