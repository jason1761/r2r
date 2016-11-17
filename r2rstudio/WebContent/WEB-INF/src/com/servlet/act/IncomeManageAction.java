package com.servlet.act;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.comm.FmtBean;
import com.db.DBUtils;

public class IncomeManageAction extends IncomeManageBean{

	private boolean flag = false;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private StringBuffer sql = new StringBuffer("");
	private List<IncomeManageBean> list = new ArrayList<IncomeManageBean>();
	private IncomeManageBean form = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String execute(){
		return "success";
	}
	
	
	public String edit() {
		if (this.getStatus().equals("edit")) {
			get();
		}
		return "input";
	}
	
	public boolean qry() {
		flag = false;
		try {
			conn = DBUtils.getConnection();
			
			sql.setLength(0);
			sql.append("select sum(income) total_income,sum(outcome) total_outcome, (sum(income) - sum(outcome))  as total from income_detail");
			//logger.info(sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				this.setTotal_income(rs.getInt("total_income"));
				this.setTotal_outcome(rs.getInt("total_outcome"));
				this.setTotal(rs.getInt("total"));
			}
			
			if(rs!=null){
				rs.close();
				rs = null;
			}
			sql.setLength(0);
			sql.append(" select a.* ,(select fb_name from sys_user where user_id = a.cre_user) cre_user ");
			sql.append(" from income_detail a");
			//logger.info(sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				form = new IncomeManageBean();
				form.setSeq(rs.getString("seq"));
				form.setGetdate(rs.getString("getdate"));
				form.setSubtitle(rs.getString("subtitle"));
				form.setIncome(rs.getInt("income"));
				form.setOutcome(rs.getInt("outcome"));
				form.setSubmemo(rs.getString("submemo"));
				form.setCre_user(rs.getString("cre_user"));
				form.setMemo(rs.getString("memo"));
				list.add(form);
			}
			this.setResult(list);
			flag = true;
		} catch (SQLException e) {
			flag = false;
			logger.error(e.getMessage(), e);
		} finally {
			this.clsConn();
		}
		return flag;
	}
	
	
	protected boolean get() {
		flag = false;
		try {
			conn = DBUtils.getConnection();
			sql.setLength(0);
			sql.append(" select a.* ,(select fb_name from sys_user where user_id = a.cre_user) cre_user");
			sql.append(" from income_detail a where seq = '" + this.getSeq()+"'");
			//logger.info(sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				this.setSeq(rs.getString("seq"));
				this.setGetdate(rs.getString("getdate"));
				this.setSubtitle(rs.getString("subtitle"));
				this.setIncome(rs.getInt("income"));
				this.setOutcome(rs.getInt("outcome"));
				this.setSubmemo(rs.getString("submemo"));
				this.setCre_user(rs.getString("cre_user"));
				this.setMemo(rs.getString("memo"));
			}
			flag = true;
		} catch (SQLException e) {
			flag = false;
			logger.error(e.getMessage(), e);
		} finally {
			this.clsConn();
		}
		return flag;
	}
	
	public String updAction() throws SQLException {
		flag = upd();
		if (flag)
			this.setMsg_ext("upd_success");
		else
			this.setMsg_ext("upd_error");
		return "upd";
	}

	protected boolean upd() throws SQLException {
		flag = false;
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			
			if(this.getStatus().equals("add")){
				this.setSeq(FmtBean.getSeqNo("income_detail", "seq", "", 6, 3, 2, 3));
				sql.setLength(0);
				sql.append(" insert into  income_detail  (seq,getdate,subtitle,submemo,income,outcome,memo,cre_user,cre_date)  ");
				sql.append(" values ( ");
				sql.append(" '"+ this.getSeq()+"'");
				sql.append(" ,'"+this.getGetdate().replaceAll("/", "")+"'");
				sql.append(" ,'"+this.getSubtitle()+"'");
				sql.append(" ,'"+this.getSubmemo()+"'");
				sql.append(" ,"+this.getIncome());
				sql.append(" ,"+this.getOutcome());
				sql.append(" ,'"+this.getMemo()+"'");
				sql.append(" ,'"+(String) ServletActionContext.getRequest().getSession().getAttribute("userid")+"'");
				sql.append(" ,getDate() ");
				sql.append(" )");
				//logger.info(sql.toString());
				pstmt = conn.prepareStatement(sql.toString());
				if (pstmt.executeUpdate() == 1) {
					flag = true;
					conn.commit();
					this.setStatus("edit");
				} else {
					flag = false;
					conn.rollback();
				}
			}else if(this.getStatus().equals("edit")){
				sql.setLength(0);
				sql.append(" update income_detail set ");
				sql.append("  getdate = '"+this.getGetdate().replaceAll("/", "")+"'");
				sql.append(" ,subtitle ='"+this.getSubtitle()+"'");
				sql.append(" ,submemo='"+this.getSubmemo()+"'");
				sql.append(" ,income="+this.getIncome());
				sql.append(" ,outcome="+this.getOutcome());
				sql.append(" ,memo='"+this.getMemo()+"'");
				sql.append(" ,upd_user='"+(String) ServletActionContext.getRequest().getSession().getAttribute("userid")+"',upd_date = getDate() ");
				sql.append(" where seq = '"+ this.getSeq()+"'");
				//logger.info(sql.toString());
				pstmt = conn.prepareStatement(sql.toString());
				if (pstmt.executeUpdate() == 1) {
					flag = true;
					conn.commit();
				} else {
					flag = false;
					conn.rollback();
				}
			}
		} catch (SQLException e) {
			flag = false;
			conn.rollback();
			logger.error(e.getMessage(), e);
		} finally {
			this.clsConn();
		}
		return flag;
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
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
		}
	}
}
