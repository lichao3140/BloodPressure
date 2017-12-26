package com.a1byone.bloodpressure.mail;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;

/**
 * 邮件工具类
 * Created by Administrator on 2017/12/26.
 */

public class SendMailUtil {

    //QQ
    private static final String HOST = "smtp.qq.com";
    private static final String PORT = "587";
    private static final String FROM_ADD = "396229938@qq.com";
    private static final String FROM_PSW = "jmqhxzkmowulcbcj";

//    //163
//    private static final String HOST = "smtp.163.com";
//    private static final String PORT = "465"; //或者465  994
//    private static final String FROM_ADD = "teprinciple@163.com";
//    private static final String FROM_PSW = "teprinciple163";
//    private static final String TO_ADD = "2584770373@qq.com";


    /**
     * 发送附件
     * @param file  发送的附件
     * @param toAdd  接收方邮箱地址
     */
    public static void send(final File file, String toAdd, String title, String content){
        final MailInfo mailInfo = createMail(toAdd, title, content);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendFileMail(mailInfo, file);
            }
        }).start();
    }

    /**
     * 发送文本
     * @param toAdd 接收方邮箱地址
     */
    public static void send(String toAdd, String title, String content){
        final MailInfo mailInfo = createMail(toAdd, title, content);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendTextMail(mailInfo);
            }
        }).start();
    }

    /**
     * 创建邮件
     * @param toAdd  接收方邮箱地址
     * @param title  邮件主题
     * @param content  邮件内容
     * @return
     */
    @NonNull
    private static MailInfo createMail(String toAdd, String title, String content) {
        Log.e("lichao", "地址:" + toAdd + " 主题:" + title + " 内容:" + content);
        final MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost(HOST);
        mailInfo.setMailServerPort(PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(FROM_ADD); // 你的邮箱地址
        mailInfo.setPassword(FROM_PSW);// 您的邮箱密码
        mailInfo.setFromAddress(FROM_ADD); // 发送的邮箱
        mailInfo.setToAddress(toAdd); // 发到哪个邮件去
        mailInfo.setSubject(title); // 邮件主题
        mailInfo.setContent(content); // 邮件文本
        return mailInfo;
    }

}
