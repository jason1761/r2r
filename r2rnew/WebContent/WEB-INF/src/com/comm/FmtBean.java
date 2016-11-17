package com.comm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import com.db.DBUtils;

import sun.misc.BASE64Decoder;

/**
 * 程式名稱：FmtBean.java 建立日期：2015/5/7 上午11:38:15 撰寫人：Jason 說明： 常用函數
 */

public class FmtBean {
	public static String errMsg = "";

	public static String getAuthCode(String str) {
		return str2EnCoding(str, "SHA-1");
	}

	/**
	 * 將map轉換成get或post網址後的param
	 * 
	 * @param param
	 * @return
	 */
	public static String chg2URLParam(Map<String, String> param) {
		String urlParam = "";
		if (param != null && param.size() > 0) {
			for (Object key : param.keySet()) {
				urlParam += "&" + (String) key + "=" + param.get(key);
			}
			if (!urlParam.equals(""))
				urlParam = urlParam.substring(1);
		}
		return urlParam;
	}

	/**
	 * 將JSON轉換成get或post網址後的param
	 * 
	 * @param param
	 * @return
	 */
	/*
	 * public static String chg2URLParam(JSONObject js) throws JSONException {
	 * Map<String, String> param = new HashMap<String, String>(); Iterator iter
	 * = js.keys(); while (iter.hasNext()) { String key = (String) iter.next();
	 * String value = js.getString(key); param.put(key, value); } return
	 * chg2URLParam(param); }
	 */

	public static String chgURLParam2JsonStr(String param) {
		String rtnVal = "";
		// APUser=APS01&APKey1=GKWOP124MAS&RQToken=A0DE85721F1269B7C6FF3DE04A740465683A753BC576E12C4CB504944E6A9A0F&CPSrv=APTG&CPID=8003100851&CPPrice=10&APMemberId=Xiaopi&FacTradeSeq=XP0001
		String[] a1 = param.split("&");
		String[] a2 = null;
		for (int i = 0; i < a1.length; i++) {
			a2 = a1[i].split("=");
			if (a2.length == 2) {
				rtnVal += ",\"" + a2[0] + "\":\"" + a2[1] + "\"";
			}
		}
		rtnVal = rtnVal.substring(1);
		rtnVal = "{" + rtnVal + "}";
		return rtnVal;
	}

	/**
	 * 將字串進行編碼(SHA-1,SHA-256,MD5)
	 * 
	 * @param str
	 *            需要編碼的字串
	 * @param keyCode
	 *            編碼格式 (SHA-1,SHA-256,MD5)
	 * @return 編碼後String
	 */
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

	/**
	 * HTTP GET 取得資料(字串)
	 * 
	 * @param url
	 * @return
	 */
	public static String getHttpUrl2Str(String url, Map<String, String> param) {
		String urlParam = "";
		if (param != null && param.size() > 0) {
			for (Object key : param.keySet()) {
				urlParam += "&" + (String) key + "=" + param.get(key);
			}
			if (!urlParam.equals(""))
				urlParam = urlParam.substring(1);
			url += "?" + urlParam;
		}
		return getHttpUrl2Str(url);

	}

