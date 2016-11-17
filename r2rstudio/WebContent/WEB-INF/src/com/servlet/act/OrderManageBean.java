package com.servlet.act;

import java.util.HashMap;
import java.util.Map;

import com.servlet.comm.FormBean;

public class OrderManageBean extends FormBean{
	
	private static final long serialVersionUID = 1L;
	String seq;
	String cname;
	String con_tel;
	int order_towel;
	int order_bracelet;
	int total_price;
	String pay_status;
	String get_place;
	String get_date;
	String status;
	
	String con_mail;
	String fb_pic;
	String fb_link;
	String order_date;
	String check_yn;
	String rel_get_date;
	Map<String, String> tfList = new HashMap<>();
	
	
	
	public String getRel_get_date() {
		return null2Str(rel_get_date);
	}
	public void setRel_get_date(String rel_get_date) {
		this.rel_get_date = rel_get_date;
	}
	public String getCheck_yn() {
		return null2Str(check_yn);
	}
	public void setCheck_yn(String check_yn) {
		this.check_yn = check_yn;
	}
	public Map<String, String> getTfList() {
		tfList.put("Y", "已確認");
		tfList.put("N", "未確認");
		return (tfList);
	}
	public void setTfList(Map<String, String> tfList) {
		this.tfList = tfList;
	}
	public String getOrder_date() {
		return null2Str(order_date);
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public String getFb_pic() {
		return null2Str(fb_pic);
	}
	public void setFb_pic(String fb_pic) {
		this.fb_pic = fb_pic;
	}
	public String getFb_link() {
		return null2Str(fb_link);
	}
	public void setFb_link(String fb_link) {
		this.fb_link = fb_link;
	}
	public String getCon_mail() {
		return null2Str(con_mail);
	}
	public void setCon_mail(String con_mail) {
		this.con_mail = con_mail;
	}
	public String getStatus() {
		return null2Str(status);
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSeq() {
		return null2Str(seq);
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getCname() {
		return null2Str(cname);
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCon_tel() {
		return null2Str(con_tel);
	}
	public void setCon_tel(String con_tel) {
		this.con_tel = con_tel;
	}
	public int getOrder_towel() {
		return (order_towel);
	}
	public void setOrder_towel(int order_towel) {
		this.order_towel = order_towel;
	}
	public int getOrder_bracelet() {
		return (order_bracelet);
	}
	public void setOrder_bracelet(int order_bracelet) {
		this.order_bracelet = order_bracelet;
	}
	public int getTotal_price() {
		return (total_price);
	}
	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}
	public String getPay_status() {
		return null2Str(pay_status);
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public String getGet_place() {
		return null2Str(get_place);
	}
	public void setGet_place(String get_place) {
		this.get_place = get_place;
	}
	public String getGet_date() {
		return null2Str(get_date);
	}
	public void setGet_date(String get_date) {
		this.get_date = get_date;
	}
	
	

}
