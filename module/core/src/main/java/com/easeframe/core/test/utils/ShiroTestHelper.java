package com.easeframe.core.test.utils;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.ThreadState;
import org.mockito.Mockito;

/**
 * Shiro helper for test.
 * 
 * @author Chris
 *
 */
public final class ShiroTestHelper {

	private static ThreadState threadState;

	private ShiroTestHelper() {
	}

	/**
	 * 綁定Subject到當前線程.
	 */
	public static void bindSubject(Subject subject) {
		clearSubject();
		threadState = new SubjectThreadState(subject);
		threadState.bind();
	}

	/**
	 * 用Mockito快速創建一個已認證的用户.
	 */
	public static void mockSubject(String principal) {
		Subject subject = Mockito.mock(Subject.class);
		Mockito.when(subject.isAuthenticated()).thenReturn(true);
		Mockito.when(subject.getPrincipal()).thenReturn(principal);

		bindSubject(subject);
	}

	/**
	 * 清除當前線程中的Subject.
	 */
	public static void clearSubject() {
		if (threadState != null) {
			threadState.clear();
			threadState = null;
		}
	}
}