	public static String getHttpUrl2Str(String url) {
		String rtnVal = "";
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
				String inputLine;
				StringBuffer rsp = new StringBuffer(); // 取得結果
				while ((inputLine = in.readLine()) != null) {
					rsp.append(inputLine);
				}
				in.close();
				rtnVal = rsp.toString();
			} else
				rtnVal = "Connection error (" + responseCode + ")>" + url;
		} catch (ProtocolException e) {
			rtnVal = "Connection error:" + e.getMessage();
		} catch (MalformedURLException e) {
			rtnVal = "Connection error:" + e.getMessage();
		} catch (IOException e) {
			rtnVal = "Connection error:" + e.getMessage();
		}
		return rtnVal;
	}

	/**
	 * HTTP POST 取得資料(字串)
	 * 
	 * @param url
	 * @return
	 */
	public static String postHttpUrl2Str(String url, Map<String, String> param) {
		String urlParam = "";
		if (param != null && param.size() > 0) {
			for (Object key : param.keySet()) {
				urlParam += "&" + (String) key + "=" + param.get(key);
			}
			if (!urlParam.equals(""))
				urlParam = urlParam.substring(1);
			// System.out.println(urlParam);
		}
		return postHttpUrl2Str(url, urlParam, null);
	}

	public static String postHttpUrl2Str(String url, String urlParam) {
		return postHttpUrl2Str(url, urlParam, null);
	}

	/**
	 * send post 不取得response 用
	 * 
	 * @param url
	 * @param urlParam
	 * @return
	 */
	public static boolean sendPostHttpUrl(String url, String urlParam) {
		boolean flag = true;
		String val = postHttpUrl2Str(url, urlParam);
		if (val.indexOf("ERROR:") > -1)
			flag = false;
		return flag;
	}

	/**
	 * send post 不取得response 用
	 * 
	 * @param url
	 * @param urlParam
	 * @return
	 */
	public static boolean sendPostHttpUrl(String url, Map<String, String> param) {
		boolean flag = true;
		String urlParam = "";
		if (param != null && param.size() > 0) {
			for (Object key : param.keySet()) {
				urlParam += "&" + (String) key + "=" + param.get(key);
			}
			if (!urlParam.equals(""))
				urlParam = urlParam.substring(1);
			// System.out.println(urlParam);
		}
		String val = postHttpUrl2Str(url, urlParam);
		if (val.indexOf("ERROR:") > -1)
			flag = false;
		return flag;
	}

	public static String postHttpUrl2Str(String url, String urlParam, Map<String, String> header) {
		String rtnVal = "";
		BufferedReader in;
		try {
			URL obj = new URL(url);
			int responseCode = 0;
			if (url.indexOf("Https") > -1) {
				HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
				con.setRequestMethod("POST");

				// header設定
				if (header != null && !header.isEmpty()) {
					for (Object key : header.keySet()) {
						con.setRequestProperty((String) key, (String) header.get(key));
					}
				}
				con.setDoOutput(true);
				con.setDoInput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(urlParam);
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
			} else {
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("POST");
				// header設定
				if (header != null && !header.isEmpty()) {
					for (Object key : header.keySet()) {
						con.setRequestProperty((String) key, (String) header.get(key));
					}
				}
				con.setDoOutput(true);
				con.setDoInput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(urlParam);
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
			}
			if (responseCode != 200 && responseCode != 204)
				rtnVal = "ERROR:" + responseCode + ">>" + url;

		} catch (MalformedURLException e) {
			rtnVal = "Connection error:" + e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			rtnVal = "Connection error:" + e.getMessage();
			e.printStackTrace();
		} catch (Exception e) {
			rtnVal = "Connection error:" + e.getMessage();
			e.printStackTrace();
		}
		return rtnVal;
	}

	/**
	 * Xiaopi 0721 改寫成Aplus版[其實語法是Json寫的XD] 取得SEQ_NO
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static String getSeqNo() {
		String rtnVal = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			StringBuffer sql = new StringBuffer("");
			stmt = conn.createStatement();
			// 取得交易序號
			sql.append("SELECT concat( 'QA', DATE_FORMAT(NOW(), '%Y'), ");
			sql.append("LPAD( LTRIM( CAST( ifnull(max(substr(question_id, 7)) + 1, 1) AS CHAR )), 5, '0' )) seq ");
			sql.append("FROM question ");
			sql.append("WHERE substr(question_id, 1, 2) = 'QA'");
			// System.out.println(sql.toString());
			rs = stmt.executeQuery(sql.toString());
			if (rs.next()) {
				rtnVal = rs.getString("seq");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rtnVal = "取得失敗";
		} finally {
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
			} catch (SQLException e) {
			}
		}
		return rtnVal;
	}

	/**
	 * Xiaopi 0721 改寫成Aplus版[其實語法是Json寫的XD] 取得SEQ_NO
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static String getViewSeqNo() {
		String rtnVal = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			StringBuffer sql = new StringBuffer("");
			stmt = conn.createStatement();
			// 取得交易序號
			sql.append("SELECT concat( 'VODR', DATE_FORMAT(NOW(), '%Y'), ");
			sql.append("LPAD( LTRIM( CAST( ifnull(max(substr(view_id, 9)) + 1, 1) AS CHAR )), 8, '0' )) seq ");
			sql.append("FROM aplus_video_order ");
			sql.append("WHERE substr(view_id, 1, 4) = 'VODR'");
			// System.out.println(sql.toString());
			rs = stmt.executeQuery(sql.toString());
			if (rs.next()) {
				rtnVal = rs.getString("seq");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rtnVal = "取得失敗";
		} finally {
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
			} catch (SQLException e) {
			}
		}
		return rtnVal;
	}

	/**
	 * 取得SEQ_NO
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static int getBillPrice(String service_ap, String product_id) {
		int rtnVal = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			StringBuffer sql = new StringBuffer("");
			sql.append(" select price from myyoho..sys_bill ");
			sql.append(" where service_ap ='" + service_ap + "' and  product_id = '" + product_id + "'");
			rs = stmt.executeQuery(sql.toString());
			// System.out.println(sql.toString());
			if (rs.next()) {
				rtnVal = rs.getInt("price");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rtnVal = 0;
		} finally {
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
			} catch (SQLException e) {
			}
		}
		return rtnVal;
	}

	/**
	 * 
	 * @param uid（必填）
	 * @param type
	 *            呼叫功能（必填）
	 * @param request
	 *            request內容
	 * @param response
	 *            response內容（必填 500）
	 * @param memo
	 *            備註
	 * @param sys_memo
	 *            非必填可填入null
	 * @return true:成功,false:失敗
	 */
	public static boolean addLogD(String uid, String type, String request, String memo, String sys_memo) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer sql = new StringBuffer("");
		try {
			flag = false;
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			sql.setLength(0);
			sql.append(" insert into aplus_usage (uid,type,request,memo,sys_memo");
			sql.append(" )values(");
			sql.append(" ?,?,?,?,?");
			sql.append(" )");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, uid);
			pstmt.setString(2, type);
			pstmt.setString(3, request);
			pstmt.setString(4, memo);
			pstmt.setString(5, sys_memo);
			if (pstmt.executeUpdate() == 1) {
				flag = true;
				conn.commit();
			}
			// System.out.println(sql.toString());
		} catch (SQLException e) {
			flag = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
			}
			e.printStackTrace();
		} catch (Exception e) {
			flag = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
			}
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
			}
		}
		return flag;
	}

	public static boolean verifyUidWithToken(String uid, String token) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");

		try {
			conn = DBUtils.getConnection();
			sql.setLength(0);
			sql.append(" select * from aplus_member_uuid where uid=? and token=?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, uid);
			pstmt.setString(2, token);
			// System.out.println(sql.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = true;
			}
			FmtBean.addLogD(uid, "VerifyToken", "token=" + token, "驗證Token", String.valueOf(flag));
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
			}
		}
		return flag;
	}

	/**
	 * 將null換成空值<br>
	 * 只能是字串
	 */
	public static String null2Str(String str) {
		String rtnVal = "";
		if (str == null || str.equals("") || str.toLowerCase().equals("null"))
			rtnVal = "";
		else {
			rtnVal = str.trim().replaceAll("'", "").replaceAll("'", "\"");
		}

		return rtnVal;
	}

	/**
	 * 取得USER 實際IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 取得錯誤訊息
	 * 
	 * @param errCode
	 * @return
	 */
	public static String getErrMsg(String errCode) {
		try {
			InputStream p = FmtBean.class.getResourceAsStream("/com/properties/errorMsg.properties");
			Properties pp = new Properties();
			pp.load(p);
			if (pp.containsKey(errCode)) {
				return pp.getProperty(errCode);
			} else {
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}

	public static String convertRequestBODY2String(InputStream requestBodyStream) {
		return convertRequestBODY2String(requestBodyStream, "ISO-8859-1", "UTF-8");
	}

	public static String convertRequestBODY2String(InputStream requestBodyStream, String encode, String decode) {
		StringBuffer buffer = new StringBuffer();
		int bufferContent = 0;
		String rtnval = "";
		do {
			try {
				bufferContent = requestBodyStream.read();
				// System.out.println(bufferContent);
				if (bufferContent > 0)
					buffer.append((char) bufferContent);
				rtnval = new String(buffer.toString().getBytes(encode), decode);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (bufferContent > 0);

		return rtnval.trim();
	}

	/**
	 * 取得APID 的共用約定回傳網址
	 * 
	 * @param apid
	 * @return URL
	 * @throws SQLException
	 */
	public static String getRtnUrl(String apid) throws SQLException {
		boolean flag = false;
		Connection conn2 = null;
		Statement stmt2 = null;
		ResultSet rs2 = null;
		StringBuffer sql = new StringBuffer("");
		String rtnUrl = "";
		try {
			conn2 = DBUtils.getConnection();
			stmt2 = conn2.createStatement();
			sql.setLength(0);
			sql.append("select rtn_url from myyoho..bill_apuser where apid = '" + apid + "'");
			rs2 = stmt2.executeQuery(sql.toString());
			if (rs2.next()) {
				rtnUrl = null2Str(rs2.getString("rtn_url"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs2 != null) {
				rs2.close();
				rs2 = null;
			}
			if (stmt2 != null) {
				stmt2.close();
				stmt2 = null;
			}
			if (conn2 != null) {
				conn2.close();
				conn2 = null;
			}
		}
		return rtnUrl;
	}

	/*	*//**
			 * Xiaopi 20150529 - 15:35 非法字元檢查 - 防止語法漏洞
			 * 
			 * @input JSONObject
			 * @Output boolean
			 */
	/*
	 * public static boolean illegalReqFilter(JSONObject json) { try {
	 * Iterator<String> keys = json.keys(); while (keys.hasNext()) { String key
	 * = (String) keys.next(); String value = json.getString(key); if
	 * (value.contains(" ") || value.replaceAll(" ", "").equals("") ||
	 * value.contains("'or'") || value.contains("'='")) { return false; } } }
	 * catch (JSONException e) { e.printStackTrace(); } return true; }
	 * 
	 *//**
		 * 確認必填欄位
		 * 
		 * @param obj
		 * @param col
		 *            必填欄位key值
		 * @return
		 *//*
		 * public static boolean chkJSONContent(JSONObject obj, String[] col) {
		 * String err = ""; boolean flag = true; String key = ""; if (col !=
		 * null && col.length > 0) { for (int i = 0; i < col.length; i++) { key
		 * = col[i]; if (!obj.has(key)) { err += "[" + key + "] "; flag = false;
		 * break; } else if (obj.isNull(key)) { err += "[" + key + "] "; flag =
		 * false; break; } } } else { err = "[no param]"; errMsg = err; flag =
		 * false; } return flag; }
		 */

	/**
	 * 輸出Log訊息至Log檔案 參數： logStr 輸出字串 fileName 寫入檔案名稱 chkFile 是否須檢核檔案是否存在(true:檢核
	 * * false:不檢核) 須先設定絕對路徑： ex. FmtBean.logFilePath="C:\\";
	 */
	public static String logFilePath = "";

	public static void LogWrite(String logStr, String fileName, boolean chkFile) {
		File fleObj = new File(logFilePath + fileName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss");
		String str = "";
		try {
			String msgFile = logFilePath + fileName;

			if (!fleObj.exists() && chkFile) {
				File expPath = new File(logFilePath);
				if (!expPath.exists())
					expPath.mkdirs(); // 新增路徑
				fleObj.createNewFile();
			}
			FileWriter Fw = new FileWriter(msgFile, true);
			str = "[" + sdf.format(new Date()) + "]" + logStr + "\r\n";
			Fw.write(str);
			Fw.close();
		} catch (IOException e) {
			// System.out.println(fileName);
			e.printStackTrace();
		} finally {
			System.out.print(str);
		}
	}

	public static String dateConvertFormat(String verDate, String ori_format, String new_format) {
		SimpleDateFormat sdf_ori = new SimpleDateFormat(ori_format);
		SimpleDateFormat sdf_new = new SimpleDateFormat(new_format);
		String rtnVal = "";
		try {
			Date convertDate = sdf_ori.parse(verDate);
			rtnVal = sdf_new.format(convertDate);
		} catch (ParseException e) {
		}
		return rtnVal;
	}

/*	*//**
	 * 上傳檔案到S3
	 * 
	 * @param request
	 * @param s3Path
	 * @return
	 *//*
	public static String s3FileName = "";
	public static String s3Url = "";

	public static boolean uploadS3FileUpload(File file, String s3Path, String s3fileName) {
		boolean flag = false;
		String fileUrl = "http://s3.hicloud.net.tw/myyoho/";
		try {
			String Banner_HicloudS3_FilePath = "";
			s3FileName = s3fileName;
			AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(FmtBean.class.getClassLoader().getResourceAsStream("AwsCredentials.properties")));
			String bucketName = "myyoho";
			String Banner_HicloudS3_Path = s3Path; // 子目錄
			Banner_HicloudS3_FilePath = Banner_HicloudS3_Path + "/" + s3fileName; // 上傳目錄
			if (file.canRead()) {
				 上傳檔案 參數 bucket, 路徑與檔名, 上傳檔案 可同檔名重複上傳 
				s3.putObject(new PutObjectRequest(bucketName, Banner_HicloudS3_FilePath, file));

				 變更權限 
				AccessControlList acl = new AccessControlList();
				CannedAccessControlList Cannedacl = null;
				Owner owner = new Owner();
				owner.setDisplayName("HN55012294");
				owner.setId("SM550122940001");
				acl.setOwner(owner);
				acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
				acl.grantPermission(GroupGrantee.AllUsers, Permission.ReadAcp);
				s3.setObjectAcl(bucketName, Banner_HicloudS3_FilePath, acl);
				s3Url = fileUrl + Banner_HicloudS3_FilePath;
				flag = true;
				// System.out.println(rtnVal);
			}
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			if (file.exists())
				file.delete();
		}
		return flag;
	}*/

/*	public static boolean uploadS3FileUpload(HttpServletRequest request, String s3Path, String s3fileName) {
		boolean flag = false;
		String fileUrl = "http://s3.hicloud.net.tw/myyoho/";
		File file = null;
		try {
			FileItem item = null;
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = upload.parseRequest(request);
			// System.out.println(items);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				item = (FileItem) iter.next();
			}

			file = new File(item.getName());
			item.write(file);

			String Banner_HicloudS3_FilePath = "";
			String fileName = "";
			if (s3fileName.equals("") || s3fileName == null) {
				fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				String file2Name = item.getName().substring(item.getName().lastIndexOf(".")); // 副檔名
				fileName += file2Name;
			} else
				fileName = s3fileName;
			s3FileName = fileName;
			AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(FmtBean.class.getClassLoader().getResourceAsStream("AwsCredentials.properties")));
			String bucketName = "myyoho";
			String Banner_HicloudS3_Path = s3Path; // 子目錄
			Banner_HicloudS3_FilePath = Banner_HicloudS3_Path + "/" + fileName; // 上傳目錄
			if (file.canRead()) {
				// 上傳檔案 參數 bucket, 路徑與檔名, 上傳檔案 可同檔名重複上傳
				s3.putObject(new PutObjectRequest(bucketName, Banner_HicloudS3_FilePath, file));

				// 變更權限
				AccessControlList acl = new AccessControlList();
				CannedAccessControlList Cannedacl = null;
				Owner owner = new Owner();
				owner.setDisplayName("HN55012294");
				owner.setId("SM550122940001");
				acl.setOwner(owner);
				acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
				acl.grantPermission(GroupGrantee.AllUsers, Permission.ReadAcp);
				s3.setObjectAcl(bucketName, Banner_HicloudS3_FilePath, acl);
				s3Url = fileUrl + Banner_HicloudS3_FilePath;
				flag = true;
				// System.out.println(rtnVal);

			}
		} catch (FileUploadException e) {
			flag = false;
			e.printStackTrace();
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			if (file.exists())
				file.delete();
		}
		return flag;
	}*/

	/**
	 * 取得下一個流水編號
	 * 
	 * @param tbname
	 *            目標table
	 * @param column
	 *            流水號欄位
	 * @param title
	 *            前置碼
	 * @param datelen
	 *            日期長度(yyyy:4 yyyymm:6 yyyymmdd:8)
	 * @param len
	 *            流水號長度
	 * @param tp
	 *            流水號類別
	 * @param [tp=2使用]
	 *            d2len 亂數長度，最長為 9 (HHmmSSsss)
	 * @return [tp=1] 前置碼(長度自訂) + 日期 + 流水號(長度自訂) [tp=2] 前置碼(長度自訂) + 日期 +
	 *         流水號(長度自訂) + 亂數號(長度自訂)
	 */
	public static String getSeqNo(String tbname, String column, String title, int datelen, int len) {
		return getSeqNo(tbname, column, title, datelen, len, 1, 0);
	}

	public static String getSeqNo(String tbname, String column, String title, int datelen, int len, int tp, int d2len) {
		String rtnVal = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int subtitle_leng = title.length() + datelen;
		try {
			conn = DBUtils.getConnection();
			StringBuffer sql = new StringBuffer("");
			stmt = conn.createStatement();
			// 取得交易序號
			sql.append(" select ");

			if (tp == 1) {
				sql.append(" a.date1  + a.dateseq ");
			} else if (tp == 2) {
				sql.append(" a.date1 + a.dateseq + substring(a.date2,1," + d2len + ") ");
			}

			sql.append(" as seq ");
			sql.append(" from (");
			sql.append(" select '" + title + "' + convert(varchar(" + datelen + "),getdate(),112) as date1 ,"); // date1
			sql.append(" convert(varchar,replicate ('0'," + len + "- len(isnull(max(substring(" + column + "," + (subtitle_leng + 1) + "," + len + "))+1,1))))");
			sql.append(" +convert(varchar,isnull(max(substring(" + column + "," + (subtitle_leng + 1) + "," + len + "))+1,1)) as dateseq"); // DATESEQ
			sql.append(" ,REPLACE(convert(varchar,getdate(),114), ':', '') as date2"); // hhmmsssss
			sql.append(" from " + tbname);
			sql.append(" where substring(" + column + ",1," + subtitle_leng + ") = '" + title + "'+ convert(varchar(" + datelen + "),getdate(),112)");
			sql.append(" ) as a");
			// System.out.println(sql.toString());
			rs = stmt.executeQuery(sql.toString());
			if (rs.next()) {
				rtnVal = rs.getString("seq");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rtnVal = "取得失敗";
		} finally {
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
			} catch (SQLException e) {
				rtnVal = "取得失敗";
			}
		}
		// System.out.println(rtnVal);
		return rtnVal;
	}

	public static String getsys_name(String sys_code) {
		String rtnVal = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getConnection();
			StringBuffer sql = new StringBuffer("");
			stmt = conn.createStatement();
			sql.setLength(0);
			sql.append(" select sys_name as fild1 from sys_menu where sys_code ='" + sys_code + "'");
			// System.out.println(sql.toString());
			rs = stmt.executeQuery(sql.toString());
			if (rs.next()) {
				rtnVal = rs.getString("fild1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rtnVal = "取得失敗";
		} finally {
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
			} catch (SQLException e) {
				rtnVal = "取得失敗";
			}
		}
		return rtnVal;
	}

	/**
	 * 取得當天日期
	 * 
	 * @param i
	 *            格式
	 * @return
	 */
	public static String nowDate(int i) {
		String rtn = "";
		String parten_type = "yyyyMMddHHmmsS"; // ex:20081231120012123->西元年[4]+月[2]+日[2]+時[2]+分[2]+秒[2]+毫秒[3]
		try {
			SimpleDateFormat st = new SimpleDateFormat(parten_type);
			rtn = st.format(Calendar.getInstance().getTime());
			String rtn2 = "";
			if (rtn != null && rtn.length() < 17) {
				for (int x = 0; x < (17 - rtn.length()); x++)
					rtn2 += "0";
			}
			rtn += rtn2;
			int yyyyInt = Integer.parseInt(rtn.substring(0, 4));
			int yyyInt = yyyyInt - 1911;
			String yyyStr = ""; // 民國年
			switch (String.valueOf(yyyInt).length()) {
			case 1:
				yyyStr = "00";
				break;
			case 2:
				yyyStr = "0";
				break;
			}
			yyyStr += yyyInt;
			String yyyyStr = String.valueOf(yyyyInt); // 西元年
			String mmStr = rtn.substring(4, 6); // 月
			String ddStr = rtn.substring(6, 8); // 日
			String hhStr = rtn.substring(8, 10); // 時
			String ssStr = rtn.substring(10, 12); // 分
			String sssStr = rtn.substring(12, 14); // 秒
			String ssssStr = rtn.substring(14); // 毫秒

			// 顯示日期的格式
			switch (i) {
			case 0: // yyymmdd(ex:0950101)
				rtn = yyyStr + mmStr + ddStr;
				break;
			case 1: // yyymmdd(ex:20060101)
				rtn = yyyyStr + mmStr + ddStr;
				break;
			case 2: // yy/mm/dd(ex:95/01/01)
				rtn = String.valueOf(yyyInt) + "/" + mmStr + "/" + ddStr;
				break;
			case 3: // yyyymmddhhmiss(ex:20060418131132
				break;
			case 4: // yyymmddhhmiss(ex:0950418131132)
				rtn = yyyStr + rtn.substring(4, 14);
				break;
			case 5: // yy/mm/dd(ex:95/01/01)
				rtn = String.valueOf(yyyInt) + "年" + mmStr + "月" + ddStr + "日";
				break;
			case 6: // yyyy年mm月dd日(ex:2006年01月01日)
				rtn = String.valueOf(yyyyStr) + "年" + mmStr + "月" + ddStr + "日";
				break;
			case 7: // yyy.mm.dd(ex:95.01.01)
				rtn = String.valueOf(yyyInt) + "." + mmStr + "." + ddStr;
				break;
			case 8: // yyyy.mm.dd(ex:2006.01.01)
				rtn = yyyyStr + "." + mmStr + "." + ddStr;
				break;
			case 9: // yyymmddhhmissSSS(ex:0971231120012123)
				rtn = yyyStr + rtn.substring(4);
				break;
			case 10: // yyyymmddhhmissSSS(ex:20081231120012123)
				break;
			case 11: // yyymmddhhmi(ex:09504181311)
				rtn = yyyStr + rtn.substring(4, 12);
				break;
			case 12: // yyyymmddhhmi(ex:200604181311)
				break;
			case 13:// yyyymm
				rtn = yyyyStr + mmStr;
				break;
			case 14: // yyymm(ex:09501)
				rtn = yyyStr + mmStr;
				break;
			case 15: // yyymm(ex:09501)
				rtn = yyyyStr + mmStr + "01";
				break;
			case 16: // yyyy
				rtn = yyyyStr;
				break;
			case 17: // yyymm(ex:09501)
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
				rtn = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
				break;
			}
		} catch (Exception e) {
		}

		return rtn;
	}

	/**
	 * 取得訊息 file_name:com.sysview.config.msg county:Taiwan
	 */
	public static String getBundle(String file_name, String key_name, Locale county) {
		String rtn = "";
		try {
			ResourceBundle rb = ResourceBundle.getBundle(file_name, county);
			rtn = rb.getString(key_name);
		} catch (MissingResourceException e) {
			e.printStackTrace();
			rtn = "Exception:" + e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			rtn = "Exception:" + e.getMessage();
		}
		return rtn;
	}

	/**
	 * 取得server Path ex:/C:/Tomcat/webapps/nantouWeb/
	 */
	public static String serverPath() {
		String ser_url = "";
		try {
			ser_url = FmtBean.class.getResource("/").getPath();
			int idx1 = ser_url.lastIndexOf("/WEB-INF");
			if (idx1 > -1)
				ser_url = ser_url.substring(0, idx1 + 1);
		} catch (Exception e) {
		}
		return ser_url;
	}

	/**
	 * 轉換日期<br>
	 * param->【str】：指傳入的值，格式為yyymmdd(ex:0950101)，yyymm(ex:09501)<br>
	 * yyymmddhhss(ex:09501011200),yyyymmdd(ex:20060101)
	 * 【i】：指0:表yyy/mm/dd(ex:95/01/01)、1:表yyyy/mm/dd(ex:2006/01/01)、<br>
	 * 2:yy/mm(ex:95/01)、3:yyyy/mm(ex:2006/01)、4:yy年mm月dd日(ex:95年01月01日)<br>
	 * 5:yy年mm月(ex:95年01月)、6:表yy.mm.dd(ex:95.01.01) 7:yyy/mm/dd
	 * hh:mm(ex.98/06/20 14:00)、 8:yyy/mm/dd hh:mm:ss(ex.98/06/20 14:00:01)
	 * 9:yy年mm月dd日 hh:mm:ss(ex.95年01月01日 14:00:01) 10:yy年mm月dd日
	 * hh時mm分ss秒(ex:96年01月01日 12時00分00秒)，傳入長度為11碼時，只會處理到(分)
	 */
	public static String transDate(String str, int i) {
//		System.out.println(str + "," + i);
		String rtn = "";
		try {
			if (str != null && !str.equals("")) {
				str = str.trim();
				String str1 = "";
				if (str.length() == 5) {
					// 只傳入YYYMM
					switch (i) {
					case 2: // yyy/mm
						rtn = String.valueOf(Integer.parseInt(str.substring(0, 3))) + "/" + str.substring(3);
						break;
					case 3: // yyyy/mm
						rtn = String.valueOf(Integer.parseInt(str.substring(0, 3)) + 1911) + "/" + str.substring(3);
						break;
					case 5: // yy年mm月
						rtn = String.valueOf(Integer.parseInt(str.substring(0, 3))) + "年" + str.substring(3) + "月";
						break;
					} // end switch
				} else {
					// 只傳入YYYMMDD
					switch (str.length()) {
					case 7: // 傳入YYYMMDD
						str1 = String.valueOf(Integer.parseInt(str.substring(0, 3)) + 1911);
						str1 += str.substring(3, 5) + str.substring(5, 7);
						break;
					case 8: // 傳入YYYYMMDD
						str1 = str;
						break;
					}
					if (str1.length() == 8) {
						// str1-->YYYYMMDD,ex:200060101
						switch (i) {
						case 1: // yyyy/mm/dd
							rtn = str1.substring(0, 4) + "/";
							rtn += str1.substring(4, 6) + "/";
							rtn += str1.substring(6, 8);
							break;
						case 2: // yy/mm
							rtn = String.valueOf(Integer.parseInt(str1.substring(0, 4)) - 1911) + "/";
							rtn += str1.substring(4, 6);
							break;
						case 3: // yyyy/mm
							rtn = str1.substring(0, 4) + "/";
							rtn += str1.substring(4, 6);
							break;
						case 4: // yy年mm月dd日
							rtn = String.valueOf(Integer.parseInt(str1.substring(0, 4)) - 1911) + "年";
							rtn += str1.substring(4, 6) + "月";
							rtn += str1.substring(6, 8) + "日";
							break;
						case 5: // yy年mm月
							rtn = String.valueOf(Integer.parseInt(str1.substring(0, 4)) - 1911) + "年";
							rtn += str1.substring(4, 6) + "月";
							break;
						case 6: // yy.mm.dd
							rtn = String.valueOf(Integer.parseInt(str1.substring(0, 4)) - 1911) + ".";
							rtn += str1.substring(4, 6) + ".";
							rtn += str1.substring(6, 8);
							break;
						default:
							rtn = String.valueOf(Integer.parseInt(str1.substring(0, 4)) - 1911) + "/";
							rtn += str1.substring(4, 6) + "/";
							rtn += str1.substring(6, 8);
							break;
						} // end switch
					}

					if (str.length() == 6) {
						switch (i) {
						case 1: // yyyy/mm
							rtn = str.substring(0, 4) + "/";
							rtn += str.substring(4, 6);
							break;
						}
					} else if (str.length() == 11) {
						// str->YYYMMDDHHSS,ex:09501011200
						switch (i) {
						case 7: // 表yyy/mm/dd hh:mm
							rtn = String.valueOf(Integer.parseInt(str.substring(0, 3)));
							rtn += "/" + str.substring(3, 5);
							rtn += "/" + str.substring(5, 7);
							rtn += " " + str.substring(7, 9);
							rtn += ":" + str.substring(9, 11);
							break;
						case 10:// 表yyy年mm月dd日 hh時mm分
							rtn = String.valueOf(Integer.parseInt(str.substring(0, 3))) + "年";
							rtn += str.substring(3, 5) + "月";
							rtn += str.substring(5, 7) + "日";
							rtn += " " + str.substring(7, 9) + "時";
							rtn += str.substring(9, 11) + "分";
							break;
						}
					} else if (str.length() == 12) {// str->YYYYMMDDHHSS,ex:201501011200
						switch (i) {
						case 7:
							rtn = String.valueOf(Integer.parseInt(str.substring(0, 4)));
							rtn += "/" + str.substring(4, 6);
							rtn += "/" + str.substring(6, 8);
							rtn += " " + str.substring(8, 10);
							rtn += ":" + str.substring(10, 12);
							break;
						}
					} else if (str.length() == 13) {
						// str->YYYMMDDHHSS,ex:09501011200
						switch (i) {
						case 7: // 表yyy/mm/dd hh:mm
							rtn = String.valueOf(Integer.parseInt(str.substring(0, 3)));
							rtn += "/" + str.substring(3, 5);
							rtn += "/" + str.substring(5, 7);
							rtn += " " + str.substring(7, 9);
							rtn += ":" + str.substring(9, 11);
							break;
						case 8: // 表yyy/mm/dd hh:mm:ss
							rtn = String.valueOf(Integer.parseInt(str.substring(0, 3)));
							rtn += "/" + str.substring(3, 5);
							rtn += "/" + str.substring(5, 7);
							rtn += " " + str.substring(7, 9);
							rtn += ":" + str.substring(9, 11);
							rtn += ":" + str.substring(11);
							break;
						case 9: // 表yy年mm月dd日 hh:mm:ss
							rtn = String.valueOf(Integer.parseInt(str.substring(0, 3))) + "年";
							rtn += str.substring(3, 5) + "月";
							rtn += str.substring(5, 7) + "日";
							rtn += " " + str.substring(7, 9);
							rtn += ":" + str.substring(9, 11);
							rtn += ":" + str.substring(11);
							break;
						case 10:// 表yyy年mm月dd日 hh時mm分ss秒
							rtn = String.valueOf(Integer.parseInt(str.substring(0, 3))) + "年";
							rtn += str.substring(3, 5) + "月";
							rtn += str.substring(5, 7) + "日";
							rtn += " " + str.substring(7, 9) + "時";
							rtn += str.substring(9, 11) + "分";
							rtn += str.substring(11, 13) + "秒";
							break;
						}
					} else if (str.length() == 14) {// str->YYYYMMDDHHSS,ex:201501011200
						switch (i) {
						case 7:
							rtn = String.valueOf(Integer.parseInt(str.substring(0, 4)));
							rtn += "/" + str.substring(4, 6);
							rtn += "/" + str.substring(6, 8);
							rtn += " " + str.substring(8, 10);
							rtn += ":" + str.substring(10, 12);
							rtn += ":" + str.substring(12, 14);
							break;
						}
					}
				} // end if
			} // end if str
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rtn;
	}

	public static String transTimes(String str, int i) {
		String rtn = "";
		String hhStr = "";
		String mmStr = "";
		try {
			if (str != null && !str.equals("")) {
				str = str.trim();
				if (str.length() == 4) {
					hhStr = str.substring(0, 2);
					mmStr = str.substring(2, 4);
					switch (i) {
					case 1:
						rtn = hhStr + ":" + mmStr;
						break;
					}
				}
			}
		} catch (Exception e) {
		}
		return rtn;
	}

	/**
	 * 取得代碼的中文名稱<br>
	 * param->【name】：指select colname，例如：comm_id||comm_name、<br>
	 * 【tableName】：指tablename、<br>
	 * 【whereCase】：指查詢條件，例如：where comm_kind='1' order by comm_id 回傳值：若有多筆，則用【｜】
	 */
	public static String getCodeName(String name, String tableName, String whereCase) {
		String rtn = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		try {
			if (name != null && !name.equals("") && tableName != null && !tableName.equals("")) {
				conn = DBUtils.getConnection();
				stmt = conn.createStatement();

				sql.append("select ");
				sql.append(name);
				sql.append(" as name1 from ");
				sql.append(tableName);
				if (whereCase != null && !whereCase.equals(""))
					sql.append(" " + whereCase);
				// System.out.println(sql.toString());
				rs = stmt.executeQuery(sql.toString());
				while (rs.next())
					rtn += "｜" + rs.getString("name1");
				if (rtn != null && !rtn.equals(""))
					rtn = rtn.substring(1);
				// if(rs.next()) rtn = rs.getString("name1");
			} else {
				rtn = "取得失敗";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + "｜" + sql.toString());
			rtn = "取得失敗";
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}

		return rtn;
	}

	/**
	 * 計算年資/日期區間
	 * 
	 * @param onboardDate
	 *            到部日期
	 * @param endDate
	 *            日期迄值(選填)
	 * @param type:
	 *            1:年月日YYMMDD 2:日(無條件捨去) 3:月(無條件捨去) 4:年(無條件捨去)
	 *            21:月(每月以30天計算)/小數點1位 22:年(每月以365天計算)/小數點1位
	 * @return
	 *//*
	public static String getWorkingSeniority(String onboardDate, int type) {
		return getWorkingSeniority(onboardDate, "", type);
	}

	public static String getWorkingSeniority(String StartDate, String endDate, int type) {
		String rtnVal = "";
		DateTime dt = null;
		DateTime nowDt = new DateTime();

		Period per = null;
		String sdf = "yyyyMMdd";
		if (StartDate.length() == 12)
			sdf = "yyyyMMddHHmm";
		try {
			if (!endDate.equals("")) {
				nowDt = new DateTime(new SimpleDateFormat(sdf).parse(endDate));
			}
			dt = new DateTime(new SimpleDateFormat(sdf).parse(StartDate));
			switch (type) {
			case 1: // YYMMDD

				per = new Period(dt, nowDt);
				// System.out.println(per);
				int year = per.getYears();
				int month = per.getMonths();
				int day = per.getWeeks() * 7 + per.getDays();
				// System.out.println(day);
				rtnVal = padVal(year, 2, "0") + padVal(month, 2, "0") + padVal(day, 2, "0");
				break;
			case 2:
				per = new Period(dt, nowDt, PeriodType.days());
				// System.out.println(per);
				rtnVal = String.valueOf(per.getDays());
				break;
			case 3:
				per = new Period(dt, nowDt, PeriodType.months());
				// System.out.println(per);
				rtnVal = String.valueOf(per.getMonths());
				break;
			case 4:
				per = new Period(dt, nowDt, PeriodType.years());
				// System.out.println(per);
				rtnVal = String.valueOf(per.getYears());
				break;
			case 21:
				per = new Period(dt, nowDt, PeriodType.days());
				rtnVal = String.valueOf(new BigDecimal(((double) per.getDays() / 30)).setScale(1, BigDecimal.ROUND_DOWN).doubleValue());
				break;
			case 22:
				per = new Period(dt, nowDt, PeriodType.days());
				rtnVal = String.valueOf(new BigDecimal(((double) per.getDays() / 365)).setScale(1, BigDecimal.ROUND_DOWN).doubleValue());
				break;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rtnVal;

	}*/

	/**
	 * 字元補字 math :須補字的數字/字串 path :字串總長度 val :補的字元
	 */
	public static String padVal(int math, int path, String val) {
		return padVal(String.valueOf(math), path, val);
	}

	public static String padVal(String math, int path, String val) {
		String rtnVal = "";
		if (String.valueOf(math).length() < path) {
			for (int i = 0; i < path - String.valueOf(math).length(); i++) {
				rtnVal += val;
			}
			rtnVal += math;
		} else {
			rtnVal = math;
		}
		return rtnVal;
	}

	/**
	 * 日期的加總 param-->【val】：若是指定日期，格式：yyyymmdd；若是系統日期，則放空值<br>
	 * 【type_diff】：加總的格式，y:指年份、m:指月份、d:指日<br>
	 * 【cnt_val】：加總的數字<br>
	 * 【rtn_type】：回傳日期格式，0:yyymmdd、1:yyyymmdd<br>
	 * 範例：dateDiff("0940101",'y',1,0)-->20060101或0950101<br>
	 * dateDiff("",'m',-6,0)-->20040624或0931224
	 */
	public static String dateDiff(String val, char type_diff, int cnt_val, int rtn_type) {
		String rtn = "";
		try {
			SimpleDateFormat st = new SimpleDateFormat("yyyyMMdd");
			Calendar date2 = Calendar.getInstance();
			if (val != null && val.length() == 7) {
				int y_val = Integer.parseInt(val.substring(0, 3)) + 1911;
				int m_val = Integer.parseInt(val.substring(3, 5)) - 1;
				int d_val = Integer.parseInt(val.substring(5, 7));
				date2.set(y_val, m_val, d_val);
			} else if (val != null && val.length() == 8) {
				int y_val = Integer.parseInt(val.substring(0, 4));
				int m_val = Integer.parseInt(val.substring(4, 6)) - 1;
				int d_val = Integer.parseInt(val.substring(6, 8));
				date2.set(y_val, m_val, d_val);
			} else {
				date2.getTime();
			}
			// System.out.println(st.format(date2.getTime()));
			switch (type_diff) {
			case 'y':
				date2.add(Calendar.YEAR, cnt_val);
				break;
			case 'm':
				date2.add(Calendar.MONTH, cnt_val);
				break;
			case 'd':
				date2.add(Calendar.DATE, cnt_val);
				break;
			}

			rtn = st.format(date2.getTime());
			// System.out.println("date="+rtn);
			if (rtn_type != 1) {
				String rtn_y = String.valueOf(Integer.parseInt(rtn.substring(0, 4)) - 1911);
				if (rtn_y.length() == 1)
					rtn_y = "00" + rtn_y;
				if (rtn_y.length() == 2)
					rtn_y = "0" + rtn_y;
				rtn = rtn_y + rtn.substring(4);
			}
		} catch (Exception e) {
		}

		return rtn;
	}

	/**
	 * 確認此帳號是否有該支程式的權限
	 * 
	 * @param userid
	 * @param sys_code
	 * @return
	 */
	public static boolean getAuth(String userid, String sys_code) {
		boolean flag = false;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		try {
			if (userid != null && !userid.equals("") && sys_code != null && !sys_code.equals("")) {
				conn = DBUtils.getConnection();
				stmt = conn.createStatement();
				sql.setLength(0);
				sql.append(" select a.userid,r.role,u.sys_code from sys_account a,");
				sql.append(" sys_account_role r left join sys_auth u on r.role = u.role ");
				sql.append(" where a.userid = r.userid and a.userid = '" + userid + "'");
				sql.append(" and u.sys_code ='" + sys_code + "'");
				rs = stmt.executeQuery(sql.toString());
				if (rs.next()) {
					flag = true;
				}
			} else {
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
			System.out.println(e.getMessage() + "｜" + sql.toString());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return flag;
	}

	/**
	 * 計算剩餘/增加天數
	 * 
	 * @param day
	 *            原始天數
	 * @param type
	 *            p:加 m:減
	 * @param cntDay
	 *            天數
	 * @param cntHr
	 *            小時
	 * @return 天數小時 ddHH
	 */
	public static String countDayHr(int day, char type, int cntDay, int cntHr, int rtn) {
		String rtnVal = "";
		int newday = day;
		int newhr = 0;
		double newallhr = 0;
		switch (type) {
		case 'p':
			newday += cntDay + cntHr / 8;
			newhr = cntHr % 8;
			break;
		case 'm':
			newday -= (cntDay + Math.ceil((double) cntHr / 8));
			if (cntHr > 0)
				newhr = 8 - cntHr % 8;
			break;
		}

		switch (rtn) {
		case 1:
			rtnVal = padVal(newday, 2, "0") + padVal(newhr, 2, "0");
			break;
		case 2:
			if (type == 'p') {
				newallhr = new BigDecimal((double) cntHr / 8).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				rtnVal = String.valueOf((double) newday + (double) newallhr);
			} else {
				// System.out.println(cntHr);
				newallhr = new BigDecimal((double) cntHr / 8).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				// System.out.println(newallhr);
				rtnVal = String.valueOf((double) newday - (double) newallhr);
			}
		}
		return rtnVal;
	}


	/**
	 * 輸入USERID 取得USER INFO
	 * 
	 * @param userid
	 * @param val
	 *            1:使用者姓名 2:部門(代碼) 3:部門(名稱) 4:部門主管
	 * @return
	 */
	public static String getUserInfo(String userid, int val) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		String rtnVal = null;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			sql.setLength(0);
			sql.append(" SELECT a.userid, a.user_name, b.department, c.dep_name, ");
			sql.append(" CASE WHEN a.userid = c.dep_pm ");
			sql.append(" THEN ( SELECT d.dep_pm FROM sys_department d WHERE c.dep_pdep = d.dep_code ) ELSE c.dep_pm END dep_pm ");
			sql.append(" FROM sys_account a, sys_account_d b, sys_department c ");
			sql.append(" WHERE a.userid = b.userid AND b.department = c.dep_code AND a.userid = '" + userid + "'");
			// System.out.println(sql.toString());
			rs = stmt.executeQuery(sql.toString());
			if (rs.next()) {
				switch (val) {
				case 1:
					rtnVal = rs.getString("user_name");
					break;
				case 2:
					rtnVal = rs.getString("department");
					break;
				case 3:
					rtnVal = rs.getString("dep_name");
					break;
				case 4:
					rtnVal = rs.getString("dep_pm");
					break;
				}
			} else {
				rtnVal = "";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + "｜" + sql.toString());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return rtnVal;
	}


	/**
	 * Base64加密
	 * 
	 * @param String
	 *            originString
	 * @return Base64 String
	 */
	public static String getEncodeBASE64String(String originString) {
		String rtnVal = "";
		if (originString != null) {
			rtnVal = (new sun.misc.BASE64Encoder()).encode(originString.getBytes());
		}
		return rtnVal;
	}

	/**
	 * Base64加密
	 * 
	 * @param String
	 *            encodeString
	 * @return Base64 String
	 */
	public static String getDecodeBASE64String(String encodeString) {
		String rtnVal = "";
		if (encodeString != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				byte[] b = decoder.decodeBuffer(encodeString);
				rtnVal = new String(b);
			} catch (Exception e) {
				rtnVal = "";
			}
		}
		return rtnVal;
	}

	/**
	 * 寫入數位簽核LOG
	 * 
	 * @param userid
	 *            工號
	 * @param sys_code
	 *            程式代碼
	 * @param act_seq
	 *            案號
	 * @param action
	 *            執行動作
	 * @param memo
	 *            備註
	 * @param sys_memo
	 *            系統備註
	 * @return
	 */
	public static boolean addDigiLogD(String userid, String sys_code, String act_seq, String action, String memo, String sys_memo) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer sql = new StringBuffer("");
		try {
			if (userid != null && !userid.equals("") && sys_code != null && !sys_code.equals("")) {
				conn = DBUtils.getConnection();
				conn.setAutoCommit(false);
				sql.setLength(0);
				sql.append(" insert into sys_digi_log (userid,sys_code,act_seq,action,upd_date,memo,sys_memo");
				sql.append(" ) values ( ?,?,?,?,getDate(),?,?)");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, userid);
				pstmt.setString(2, sys_code);
				pstmt.setString(3, act_seq);
				pstmt.setString(4, action);
				pstmt.setString(5, memo);
				pstmt.setString(6, sys_memo);
				if (pstmt.executeUpdate() == 1) {
					flag = true;
					conn.commit();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println(e1.getMessage() + "｜" + sql.toString());
			}
			System.out.println(e.getMessage() + "｜" + sql.toString());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 將陣列轉成字串以,分隔 參數值：【aryVal】轉換之陣列值 傳回值：以,分隔之字串
	 */
	public static String ary2Str(String[] aryVal) {
		String ary2Str = "";
		try {
			// 陣列處理
			if (aryVal != null && aryVal.length > 0) {
				for (int i = 0; i < aryVal.length; i++) {
					ary2Str += "," + aryVal[i];
				}
				if (!ary2Str.equals(""))
					ary2Str = ary2Str.substring(1);
			}
		} catch (Exception e) {
			ary2Str = "";
		}
		return ary2Str;
	}

	/**
	 * 將陣列轉成字串以自訂符號分隔 參數值：【aryVal】陣列值；【sign】分隔之符號 傳回值：型態：String；以,分隔之字串
	 */
	public static String ary2Str(String[] aryVal, char sign) {
		String ary2Str = "";
		try {
			// 陣列處理
			if (aryVal != null && aryVal.length > 0) {
				for (int i = 0; i < aryVal.length; i++) {
					ary2Str += sign + aryVal[i];
				}
				if (!ary2Str.equals(""))
					ary2Str = ary2Str.substring(1);
			}
		} catch (Exception e) {
			ary2Str = "";
		}
		return ary2Str;
	}

	/**
	 * 取得用戶EMAIL
	 * 
	 * @param userid
	 *            (多個以,分隔)
	 * @return LIST
	 */
	public static List getMailInfo(String userid) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		List rtn = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			sql.setLength(0);
			sql.append(" SELECT a.user_mail FROM sys_account a, sys_account_d d");
			sql.append("  WHERE a.userid = d.userid AND d.office_status NOT IN ('0', '3') ");
			sql.append(" AND a.user_mail LIKE '%@%' AND a.use_status = 'Y'");
			if (userid != null && !userid.equals("")) {
				sql.append(" and a.userid in ('" + userid.replaceAll(",", "','") + "')");
			}
			// System.out.println(sql.toString());
			rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				rtn.add(null2Str(rs.getString("user_mail")));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + "｜" + sql.toString());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return rtn;
	}

	/**
	 * 更新出勤報告記錄
	 * 
	 * @param userid
	 *            申請人
	 * @param office_date
	 *            申請日期
	 * @param error_code
	 * @param error_reason
	 * @param upd_user
	 * @return
	 */
	public boolean updClockRepoartStatus(String userid, String office_date_start, String office_date_end, String error_code, String error_reason, String upd_user) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		List rtn = new ArrayList<>();
		boolean flag = false;
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			sql.setLength(0);
			sql.append(" update ar1_clock_report set ");
			sql.append(" error_code = '" + error_code + "'");
			sql.append(" ,oth_error = case when error_code <> '0' and isnull(other_error,'*') not in ('','*') then error_code else other_error end ");
			sql.append(" ,error_reason = case when isnull(error_reason,'*') in ('','*') then '" + error_reason + "' else '" + error_reason + "' end");
			sql.append(" ,sys_memo = sys_memo +',UPD1'");
			sql.append(" ,upd_date = getDate()");
			sql.append(" ,upd_user = '" + upd_user + "'");
			sql.append(" where userid = '" + userid + "'");
			sql.append(" and office_date between '" + office_date_start + "' and '" + office_date_end + "'");
			// System.out.println(sql.toString());
			if (stmt.executeUpdate(sql.toString()) == 1)
				flag = true;

			if (flag) {
				sql.setLength(0);
				sql.append(" update ar1_clock_report set ");
				sql.append(" chk_status = case when error_code not in ('0','2','21') or isnull(oth_error,'*') not in ('','*','0','2') then 0 else 9 end");
				sql.append(" ,sys_memo = sys_memo +',UPD2'");
				sql.append(" ,upd_date = getDate()");
				sql.append(" ,upd_user = '" + upd_user + "'");
				sql.append(" where userid = '" + userid + "'");
				sql.append(" and office_date between '" + office_date_start + "' and '" + office_date_end + "'");
				// System.out.println(sql.toString());
				stmt.executeUpdate(sql.toString());
				flag = true;
			}
			if (flag) {
				conn.commit();
				addDigiLogD(upd_user, error_reason.substring(0, 3) + "0090", error_reason, "updchk", "人事核定更新出勤報告", sql.toString());
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			flag = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
			}
			System.out.println(e.getMessage() + "｜" + sql.toString());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return flag;
	}

	/**
	 * 檢核使用者權限
	 * 
	 * @param userid
	 * @param sys_code
	 * @return
	 */
	public static boolean chkUserAuth(String userid, String sys_code) {
		boolean flag = false;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		int cnt = 0;
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql.setLength(0);
			sql.append(" SELECT count(*) cnt FROM sys_menu m, sys_module d WHERE m.sys_module = d.sys_module ");
			sql.append(" AND m.sys_code IN ( SELECT s.sys_code FROM sys_account a ,sys_account_role r, sys_auth s WHERE a.userid = r.userid");
			sql.append(" and (r.role = s.ROLE OR s.role = a.userid ) AND a.userid = '" + userid + "' AND m.use_status = 'Y' )");
			sql.append(" and m.sys_code = '" + sys_code.toUpperCase() + "'");
			// System.out.println(sql.toString());
			rs = stmt.executeQuery(sql.toString());
			if (rs.next()) {
				cnt = rs.getInt("cnt");
			}
			if (cnt > 0)
				flag = true;
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return flag;
	}

	/**
	 * 檢核使用者權限
	 * 
	 * @param userid
	 * @param sys_code
	 * @return
	 */
	public static Map<String, String> getSysCDMap(String main_code) {
		return getSysCDMap(main_code, "");
	}

	public static Map<String, String> getSysCDMap(String main_code, String whrStr) {
		Map map = new TreeMap<String, String>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql.setLength(0);
			sql.append(" select sub_code,sub_desc from sys_cd where main_code = '" + main_code + "'");
			if (whrStr != null && !whrStr.equals("")) {
				sql.append(" " + whrStr);
			}
			// Logger.getLogger("LI").info(sql.toString());
			rs = stmt.executeQuery(sql.toString());
			map.clear();
			while (rs.next()) {
				map.put(rs.getString("sub_code"), rs.getString("sub_desc"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return map;
	}

	

	/**
	 * 取得SYS_PROP資料
	 * @param p_code
	 * @param whrcol
	 * @return
	 */
	public static String getPropText(String p_code, String whrcol) {
		String rtnVal = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql.setLength(0);
			sql.append(" select "+whrcol+" as rtnval from sys_prop where p_code = '" + p_code + "'");
			// Logger.getLogger("LI").info(sql.toString());
			rs = stmt.executeQuery(sql.toString());
			if (rs.next()) {
				rtnVal = rs.getString("rtnval");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return rtnVal;
	}
	
	
	/**
	 * 撰寫日:094/06/30<br>
	 * 功能:【數字三位一撇、四捨五入、顯示出小數位數】<br>
	 * 說明:1.參數:value(指要轉換的值,型態:String)、dot(指小數點位數)<br>
	 * 範例:numSep("12345.55",1)-->12,345.6
	 */
	public static String numSep(String value, int dot) {
		String rtnVal = "0"; // 回傳的值
		String dotVal = ""; // 小數點位數的pattern
		String decPartten = "#,##0";
		try {
			double newVal = checkDot(value, dot); // 轉換的值
			if (dot > 0) {
				for (int i = 1; i <= dot; i++) {
					if (i == 1)
						dotVal = ".0";
					else
						dotVal += "0";
				} // end for i
			}
			decPartten += dotVal;
			DecimalFormat decFmt = new DecimalFormat(decPartten);
			rtnVal = decFmt.format(newVal);
			rtnVal = NumberFormat.getInstance().format(Double.parseDouble(rtnVal.replaceAll(",", ""))); // 去除.00
		} catch (Exception e) {
		}

		return rtnVal;
	}

	/**
	 * 撰寫日:094/06/30<br>
	 * 功能:【數字三位一撇、四捨五入、顯示出小數位數】<br>
	 * 說明:1.參數:value(指要轉換的值,型態:double)、dot(指小數點位數)<br>
	 * 範例:numSep("12345.55",1)-->12,345.6
	 */
	public static String numSep(double value, int dot) {
		// Logger.getRootLogger().info(value);
		String rtnVal = "0"; // 回傳的值
		String dotVal = ""; // 小數點位數的pattern
		String decPartten = "#,##0";
		try {
			double newVal = checkDot(String.valueOf(value), dot); // 轉換的值
			if (dot > 0) {
				for (int i = 1; i <= dot; i++) {
					if (i == 1)
						dotVal = ".0";
					else
						dotVal += "0";
				} // end for i
			}
			decPartten += dotVal;
			DecimalFormat decFmt = new DecimalFormat(decPartten);
			rtnVal = decFmt.format(newVal);
			rtnVal = NumberFormat.getInstance().format(Double.parseDouble(rtnVal.replaceAll(",", ""))); // 去除.00
			/*
			 * if(dot==0 && rtnVal.lastIndexOf(".0")>-1) rtnVal =
			 * rtnVal.substring(0,rtnVal.lastIndexOf(".0"));
			 */
		} catch (Exception e) {
		}

		return rtnVal;
	}

	/**
	 * 檢查小數點數
	 */
	private static double checkDot(String val, int dot) throws Exception {
		double rtnVal = 0;
		BigDecimal bd = new BigDecimal(val);
		int scale = 0;
		if (dot >= 0)
			scale = dot;
		rtnVal = Double.parseDouble(bd.setScale(scale, BigDecimal.ROUND_HALF_UP).toString());
		return rtnVal;
	}
}
