package com.easeframe.core.test.utils;

import static org.easymock.EasyMock.*;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.ThreadState;

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
	 * 用EasyMock快速創建一個已認證的用户.
	 */
	public static void mockSubject(String principal) {
		Subject subject = createNiceMock(Subject.class);
		expect(subject.isAuthenticated()).andReturn(true);
		expect(subject.getPrincipal()).andReturn(principal);
		replay(subject);

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
