package com.gsitm.mail;

import java.util.*;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @프로그램명 : MailManager.java
 * @작성일    : 2017. 4. 20.
 * @작성자    : 김태형
 * @기능     : 메일 서버연동과 이메일을 전송을 관리한다.
 * @버전     : 1.0
 *
 */
public class MailManager {
	
	private String mailServer = "mail.gsitm.com"; //메일서버
	private String senderName; //발신자 이름
	private String senderAccountId; //발신자 메일계정
	private String senderAccountPwd; //발신자 메일계정 비번
	private boolean sslEnable = false; //보안 접속 사용여부

	public MailManager(String senderName, String senderAccountId, String senderAccountPwd){
		this.senderName = senderName;
		this.senderAccountId = senderAccountId;
		this.senderAccountPwd = senderAccountPwd;
	}
	
	public MailManager(String senderName, String senderAccountId, String senderAccountPwd, boolean sslEnable){
		this.senderName = senderName;
		this.senderAccountId = senderAccountId;
		this.senderAccountPwd = senderAccountPwd;
		this.sslEnable = sslEnable;
	}
     
	/**
	 * @작성일  : 2017. 4. 20.
	 * @작성자  : 김태형
	 * @기능    : 지정한 주소로 메일을 전송한다.
	 * @메소드명 : sendMail
	 * @param recipient - 수신자 메일주소
	 * @param subject - 발송 메일 제목
	 * @param body - 발송 메일 본문내용
	 * @return - 전송 성공여부 (성공시 "Send OK"반환)
	 */
	public String sendMail(String recipient, String subject, String body){

		//메일정보 설정
		Properties props = System.getProperties();
		props.put("mail.smtp.host", mailServer);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", mailServer);
		props.put("mail.smtp.ssl.enable", sslEnable); //보안연결 여부
		if(sslEnable)
			props.put("mail.smtp.port", 465); //보안 연결시 465번 포트 사용
		
		if(recipient == null || subject == null || body == null 
			|| "".equals(recipient) || "".equals(subject) || "".equals(body)){
				throw new RuntimeException("메일 전송 인자 누락");
			}
 		
		//인증 세션
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			String un = senderAccountId;
			String pw = senderAccountPwd;
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(un, pw);
			}
		});
		//session.setDebug(true); //디버그 코드

		//메일 전송
		try{
			Message mimeMessage = new MimeMessage(session);        
			mimeMessage.setFrom( new InternetAddress(senderAccountId, senderName) );
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			mimeMessage.setSubject(subject);
			mimeMessage.setContent(body, "text/html; charset=utf-8");
			//mimeMessage.setText(body);
			Transport.send(mimeMessage);
		}
		catch(Exception e){
			return "Send Fail: " + e.getMessage();
		}

		return "Send OK";
	}
}
