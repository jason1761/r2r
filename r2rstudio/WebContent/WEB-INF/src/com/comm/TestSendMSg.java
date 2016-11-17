package com.comm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSendMSg {
 
	public static void main(String[] args) throws Exception {
		java.util.Scanner scanner = new java.util.Scanner(System.in);
		System.out.print("輸入電話:");
		String phone = scanner.next();
		
		System.out.print("輸入訊息:");
		String sendMsg = scanner.next();
		if(sendMsg.length()>70) sendMsg = sendMsg.substring(0,70); 
		System.out.println("電話："+phone);
		System.out.println("訊息："+sendMsg);
		ccnet(phone, sendMsg);
	}
	
	public static void ccnet(String phone, String sendMsg) {
		String memberID = "SYOHO";
		String pass = "5959";
		String spr = new SimpleDateFormat("ddhhmmss").format(new Date());
		String psEncode = str2EnCoding(memberID + ":" + pass + ":" + phone.substring(4) + ":" +spr, "MD5");
		String data = "";
//		String urlStr = "http://60.251.6.233:30080/sms/SMSBridge.php";
		String urlStr = "http://www.yoyo8.com.tw/SMSBridge.php";
		int responseCode = 0;
		BufferedReader in = null;
		String rtnVal = "";
		try {
			data += "MemberID=SYOHO";
			data += "&Password=" + psEncode;
			data += "&MobileNo=" + phone;
			data += "&CharSet=U";
			data += "&SMSMessage=" + URLEncoder.encode(sendMsg, "UTF-8").toLowerCase();
			data += "&SourceProdID=" +phone.substring(4);
			data += "&SourceMsgID=" +  spr;
			System.out.println("data -- > "+data);
			URL url = new URL(urlStr);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			((HttpURLConnection) con).setRequestMethod("POST");
			// con.setRequestProperty("content-type", "text/html;
			// charset=big5");
			con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(data);
			wr.flush();
			wr.close();
			responseCode = con.getResponseCode();
			if (responseCode == 200) {
				in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				rtnVal = response.toString();
			}

		} catch (IOException e) {
			System.out.println(e);
		} finally {
			System.out.println("response Code  === " +responseCode);
			System.out.println("status === "+rtnVal);
		}

	}
	
	
	public static String str2EnCoding(String str, String keyCode) {
		String rtnVal = "";
		StringBuffer descStr = new StringBuffer("");
		descStr.setLength(0);
		descStr.append(str);
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance(keyCode);
			sha.update(descStr.toString().getBytes());
			byte[] b = sha.digest();
			String hs = "";
			String stmp = "";
			for (int n = 0; n < b.length; n++) {
				stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
				if (stmp.length() == 1)
					hs = hs + "0" + stmp;
				else
					hs = hs + stmp;
			}
			rtnVal = hs;
		} catch (Exception e) {
			rtnVal = "";
		}
		return rtnVal.toUpperCase();
	}
}
