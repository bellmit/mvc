package com.thd.base.mail.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.thd.base.mail.vo.MailVo;
import com.thd.base.util.LoggerUtil;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Component
@SuppressWarnings("unchecked")
public class MailSenderUtil {

	private static final String ENCODING = "utf-8";

	@Value("#{appProps['mail.address.from']}")
	private String mailAddressFrom;

	@Resource
	private JavaMailSender mailSender;

	@Value("#{appProps['mail.template.name']}")
	private String mailTemplateName;

	@Resource
	private Configuration freemarkerConfiguration;

	Logger logger = LoggerUtil.getLogger();

	/**
	 * 发送邮件
	 */
	public Boolean sendNotificationMail(MailVo mail) {
		Boolean result = true;
		MimeMessage msg = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, ENCODING);

			//helper.setTo(mail.getMailReceiverAddresses());//发送多人
			helper.setTo(mail.getMailReceiver());
			helper.setFrom(mailAddressFrom);
			helper.setSubject(mail.getMailSubject());
			//套用HTML模版
			buildMailTemplate(helper, mail);

			mailSender.send(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = false;
		}

		return result;
	}

	/**
	 * 套用HTML模版
	 */
	public void buildMailTemplate(MimeMessageHelper helper, MailVo mail) throws MessagingException {

		try {
			Map context = new HashMap();
			context.put("mailVo", mail);

			String content = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(
					mailTemplateName, ENCODING), context);

			helper.setText(content, true);
		} catch (IOException e) {
			throw new MessagingException("FreeMarker模板不存在！", e);
		} catch (TemplateException e) {
			throw new MessagingException("FreeMarker处理失败！", e);
		}
	}

	public void buildAttachment(MimeMessageHelper helper, Order order) {
		ClassPathResource attachment = new ClassPathResource("");
		try {
			helper.addAttachment("", attachment.getFile());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getMailAddressFrom() {
		return mailAddressFrom;
	}

	public void setMailAddressFrom(String mailAddressFrom) {
		this.mailAddressFrom = mailAddressFrom;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String getMailTemplateName() {
		return mailTemplateName;
	}

	public void setMailTemplateName(String mailTemplateName) {
		this.mailTemplateName = mailTemplateName;
	}

}
