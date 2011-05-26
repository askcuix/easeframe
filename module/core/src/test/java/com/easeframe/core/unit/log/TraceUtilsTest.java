package com.easeframe.core.unit.log;

import static org.junit.Assert.*;

import org.apache.log4j.MDC;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easeframe.core.log.MockLog4jAppender;
import com.easeframe.core.log.TraceUtils;

public class TraceUtilsTest {

	Logger logger = LoggerFactory.getLogger(TraceUtilsTest.class);

	@Test
	public void test() {
		MockLog4jAppender appender = new MockLog4jAppender();
		appender.setLayout("%X{traceId} %m");
		appender.addToLogger(TraceUtilsTest.class);

		//begin trace
		TraceUtils.beginTrace();
		assertNotNull(MDC.get(TraceUtils.TRACE_ID_KEY));
		String traceId = (String) MDC.get(TraceUtils.TRACE_ID_KEY);
		assertNotNull(traceId);

		//log message
		logger.info("message");
		assertEquals(traceId + " message", appender.getFirstRenderedMessage());
		System.out.println(appender.getFirstRenderedMessage());

		//end trace
		TraceUtils.endTrace();
		assertNull(MDC.get(TraceUtils.TRACE_ID_KEY));
		appender.clearLogs();
		logger.info("message");
		assertEquals(" message", appender.getFirstRenderedMessage());
	}
}
