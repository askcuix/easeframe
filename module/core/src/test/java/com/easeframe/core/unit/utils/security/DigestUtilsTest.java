package com.easeframe.core.unit.utils.security;

import java.io.IOException;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.easeframe.core.utils.security.DigestUtils;

public class DigestUtilsTest {

	@Test
	public void digestString() {
		String input = "foo message";

		System.out.println("sha1 in hex result              :" + DigestUtils.sha1Hex(input));
		System.out.println("sha1 in base64 result           :" + DigestUtils.sha1Base64(input));
		System.out.println("sha1 in base64 url result       :" + DigestUtils.sha1Base64UrlSafe(input));
	}

	@Test
	public void digestFile() throws IOException {
		Resource resource = new ClassPathResource("/log4j.properties");

		System.out.println("md5: " + DigestUtils.md5Hex(resource.getInputStream()));
		System.out.println("sha1:" + DigestUtils.sha1Hex(resource.getInputStream()));
	}
}