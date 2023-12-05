package com.g3way.sicims.util.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestParam;

import com.imoxion.sensrelay.client.ImRelayClient;
import com.imoxion.sensrelay.client.ImRelayClientProc;

import egovframework.com.cmm.service.EgovProperties;

public class MailUtil {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(MailUtil.class);


	/*
	 * 서울시 통합 메일
	 */
	public static HashMap<String, Object> relayMailSender(String toUser) throws MailException {
		HashMap<String, Object> result = new HashMap<String, Object>();

		String clientKey= EgovProperties.getProperty("Globals.seoul.mail.clientKey");
		String fromUser = EgovProperties.getProperty("Globals.seoul.mail.fromUser");
		String subject	= EgovProperties.getProperty("Globals.seoul.mail.subject");
		String body		= EgovProperties.getProperty("Globals.seoul.mail.body");
		String reset 	= EgovProperties.getProperty("Global.password.reset");

		try {
			body = body.concat("\n사용자ID : " + toUser);
			body = body.concat("\n비밀번호 : " 	 + reset);


			ImRelayClientProc r = new ImRelayClientProc();

			// 반드시 발급 받은 클라이언트 키를 입력(24자리)
			r.setClient_key(clientKey);

			// 제목을 입력한다.
			r.setSubject(subject);
			// 송신자 이메일 주소를 입력한다.
			// 입력된 송신자 이메일 주소로 메일이 전송된 결과가 전송되므로 정확한 이메일 주소 입력 필요.
			r.setFrom(fromUser);
			// 메일 본문에 나올 To 헤더를 입력한다. 실제 메일을 받으면 보이는 수신자 정보
			r.setTo(toUser);
			// 수신자를 입력한다.
			r.addReceipt(toUser);
			// 메일 본문을 입력한다.
			r.setBody(body);

			// 메일을 전송한다.
			ImRelayClient rClient = r.doSend();

			if(rClient.getResultStatus().equals("00")){
				result.put("mailStatus", 	"success");
				result.put("mailKey", 		rClient.getKey());
				result.put("clientKey", 	rClient.getClientKey());
			} else {
				result.put("mailStatus", 	"fail");
				result.put("mailMessage", 	rClient.getResultMsg());
			}
		} catch (MailException e) {
			e.printStackTrace();
			result.put("mailStatus", 	"fail");
			result.put("mailMessage", 	e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("mailStatus", 	"fail");
			result.put("mailMessage", 	e.getMessage());
		}

		return result;
	}




	/*
	 * org.springframework.mail.javamail.JavaMailSenderImpl 이용
	 */
	public static HashMap<String, Object> javaMailSender(JavaMailSender javaMailSender,  String toUser) throws MailException {
		HashMap<String, Object> result = new HashMap<String, Object>();

		String fromUser = EgovProperties.getProperty("Globals.mail.fromUser");
		String subject	= EgovProperties.getProperty("Globals.mail.subject");
		String text		= EgovProperties.getProperty("Globals.mail.text");
		String reset 	= EgovProperties.getProperty("Global.password.reset");

		try {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			text = text.concat("\n사용자ID : " + toUser);
			text = text.concat("\n비밀번호 : " 	 + reset);

			simpleMailMessage.setTo(toUser);
			simpleMailMessage.setSubject(subject);
			simpleMailMessage.setText(text);
			simpleMailMessage.setFrom(fromUser);

		    javaMailSender.send(simpleMailMessage);

			result.put("mailStatus", "success");
		} catch (MailException e) {
			result.put("mailStatus", 	"fail");
			result.put("mailMessage", 	e.getMessage());
		}

		return result;
	}



	public static HashMap<String, Object> egovMailSender(@RequestParam HashMap<String, Object> param, @RequestParam(value="toUserList[]") String[] toUserList) throws EmailException {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
			if(toUserList != null) {
				for(int i = 0; i < toUserList.length; i++) {
					MultiPartEmail email = new MultiPartEmail();
					email.setCharset("euc-kr");
					email.setHostName(EgovProperties.getProperty("Globals.mail.host"));
					email.setAuthentication(EgovProperties.getProperty("Globals.mail.user"), EgovProperties.getProperty("Globals.mail.password"));
					email.setSmtpPort(new Integer(EgovProperties.getProperty("Globals.mail.port")));
					email.setFrom(EgovProperties.getProperty("Globals.mail.user"), "국토안전관리원");
					if(param != null) {
						email.setSubject(param.get("title").toString());
						email.setMsg(param.get("content").toString());
					}
					email.addTo(toUserList[i], "Me");

					email.send();
				}
			}

			result.put("mailStatus", "success");
		} catch(EmailException e) {
			result.put("mailStatus", "fail");
		}

		return result;
	}

	public static HashMap<String, Object> mailSender(@RequestParam HashMap<String, Object> param, ArrayList<String> toUserList) throws AddressException {
		HashMap<String, Object> result = new HashMap<String, Object>();

		String host 	= EgovProperties.getProperty("Globals.mail.host");
		String port 	= EgovProperties.getProperty("Globals.mail.port");
		String user 	= EgovProperties.getProperty("Globals.mail.user");
		String password = EgovProperties.getProperty("Globals.mail.password");
		String fromUser = EgovProperties.getProperty("Globals.mail.fromUser");

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);


		//Properties props = new Properties();
		//props.put("mail.smtp.starttls.enable", "true");
		//props.put("mail.smtp.host", "smtp.naver.com");
		//props.put("mail.smtp.auth", "true");
		//props.put("mail.smtp.port", "587");


		//props.put("mail.smtp.ssl.enable", "true");
		//props.put("mail.smtp.ssl.trust", "smtp.naver.com");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		if(toUserList != null) {
			InternetAddress[] arrUserList = new InternetAddress[toUserList.size()];
			for(int i = 0; i < toUserList.size(); i++) {
				arrUserList[i] = new InternetAddress(toUserList.get(i));
			}

			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(fromUser);
				message.addRecipients(Message.RecipientType.TO, arrUserList);
				message.setSubject(param.get("title").toString());
				message.setText(param.get("content").toString());

				Transport.send(message);
				result.put("mailStatus", "success");
			} catch (MessagingException e) {
				LOG.debug(e.getMessage());
				result.put("mailStatus", "fail");
			} catch (Exception e) {
				LOG.debug(e.getMessage());
				result.put("mailStatus", "fail");
			}
		}

		return result;
	}



}
