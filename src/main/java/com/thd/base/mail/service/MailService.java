package com.thd.base.mail.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thd.base.dao.BaseDao;
import com.thd.base.mail.model.Mail;
import com.thd.base.mail.util.MailSenderUtil;
import com.thd.base.mail.vo.MailVo;
import com.thd.param.model.Param;
import com.thd.param.model.Param.ParamType;
import com.thd.param.service.ParamService;

@Service
public class MailService {
	@Autowired
	private BaseDao baseDao;

	@Autowired
	private ParamService paramService;
	@Autowired
	private MailSenderUtil mailTemplate;

	/**
	 * 邮件数据Save
	 */
	public List<Mail> save(MailVo mailVo) {
		List<Mail> mails = new ArrayList<Mail>();
		//		if (mailVo != null && mailVo.getMailReceivers().size() > 0) {
		//			for (int i = 0; i < mailVo.getMailReceivers().size(); i++) {
		Mail mail = new Mail();
		mail.setCaseCode(mailVo.getCaseCode());
		mail.setMailSender(mailVo.getMailSender());
		mail.setMailReceiver(mailVo.getMailReceiver());
		mail.setMailSubject(mailVo.getMailSubject());
		mail.setMailContents(mailVo.getMailContents());
		mail.setSendResultFlg(mailVo.getSendResultFlg());
		baseDao.save(mail);
		mails.add(mail);
		//			}
		//
		//		}
		return mails;
	}

	/**
	 * 发送邮件
	 */
	public Boolean sendMail(MailVo mail) {
		// mail连接处理
		List<Param> paramList = paramService.getListByValueAndType(ParamType.SYSTEM_PATH, "systemPath");
		if (paramList != null && paramList.size() > 0) {
			// 系统连接
			mail.setMailHref(paramList.get(0).getText());
		}

		return mailTemplate.sendNotificationMail(mail);
	}
}
