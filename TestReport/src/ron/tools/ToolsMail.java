package ron.tools;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ToolsMail {
	
	// 发送邮件，默认部分参数
	public static boolean sendSimpleMail(String subject,String content) throws Exception{

		String[] tos =  ToolsConfig.getArray("MAIL_TOS");
		String from = ToolsConfig.get("MAIL_FROM");
		
		if(subject.isEmpty())
			subject="没有邮件主题";
		
		if(content.isEmpty())
			content="没有邮件内容";
		
		return sendMail(tos,from,subject,content);
	}

	// 发送邮件，详细参数
	public static boolean sendMail(String[] tos,String from, String subject,String content) throws Exception {
		
		String host = ToolsConfig.get("MAIL_HOST");
		if(from.isEmpty())
			from = ToolsConfig.get("MAIL_FROM");
		if(tos==null||tos.length<=0)
			tos = ToolsConfig.getArray("MAIL_TOS");

		
		// 获取系统属性
		Properties properties = System.getProperties();
		// 设置邮件服务器
		properties.setProperty("mail.smtp.host", host);
		// 获取默认的 Session 对象。
		Session session = Session.getDefaultInstance(properties);

		try {
			// 创建默认的 MimeMessage 对象。
			MimeMessage message = new MimeMessage(session);
			// Set From: 头部头字段
			message.setFrom(new InternetAddress(from));
			// Set To: 头部头字段
			for(String to : tos){
				if(to.trim().length()>0){
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(to.trim()));
					System.out.println(to);
				}
			}

			// Set Subject: 头字段
			message.setSubject(subject);
			message.setHeader("Content-Type", "text/html; charset=utf-8");
			message.setContent(content, "text/html;charset=utf-8");
			
			// 发送消息
			Transport.send(message);
			System.out.println("OK -> Send mail");
			return true;
		} catch (MessagingException mex) {
			System.out.println("ERROR -> "+mex.getMessage());
			return false;
		}
	}

}
