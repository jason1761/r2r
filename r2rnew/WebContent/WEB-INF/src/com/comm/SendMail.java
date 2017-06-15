package com.comm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import com.db.DBUtils;

public class SendMail {
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private final String username = "taichung.r2r@gmail.com";
	private final String password = "";
	String host = "smtp.gmail.com";
	int port = 587;
	private StringBuffer sql = new StringBuffer("");
	private List tolist = null;
	private String errMsg = "";
	private String key = "";
	public SendMail(String key){
		this.key = key;
	}

	public void sendNoticeThread() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				send();
			}
		});
		thread.start();
	}
	
	public void sendCheckThread() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				sendChk();
			}
		});
		thread.start();
	}

	public void send() {
		boolean flag = false;
		String msg = "";
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			sql.setLength(0);
			sql.append(" select email from sys_user where admin_status = 'Y' and order_mailto = 'Y'");
//			System.out.println(sql.toString());
			rs = stmt.executeQuery(sql.toString());
			tolist = new ArrayList<String>();
			while (rs.next()) {
				tolist.add(rs.getString("email"));
			}

			if (rs != null) {
				rs.close();
				rs = null;
				stmt.clearBatch();
			}

			sql.setLength(0);
			sql.append(" select a.*,b.* from order_record a, sys_user b where a.user_id = b.user_id and  a.seq = '" + key + "'");
//			System.out.println(sql.toString());
			rs = stmt.executeQuery(sql.toString());
			if (rs.next()) {
				msg = "您好，訂單系統有新增一筆資料，請查看追蹤及處理，謝謝。\r\n";
				msg += "訂單編號：" + key + "\r\n";
				msg += "訂購者：" + rs.getString("fb_name") + "\r\n";
				msg += "訂購內容：\r\n";
				msg += "毛巾:" + rs.getInt("order_towel") + "條\r\n";
				msg += "手環:" + rs.getInt("order_bracelet") + "條\r\n";
				msg += "備註:\r\n" + rs.getString("memo") + "\r\n";
				msg += "請立即登入系統查詢處理，謝謝。\r\n";
				msg += "http://yohogame.myyoho.com/R2RStudio\r\n";
				msg += "Reason 2 Run Taichung web 系統";
				flag = true;
			} else {
				flag = false;
				errMsg = "查無資料。";
			}

			if (flag) {
				flag = false;
				InternetAddress[] addressTo = new InternetAddress[tolist.size()];
				for (int i = 0; i < tolist.size(); i++) {
					addressTo[i] = new InternetAddress((String) tolist.get(i));
				}
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO, addressTo);
				message.setSubject("毛巾手環訂購資料");
				message.setText(msg);
				Transport transport = session.getTransport("smtp");
				transport.connect(host, port, username, password);
				Transport.send(message);
				flag = true;
			}
		} catch (MessagingException e) {
			flag = false;
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (SQLException e) {
			flag = false;
			errMsg = e.getMessage();
			e.printStackTrace();
		} finally {
			this.clsConn();
			if (!flag)
				System.out.println("發送mail失敗>>" + errMsg);
		}
	}
	
	
	
	
	public void sendChk() {
		boolean flag = false;
		String msg = "";
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			flag = false;
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			sql.setLength(0);
			sql.append(" select email from sys_user where user_id='"+this.key+"'");
//			System.out.println(sql.toString());
			rs = stmt.executeQuery(sql.toString());
			tolist = new ArrayList<String>();
			while (rs.next()) {
				tolist.add(rs.getString("email"));
			}
				msg = "您好，您在Reason2Run TC 預約了手環或毛巾\r\n";
				msg += "我們已經收到您的登記，會盡快處理，謝謝。 \r\n";
				msg += "http://bit.ly/R2RTCweb \r\n";
				msg += "Reason 2 Run Taichung web 系統管理員\r\n";
				msg += "(此為系統自動發送，請勿回覆此信件)";

				flag = false;
				InternetAddress[] addressTo = new InternetAddress[tolist.size()];
				for (int i = 0; i < tolist.size(); i++) {
					addressTo[i] = new InternetAddress((String) tolist.get(i));
				}
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO, addressTo);
				message.setSubject("R2RTC 已經收到您的毛巾手環登記!");
				message.setText(msg);
				Transport transport = session.getTransport("smtp");
				transport.connect(host, port, username, password);
				Transport.send(message);
				flag = true;
		} catch (MessagingException e) {
			flag = false;
			errMsg = e.getMessage();
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (SQLException e) {
			flag = false;
			errMsg = e.getMessage();
			e.printStackTrace();
		} finally {
			if (!flag)
				System.out.println("發送mail失敗>>" + errMsg);
			this.clsConn();
		}
	}
	

	/**
	 * 關閉DB 注意:若有多個rs、stmt、conn，在此處須要加上
	 */
	private void clsConn() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
		}
	}

}
