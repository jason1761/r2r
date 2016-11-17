package com.controller.comm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.comm.FmtBean;
import com.comm.SendMail;
import com.db.DBUtils;

public class OrderAction extends FormBean {

	private static final long serialVersionUID = 1L;
	public static Logger logger = Logger.getLogger("LI");
	private Connection conn = null;
	private Statement stmt = null;
	private Statement stmt2 = null;
	private ResultSet rs = null;
	private boolean flag = false;
	private StringBuffer sql = new StringBuffer("");

	public String execute() {
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			conn.setAutoCommit(false);
			this.setUserid((String) ServletActionContext.getRequest().getSession().getAttribute("user_id"));
			this.setSeq(FmtBean.getSeqNo("order_record", "seq", "", 6, 3, 2, 2));
			
			this.setPrice(this.getOrder_bracelet() * 100 + this.getOrder_towel() * 150);
			
			sql.setLength(0);
			sql.append(" insert into order_record (seq,user_id,cname,phone,order_towel,order_bracelet");
			sql.append(",price,check_yn,pay_status,cre_date,get_date,get_place,memo");
			sql.append(" ) values (");
			sql.append(" '" + seq + "'");
			sql.append(" ,'" + this.getUserid() + "'");
			sql.append(" ,'" + this.getCname() + "'");
			sql.append(" ,'" + this.getPhone() + "'");
			sql.append(" ,'" + this.getOrder_towel() + "'");
			sql.append(" ,'" + this.getOrder_bracelet() + "'");
			sql.append(" ,'" + this.getPrice() + "'");
			sql.append(" ,'N'");
			sql.append(" ,'0'");
			sql.append(" ,getDate()");
			sql.append(" ,'" + get_date + "'");
			sql.append(" ,'" + get_place + "'");
			sql.append(" ,'" + this.getMemo() + "'");
			sql.append(" )");
			logger.info(sql.toString());
			if (stmt.executeUpdate(sql.toString()) == 1) {
				flag = true;
			}
			if (flag) {
				sql.setLength(0);
				sql.append(" update sys_user set cname = '" + this.getCname() + "'");
				sql.append(" ,phone='" + this.getPhone() + "'");
				sql.append(" ,upd_date=getDate()");
				sql.append(" ,upd_user='" + this.getUserid() + "'");
				sql.append(" where user_id = '" + this.getUserid() + "'");
//				logger.info(sql.toString());
				if (stmt.executeUpdate(sql.toString()) == 1) {
					flag = true;
				}
			}
			 if(flag){
			 conn.commit();
			 SendMail s = new SendMail(this.getSeq());
			 s.sendNoticeThread();
			 s = new SendMail(this.getUserid());
			 s.sendCheckThread();
			 s = null;
			 }
		} catch (SQLException e) {
			flag = false;
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			flag = false;
			logger.error(e.getMessage(), e);
		} finally {
			this.clsConn();
		}
//		logger.info(flag);
		if (flag) {
			this.setMsgType("upd");
		} else {
			this.setMsgType("error");
		}
		return SUCCESS;
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
			if (stmt2 != null) {
				stmt2.close();
				stmt2 = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
		}
	}
}
