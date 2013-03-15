package com.easeframe.core.lang;

import org.junit.Test;

import static org.junit.Assert.*;

public class UnicodeTest {

	@Test
	public void testContainsReplacementChar() {
		String s = "abc\uD840\uDCCA";
		assertFalse(Unicode.containsReplacementChar(s));

		s += "\uFFFD";
		assertTrue(Unicode.containsReplacementChar(s));
	}
	
	@Test
	public void testContainsSupplementaryChar(){
		String normal = "abcd";
		String unicode = "\u597D";
		String suppchar = "\uD840\uDCCA";
		
		assertFalse(Unicode.containsSupplementaryCodePoint(normal));
		assertFalse(Unicode.containsSupplementaryCodePoint(unicode));
		assertTrue(Unicode.containsSupplementaryCodePoint(suppchar));
	}
	
	@Test
	public void testSubstring(){
		String normal = "abcd";
		String suppchar = "ab\uD840\uDCCA12";
		
		assertEquals("cd", Unicode.substringByCodePoint(normal, 2));
		assertEquals("12", Unicode.substringByCodePoint(suppchar, 3));
	}
	
	@Test
	public void testSubstringRange(){
		String normal = "abcd";
		String suppchar = "ab\uD840\uDCCA12";
		
		assertEquals("bc", Unicode.substringByCodePoint(normal, 1, 3));
		assertEquals("b\uD840\uDCCA1", Unicode.substringByCodePoint(suppchar, 1, 4));
	}

}
