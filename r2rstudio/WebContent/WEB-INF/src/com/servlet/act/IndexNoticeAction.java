package com.servlet.act;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.struts2.ServletActionContext;

import com.db.DBUtils;
import com.servlet.comm.FormBean;

public class IndexNoticeAction extends FormBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean flag = false;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private StringBuffer sql = new StringBuffer("");

	private String editor1 = "";
	private String index_info = "";

	public String getEditor1() {
		return null2Str(editor1);
	}

	public void setEditor1(String editor1) {
		this.editor1 = editor1;
	}

	public String getIndex_info() {
		return null2Str(index_info);
	}

	public void setIndex_info(String index_info) {
		this.index_info = index_info;
	}

	public String execute() {
		get();
		return "success";
	}

	public String updAction() throws SQLException {
		flag = upd();
		if (flag)
			this.setMsg_ext("upd_success");
		else
			this.setMsg_ext("upd_error");
		return "upd";
	}

	protected boolean get() {
		flag = false;
		try {
			conn = DBUtils.getConnection();
			sql.setLength(0);
			sql.append(" select p_text from sys_prop where p_code = 'INDEX_INTRO'");
			//logger.info(sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				this.setEditor1(rs.getString("p_text"));
			}

			sql.setLength(0);
			sql.append(" select p_nval from sys_prop where p_code = 'INDEX_INFO'");
			//logger.info(sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				this.setIndex_info(rs.getString("p_nval"));
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

	protected boolean upd() throws SQLException {
		flag = false;
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			sql.setLength(0);
			sql.append(" select count(*) cnt from sys_prop where p_code = 'INDEX_INTRO'");
			//logger.info(sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			int cnt = 0;
			if (rs.next()) {
				cnt = rs.getInt("cnt");
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstmt != null)
				pstmt.clearBatch();
			//logger.info(cnt);
			sql.setLength(0);
			if (cnt == 0) {
				sql.append(" insert into sys_prop (p_code,p_text,cre_date,cre_user) ");
				sql.append(" values ( 'INDEX_INTRO',?,getDate(),?) ");
			} else {
				sql.setLength(0);
				sql.append(" update sys_prop set p_text=?,upd_date=getDate(),upd_user=? where p_code = 'INDEX_INTRO' ");
			}
			//logger.info(sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, this.getEditor1());
			pstmt.setString(2, (String) ServletActionContext.getRequest().getSession().getAttribute("userid"));
			if (pstmt.executeUpdate() == 1) {
				flag = true;
			} else {
				flag = false;
			}
			if (flag) {
				flag = false;
				if (pstmt != null) pstmt.clearBatch();
				cnt = 0;
				sql.setLength(0);
				sql.append(" select count(*) cnt from sys_prop where p_code = 'INDEX_INFO'");
				//logger.info(sql.toString());
				pstmt = conn.prepareStatement(sql.toString());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					cnt = rs.getInt("cnt");
				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (pstmt != null)
					pstmt.clearBatch();
				//logger.info(cnt);
				sql.setLength(0);
				if (cnt == 0) {
					sql.append(" insert into sys_prop (p_code,p_nval,cre_date,cre_user) ");
					sql.append(" values ( 'INDEX_INFO',?,getDate(),?) ");
				} else {
					sql.setLength(0);
					sql.append(" update sys_prop set p_nval=?,upd_date=getDate(),upd_user=? where p_code = 'INDEX_INFO' ");
				}
				//logger.info(sql.toString());
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, this.getIndex_info());
				pstmt.setString(2, (String) ServletActionContext.getRequest().getSession().getAttribute("userid"));

				if (pstmt.executeUpdate() == 1) {
					flag = true;
				} else {
					flag = false;
				}
			}
			if(flag){
				conn.commit();
			}else{
				conn.rollback();
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
