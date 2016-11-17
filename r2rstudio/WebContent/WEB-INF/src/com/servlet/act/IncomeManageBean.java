package com.servlet.act;

import com.servlet.comm.FormBean;

public class IncomeManageBean extends FormBean{

	private static final long serialVersionUID = 1L;

	String seq;
	String getdate;
	String subtitle;
	int income;
	int outcome;
	
	String submemo;
	String cre_user;
	String memo;
	
	int total_income = 0;
	int total_outcome = 0;
	int total = 0;
	
	
	
	public String getMemo() {
		return null2Str(memo);
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getTotal_income() {
		return (total_income);
	}
	public void setTotal_income(int total_income) {
		this.total_income = total_income;
	}
	public int getTotal_outcome() {
		return (total_outcome);
	}
	public void setTotal_outcome(int total_outcome) {
		this.total_outcome = total_outcome;
	}
	public int getTotal() {
		return (total);
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getIncome() {
		return (income);
	}
	public int getOutcome() {
		return (outcome);
	}
	public void setIncome(int income) {
		this.income = income;
	}
	public void setOutcome(int outcome) {
		this.outcome = outcome;
	}
	public String getSeq() {
		return null2Str(seq);
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getGetdate() {
		return null2Str(getdate);
	}
	public void setGetdate(String getdate) {
		this.getdate = getdate;
	}
	public String getSubtitle() {
		return null2Str(subtitle);
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getSubmemo() {
		return null2Str(submemo);
	}
	public void setSubmemo(String submemo) {
		this.submemo = submemo;
	}
	public String getCre_user() {
		return null2Str(cre_user);
	}
	public void setCre_user(String cre_user) {
		this.cre_user = cre_user;
	}
	
	
	
	
}
