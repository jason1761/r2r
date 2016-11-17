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

public class NewsManageAction extends NewsManageBean{

	private static final long serialVersionUID = 1L;
	private boolean flag = false;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private StringBuffer sql = new StringBuffer("");
	
	
	public String execute(){
		return SUCCESS;
	}
	
	public String edit() {
		if (this.getStatus().equals("edit")) {
//			get();
		}
		return "input";
	}
	
	public boolean qry() {
		flag = false;
		try {
			conn = DBUtils.getConnection();
			
			sql.setLength(0);
			sql.append(" select a.* ,(select fb_name from sys_user where user_id = a.cre_user) cre_user ");
			sql.append(" from news_list a");
			//logger.info(sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
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
