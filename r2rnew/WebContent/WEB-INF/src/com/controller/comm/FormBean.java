package com.controller.comm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.opensymphony.xwork2.ActionSupport;

public class FormBean extends ActionSupport{

	private static final long serialVersionUID = 1L;
	String userid;
	String fb_name;
	String email;
	String fb_pic;
	String login_tp;
	String sex;
	String link;
	String status;
	String seq;
	Map<String, Object> jsonMap = new TreeMap();
	
	String cname;
	String phone;
	int order_bracelet;
	int order_towel;
	String get_date;
	String get_place;
	int price;
	String memo;
	
	String msgType = "";
	
	List result = new ArrayList();
	
	
	public List getResult() {
		return (result);
	}
	public void setResult(List result) {
		this.result = result;
	}
	public String getMsgType() {
		return null2Str(msgType);
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMemo() {
		return null2Str(memo);
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getOrder_bracelet() {
		return (order_bracelet);
	}
	public void setOrder_bracelet(int order_bracelet) {
		this.order_bracelet = order_bracelet;
	}
	public int getOrder_towel() {
		return (order_towel);
	}
	public void setOrder_towel(int order_towel) {
		this.order_towel = order_towel;
	}
	public String getGet_date() {
		return null2Str(get_date);
	}
	public void setGet_date(String get_date) {
		this.get_date = get_date;
	}
	public String getGet_place() {
		return null2Str(get_place);
	}
	public void setGet_place(String get_place) {
		this.get_place = get_place;
	}
	public int getPrice() {
		return (price);
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getPhone() {
		return null2Str(phone);
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCname() {
		return null2Str(cname);
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getSeq() {
		return null2Str(seq);
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getStatus() {
		return null2Str(status);
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Map<String, Object> getJsonMap() {
		return (jsonMap);
	}
	public void setJsonMap(Map<String, Object> jsonMap) {
		this.jsonMap = jsonMap;
	}
	public String getUserid() {
		return null2Str(userid);
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFb_name() {
		return null2Str(fb_name);
	}
	public void setFb_name(String fb_name) {
		this.fb_name = fb_name;
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
	public String getLogin_tp() {
		return null2Str(login_tp);
	}
	public void setLogin_tp(String login_tp) {
		this.login_tp = login_tp;
	}
	public String getSex() {
		return null2Str(sex);
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getLink() {
		return null2Str(link);
	}
	public void setLink(String link) {
		this.link = link;
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
