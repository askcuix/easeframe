package com.easeframe.core.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread util.
 * 
 * @author Chris
 * 
 */
public class ThreadUtil {
	private static Logger logger = LoggerFactory.getLogger(ThreadUtil.class);

	/**
	 * Graceful shutdown refer to ExecutorService JavaDoc.
	 * 
	 * Stop accept new task first and try to complete task which exist in work
	 * queue. Invoke shutdownNow if timeout, that will be cancel pending task
	 * from work queue. If still timeout, then force shutdown.
	 * 
	 * @param pool
	 *            thread pool
	 * @param shutdownTimeout
	 *            timeout if can not shutdown
	 * @param shutdownNowTimeout
	 *            timeout if can not shutdownNow
	 * @param timeUnit
	 *            timeout unit
	 */
	public static void gracefulShutdown(ExecutorService pool,
			int shutdownTimeout, int shutdownNowTimeout, TimeUnit timeUnit) {
		pool.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(shutdownTimeout, timeUnit)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(shutdownNowTimeout, timeUnit)) {
					logger.error("Pool did not terminated");
				}
			}
		} catch (InterruptedException ie) {
			// Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Shutdown directly, cancel pending task of work queue.
	 * 
	 * @param pool
	 *            thread pool
	 * @param timeout
	 *            shutdown timeout
	 * @param timeUnit
	 *            timeout unit
	 */
	public static void normalShutdown(ExecutorService pool, int timeout,
			TimeUnit timeUnit) {
		try {
			pool.shutdownNow();
			if (!pool.awaitTermination(timeout, timeUnit)) {
				logger.error("Pool did not terminated.");
			}
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
	}
}
