package com.easeframe.core.log;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

import com.easeframe.core.util.CollectionUtil;

/**
 * Log list appender which implement by logback.
 * 
 * Store log into list, mainly used for testing purpose. Use addToLogger() add
 * this appender to logger before testing.
 * 
 * @author Chris
 * 
 */
public class LogListAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

	private final List<ILoggingEvent> logs = new ArrayList<ILoggingEvent>();

	public LogListAppender() {
		start();
	}

	@Override
	protected void append(ILoggingEvent e) {
		logs.add(e);
	}

	/**
	 * Get first log.
	 * 
	 * @return first log
	 */
	public ILoggingEvent getFirstLog() {
		if (logs.isEmpty()) {
			return null;
		}
		return logs.get(0);
	}

	/**
	 * Get message from first log.
	 * 
	 * @return log message
	 */
	public String getFirstMessage() {
		if (logs.isEmpty()) {
			return null;
		}
		return getFirstLog().getMessage().toString();
	}

	/**
	 * Get last log.
	 * 
	 * @return last log
	 */
	public ILoggingEvent getLastLog() {
		if (logs.isEmpty()) {
			return null;
		}
		return CollectionUtil.getLast(logs);
	}

	/**
	 * Get message from last log.
	 * 
	 * @return log message
	 */
	public String getLastMessage() {
		if (logs.isEmpty()) {
			return null;
		}
		return getLastLog().getMessage().toString();
	}

	/**
	 * Get all logs.
	 * 
	 * @return logs
	 */
	public List<ILoggingEvent> getAllLogs() {
		return logs;
	}

	/**
	 * Get count of logs.
	 * 
	 * @return count
	 */
	public int getLogsCount() {
		return logs.size();
	}

	/**
	 * Check whether have log in this appender.
	 * 
	 * @return return true if no log
	 */
	public boolean isEmpty() {
		return logs.isEmpty();
	}

	/**
	 * Clear log from this appender.
	 */
	public void clearLogs() {
		logs.clear();
	}

	/**
	 * Add this appender to logger.
	 * 
	 * @param loggerName
	 */
	public void addToLogger(String loggerName) {
		Logger logger = (Logger) LoggerFactory.getLogger(loggerName);
		logger.addAppender(this);
	}

	/**
	 * Add this appender to logger.
	 * 
	 * @param loggerClass
	 */
	public void addToLogger(Class<?> loggerClass) {
		Logger logger = (Logger) LoggerFactory.getLogger(loggerClass);
		logger.addAppender(this);
	}

	/**
	 * Add this appender to root logger.
	 */
	public void addToRootLogger() {
		Logger logger = (Logger) LoggerFactory
				.getLogger(Logger.ROOT_LOGGER_NAME);
		logger.addAppender(this);
	}

	/**
	 * Remove this appender from logger.
	 * 
	 * @param loggerName
	 */
	public void removeFromLogger(String loggerName) {
		Logger logger = (Logger) LoggerFactory.getLogger(loggerName);
		logger.detachAppender(this);
	}

	/**
	 * Remove this appender from logger.
	 * 
	 * @param loggerClass
	 */
	public void removeFromLogger(Class<?> loggerClass) {
		Logger logger = (Logger) LoggerFactory.getLogger(loggerClass);
		logger.detachAppender(this);
	}

	/**
	 * Remove this appender from root logger.
	 */
	public void removeFromRootLogger() {
		Logger logger = (Logger) LoggerFactory
				.getLogger(Logger.ROOT_LOGGER_NAME);
		logger.detachAppender(this);
	}
}
