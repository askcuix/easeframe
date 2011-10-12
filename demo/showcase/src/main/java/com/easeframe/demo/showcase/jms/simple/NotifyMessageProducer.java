package com.easeframe.demo.showcase.jms.simple;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;

import com.easeframe.demo.showcase.common.entity.User;

/**
 * JMS用户变更消息生产者.
 * 
 * 使用jmsTemplate将用户变更消息分别发送到queue与topic.
 * 
 * @author Chris
 * 
 */
public class NotifyMessageProducer {

	private JmsTemplate jmsTemplate;
	private Destination notifyQueue;
	private Destination notifyTopic;

	public void sendQueue(final User user) {
		sendMessage(user, notifyQueue);
	}

	public void sendTopic(final User user) {
		sendMessage(user, notifyTopic);
	}

	/**
	 * 使用jmsTemplate最简便的封装convertAndSend()发送Map类型的消息.
	 */
	private void sendMessage(User user, Destination destination) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", user.getName());
		map.put("email", user.getEmail());

		jmsTemplate.convertAndSend(destination, map);
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setNotifyQueue(Destination notifyQueue) {
		this.notifyQueue = notifyQueue;
	}

	public void setNotifyTopic(Destination nodifyTopic) {
		this.notifyTopic = nodifyTopic;
	}
}
