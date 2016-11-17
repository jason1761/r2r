package com.controller.comm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.comm.FmtBean;
import com.comm.SendMail;
import com.db.DBUtils;

public class OrderApi extends FormBean {

	private static final long serialVersionUID = 1L;
	public static Logger logger = Logger.getRootLogger();
	private Connection conn = null;
	private Statement stmt = null;
	private Statement stmt2 = null;
	private ResultSet rs = null;
	private boolean flag = false;
	private StringBuffer sql = new StringBuffer("");
	private String errMsg = "";
	private String errCode = "";

	public String OrderAction() {
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			conn.setAutoCommit(false);

//			logger.info(this.getUserid());

			if (!this.getUserid().equals("") && !this.getEmail().equals("")) {
				flag = false;
				sql.setLength(0);
				sql.append(" select count(*) cnt from sys_user where user_id = '" + this.getUserid() + "'");
				logger.debug(sql.toString());
				rs = stmt.executeQuery(sql.toString());
				if (rs.next()) {
					if (rs.getInt("cnt") > 0) {
						flag = true;
					}
				}
				if (!flag) {
					errCode = "103";
					errMsg = "USERID 不正確，請先登入後操作";
				} else {
					this.setSeq(FmtBean.getSeqNo("order_record", "seq", "", 6, 3, 2, 2));
					this.setPrice(this.getOrder_bracelet() * 100 + this.getOrder_towel() * 150);
					flag = false;
					sql.setLength(0);
					sql.append(" insert into order_record (seq,user_id,cname,phone,order_towel,order_bracelet");
					sql.append(",price,check_yn,pay_status,cre_date,get_date,get_place,memo,con_email");
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
					sql.append(" ,'" + this.getEmail() + "'");
					sql.append(" )");
//					logger.info(sql.toString());
					if (stmt.executeUpdate(sql.toString()) == 1) {
						flag = true;
					} else {
						errCode = "901";
						errMsg = "資料新增異常";
					}
				}
				if (flag) {
					sql.setLength(0);
					sql.append(" update sys_user set cname = '" + this.getCname() + "'");
					sql.append(" ,phone='" + this.getPhone() + "'");
					sql.append(" ,email='" + this.getEmail() + "'");
					sql.append(" ,upd_date=getDate()");
					sql.append(" ,upd_user='" + this.getUserid() + "'");
					sql.append(" where user_id = '" + this.getUserid() + "'");
//					logger.info(sql.toString());
					if (stmt.executeUpdate(sql.toString()) == 1) {
						flag = true;
					} else {
						errCode = "902";
						errMsg = "資料更新異常";
					}
				}
				if (flag) {
					conn.commit();
					SendMail s = new SendMail(this.getSeq());
					s.sendNoticeThread();
					s = new SendMail(this.getUserid());
					s.sendCheckThread();
					s = null;
				}
			} else {
				flag = false;
				errCode = "101";
				errMsg = "必填資料不可空白。";
			}
		} catch (SQLException e) {
			flag = false;
			errCode = "998";
			errMsg = e.getMessage();
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			flag = false;
			errCode = "999";
			errMsg = e.getMessage();
			logger.error(e.getMessage(), e);
		} finally {
			this.clsConn();
		}
//		logger.info(flag);
		this.getJsonMap().put("flag", flag);
		if (flag) {
			this.getJsonMap().put("seq", this.getSeq());
			errCode = "000";
			errMsg = "操作成功";
		}
		this.getJsonMap().put("errCode", errCode);
		this.getJsonMap().put("errMsg", errMsg);
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
