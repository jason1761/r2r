package com.comm;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {
	private List tolist = null;
	private String title = "";
	private StringBuffer msg = new StringBuffer("");
	
	public SendMail(List tolist,String title ,StringBuffer msg){
		this.tolist = tolist;
		this.title = title;
		this.msg = msg;
	}
	
	
	public void sendThread(){
		SendEmail send = new SendEmail(tolist, title, msg);
		Thread send_t = new Thread(send);
		try {
			send_t.start();
			send_t.sleep(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


class SendEmail implements Runnable{
	private final String username = "system@di-yoho.com.tw";
	private final String password = "system";
	private final String host = "smtp.di-yoho.com.tw";
	int port = 25;

	private List tolist = null;
	private String title = "";
	private StringBuffer msg = new StringBuffer("");
	
	
	public SendEmail(List tolist,String title ,StringBuffer msg){
		this.tolist = tolist;
		this.title = title;
		this.msg = msg;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);

			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			if(tolist!=null && tolist.size()>0){
				InternetAddress[] addressTo = new InternetAddress[tolist.size()];
				for (int i = 0; i < tolist.size(); i++) {
					addressTo[i] = new InternetAddress((String) tolist.get(i));
				}
				
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO, addressTo);
//				message.setSubject(
//				message.setText(msg);
//				message.setSubject(title, "ISO-8895-1");
				message.setSentDate(new Date());
//				System.out.println(title);
				message.setSubject(MimeUtility.encodeText(title,"UTF-8","B"));
				message.setContent(msg.toString(),"text/html; charset=UTF-8");
				Transport transport = session.getTransport("smtp");
				transport.connect(host, port, username, password);
				Transport.send(message);
				flag = true; 
			}
		} catch (AddressException e) {
			flag = false;
			e.printStackTrace();
		} catch (MessagingException e) {
			flag = false;
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			flag = false;
			e.printStackTrace();
		} finally{
			this.tolist = null;
			this.title = null;
			this.msg = null;
//			System.out.println("done");
		}
	}
	
}





