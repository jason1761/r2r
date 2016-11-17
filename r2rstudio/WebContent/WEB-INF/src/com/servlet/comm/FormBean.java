package com.servlet.comm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

public class FormBean extends ActionSupport {
	private static final long serialVersionUID = 1L;
	public static Logger logger = Logger.getLogger("LI");
	String loginid;
	String userid;
	String userpwd;
	String user_name;
	String email;
	String fb_pic;
	String link;
	String login;
	String sex;
	JSONObject json;
	Map<String, Object> jsonMap = new HashMap<>();
	List result = new ArrayList<>();
	String msg_ext = "";
	String status = "";
	
	
	
	public String getStatus() {
		return null2Str(status);
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg_ext() {
		return null2Str(msg_ext);
	}

	public void setMsg_ext(String msg_ext) {
		this.msg_ext = msg_ext;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public List getResult() {
		return (result);
	}

	public Map<String, Object> getJsonMap() {
		return (jsonMap);
	}

	public void setJsonMap(Map<String, Object> jsonMap) {
		this.jsonMap = jsonMap;
	}

	public JSONObject getJson() {
		return (json);
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public String getUserpwd() {
		return null2Str(userpwd);
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getLoginid() {
		return null2Str(loginid);
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getUserid() {
		return null2Str(userid);
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUser_name() {
		return null2Str(user_name);
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getEmail() {
		return null2Str(email);
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFb_pic() {
		return null2Str(fb_pic);
	}

	public void setFb_pic(String fb_pic) {
		this.fb_pic = fb_pic;
	}

	public String getLink() {
		return null2Str(link);
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLogin() {
		return null2Str(login);
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSex() {
		return null2Str(sex);
	}

	public void setSex(String sex) {
		this.sex = sex;
	}


	/**
	 * 將null換成空值<br>
	 * 只能是字串
	 */
	public String null2Str(String str) {
		String rtnVal = "";
		if (str == null || str.equals("") || str.toLowerCase().equals("null"))
			rtnVal = "";
		else {
			rtnVal = str.trim().replaceAll("'", "").replaceAll("'", "\"");
		}
		return rtnVal;
	}
}
