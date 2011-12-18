package com.easeframe.demo.showcase.unit.schedule;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.easeframe.core.log.MockLog4jAppender;
import com.easeframe.core.test.data.Fixtures;
import com.easeframe.core.test.spring.SpringTxTestCase;
import com.easeframe.core.utils.Threads;
import com.easeframe.demo.showcase.schedule.JdkExecutorJob;

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext-test.xml", "/schedule/applicationContext-executor.xml" })
@TransactionConfiguration(transactionManager = "defaultTransactionManager")
public class JdkExecutorJobTest extends SpringTxTestCase {

	@Test
	public void scheduleJob() throws Exception {
		Fixtures.reloadAllTable(dataSource, "/data/sample-data.xml");

		//加载测试用logger appender
		MockLog4jAppender appender = new MockLog4jAppender();
		appender.addToLogger(JdkExecutorJob.class);

		//等待任务启动
		Threads.sleep(3000);

		//验证任务已执行
		assertEquals(1, appender.getAllLogs().size());
		assertEquals("There are 6 user in database.", appender.getFirstMessage());
	}
}