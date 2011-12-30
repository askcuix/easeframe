package com.easeframe.demo.showcase.email;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.easeframe.core.utils.velocity.Velocitys;

/**
 * MIME邮件服务类.
 * 
 * 由Velocity引擎生成的的html格式邮件, 并带有附件.
 * 
 * @author Chris
 *
 */
public class MimeMailService {

	private static final String DEFAULT_ENCODING = "utf-8";

	private static Logger logger = LoggerFactory.getLogger(MimeMailService.class);

	private JavaMailSender mailSender;

	private VelocityEngine velocityEngine;

	private String templateFileName;

	/**
	 * 发送MIME格式的用户修改通知邮件.
	 */
	public void sendNotificationMail(String userName) {

		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);

			helper.setTo("easeframe@gmail.com");
			helper.setFrom("easeframe@gmail.com", "EaseFrame Team");
			helper.setSubject("用户修改通知");

			String content = generateContent(userName);
			helper.setText(content, true);

			Resource inlineResource = new ClassPathResource("/email/signiture.jpg");
			helper.addInline("signiture", inlineResource);

			File attachment = generateAttachment();
			helper.addAttachment("mailAttachment.txt", attachment);

			mailSender.send(msg);
			logger.info("HTML版邮件已发送至easeframe@gmail.com");
		} catch (MessagingException e) {
			logger.error("构造邮件失败", e);
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
	}

	/**
	 * 使用Velocity生成html格式内容.
	 */
	private String generateContent(String userName) throws MessagingException {

		Map<String, String> context = Collections.singletonMap("userName", userName);
		return Velocitys.renderFile(templateFileName, velocityEngine, DEFAULT_ENCODING, context);
	}

	/**
	 * 获取classpath中的附件.
	 */
	private File generateAttachment() throws MessagingException {
		try {
			Resource resource = new ClassPathResource("/email/mailAttachment.txt");
			return resource.getFile();
		} catch (IOException e) {
			logger.error("构造邮件失败,附件文件不存在", e);
			throw new MessagingException("附件文件不存在", e);
		}
	}

	/**
	 * Spring的MailSender.
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}
}
