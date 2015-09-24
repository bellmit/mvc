package com.thd.base.mail.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thd.base.mail.model.Mail;
import com.thd.base.mail.service.MailService;
import com.thd.base.mail.vo.MailVo;

@Controller
@RequestMapping("/mail/*")
public class MailController {
    @Resource
    MailService mailService;

    @RequestMapping("/toSendMailForm")
    public String toSendMailForm(HttpServletRequest request, HttpServletResponse response) {
        return "/mail/mailForm";
    }

    @RequestMapping(value = "/sendMail")
    @ResponseBody
    public ModelMap sendMail(HttpServletRequest request, MailVo mail) {
        ModelMap map = new ModelMap();
        mail.setCaseCode("投诉110");
        mail.setMailSender("李德成");
        List<String> userList = new ArrayList<String>();
        userList.add("玉皇大帝");
        mail.setMailReceivers(userList);
        mail.setMailSubject("测试邮件!");
        mail.setMailHref("http://v4.21tb.com.com");
        Boolean sendResultFlg = mailService.sendMail(mail);// 发送邮件
        mail.setSendResultFlg(sendResultFlg);
        List<Mail> mails = mailService.save(mail);// 保存邮件到邮件列表
        if (mails.size() > 0) {
            map.put("msg", "发送成功!");
        } else {
            map.put("msg", "发送失败!");
        }
        return map;
    }

}
