package com.krito3.base.scaffold.common.utils;//package com.krito3.base.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
///**
// * @author kno.ci
// * @description: 邮件发送工具类
// * @date 2022/3/14 下午3:42
// */
//@Component
//@Slf4j
//public class SendEmailUtil {
//    @Autowired
//    JavaMailSender javaMailSender;
//
//    @Value("${spring.mail.username}")
//    private String from;
//
//    /**
//     * 发送邮件 text
//     *
//     * @param map
//     */
//    public void sendTextEmail(Map<String, String> map) {
//        //建立邮箱消息
//        SimpleMailMessage message = new SimpleMailMessage();
//        //发送者
//        message.setFrom(from);
//        //接收者
//        message.setTo(map.get("toUser"));
//        //发送标题
//        message.setSubject(map.get("subject"));
//        //发送内容
//        message.setText(map.get("text"));
//        javaMailSender.send(message);
//    }
//}
