package com.easeframe.core.lang;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.UTF16;

/**
 * Unicode related utility tools.
 * 
 * @author Chris
 * 
 */

public class Unicode {

	/* Not exists character when translating into unicode encoding form */
	private static final int REPLACEMENT_CHAR = '\uFFFD';

	private Unicode() {
		// can not initialize
	}

	/**
	 * Check if the string contains replacement character U+FFFD.
	 * 
	 * @param str
	 *            checked string
	 * @return true if the string contains the replacement code point.
	 */
	public static boolean containsReplacementChar(String str) {
		int len = str.length();

		for (int i = 0; i < len; i++) {
			if (str.charAt(i) == REPLACEMENT_CHAR) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Check if the string contains supplementary code point.
	 * 
	 * @param str
	 *            checked string
	 * @return true if the string contains supplementary code point.
	 */
	public static boolean containsSupplementaryCodePoint(String str) {
		int len = str.length();

		for (int i = 0; i < len; i++) {
			if (UTF16.isSurrogate(str.charAt(i))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Substring begins at the specified code point index to the end of this
	 * string.
	 * 
	 * @param str
	 *            original string
	 * @param offset
	 *            the start code point index
	 * @return substring
	 */
	public static String substringByCodePoint(String str, int offset) {
		if (str == null) {
			return null;
		}

		return str.substring(UTF16.findOffsetFromCodePoint(str, offset));
	}

	/**
	 * Substring begins at the specified beginOffset to the endOffset - 1.
	 * 
	 * @param str
	 * @param beginOffset
	 * @param endOffset
	 * @return
	 */
	public static String substringByCodePoint(String str, int beginOffset,
			int endOffset) {
		if (str == null) {
			return null;
		}

		int start = UCharacter.offsetByCodePoints(str, 0, beginOffset);
		int end = UCharacter.offsetByCodePoints(str, start, endOffset
				- beginOffset);

		return str.substring(start, end);
	}
}
