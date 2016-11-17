package com.servlet.comm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import com.db.DBUtils;

public class LoginServlet extends FormBean {
	private static final long serialVersionUID = 1L;
	private Connection conn = null;
	private Statement stmt = null;
	private Statement stmt2 = null;
	private ResultSet rs = null;
	private boolean flag = false;
	private StringBuffer sql = new StringBuffer("");
	
	public String login() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		json = new JSONObject();
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			if (login != null) {
				if (login.equals("")) {
					sql.setLength(0);
					sql.append(" select fb_name,admin_status from ");
					sql.append(" sys_user where user_id = '" + userid + "'");
					sql.append(" and user_pwd ='" + userpwd + "'");
					// //logger.info(sql.toString());
					rs = stmt.executeQuery(sql.toString());
					if (rs.next()) {
						session.setAttribute("fb_name", rs.getString("fb_name"));
						session.setAttribute("admin_status", rs.getString("admin_status"));
						session.setAttribute("userid", userid);
						flag = true;
					}
				} else if (login.equals("fb")) {
					sql.setLength(0);
					sql.append(" select * from ");
					sql.append(" sys_user where user_id = '" + userid + "' and fb_yn='Y' ");
					//logger.info(sql.toString());
					rs = stmt.executeQuery(sql.toString());
					if (rs.next()) {
						stmt2 = conn.createStatement();
						conn.setAutoCommit(false);
						sql.setLength(0);
						sql.append(" update sys_user set ");
						sql.append(" fb_name ='" + user_name + "'");
						sql.append(" ,email='" + email + "'");
						sql.append(" ,fb_pic='" + fb_pic + "'");
						sql.append(" ,fb_link='" + link + "'");
						sql.append(" ,upd_user='" + userid + "'");
						sql.append(" ,upd_date=getDate()");
						sql.append(" where user_id='" + userid + "'");
						//logger.info(sql.toString());
						if (stmt2.executeUpdate(sql.toString()) == 1) {
							flag = true;
							conn.commit();
						}
						if (flag) {
							flag = false;
							session.setAttribute("fb_name", rs.getString("fb_name"));
							session.setAttribute("admin_status", rs.getString("admin_status"));
							session.setAttribute("userid", userid);
							session.setAttribute("fb_pic", fb_pic);
							session.setAttribute("cname", rs.getString("cname"));
							session.setAttribute("phone", rs.getString("phone"));
							flag = true;
						}
					} else {
						stmt.clearBatch();
						sql.setLength(0);
						sql.append(" insert into sys_user (user_id,fb_name,sex,use_status,admin_status,email,fb_pic,fb_yn,fb_link,cre_date,cre_user ");
						sql.append(" )values(");
						sql.append("'" + userid + "'");
						sql.append(",'" + user_name + "'");
						sql.append(",'" + sex + "'");
						sql.append(",'Y'");
						sql.append(",'N'");
						sql.append(",'" + email + "'");
						sql.append(",'" + fb_pic + "'");
						sql.append(",'Y'");
						sql.append(",'" + link + "'");
						sql.append(",getDate()");
						sql.append(",'" + userid + "'");
						sql.append(")");
						//logger.info(sql.toString());
						if (stmt.executeUpdate(sql.toString()) == 1) {
							conn.commit();
							session.setAttribute("fb_name", user_name);
							session.setAttribute("admin_status", "N");
							session.setAttribute("userid", userid);
							session.setAttribute("fb_pic", fb_pic);
							flag = true;
						} else {
							conn.rollback();
							flag = false;
						}
					}
				} else {
					flag = false;
				}
			} else {
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
			logger.error(e.getMessage(), e);
		} finally {
			this.clsConn();
		}
		jsonMap.put("status", flag);
		json.put("status", flag);
		return SUCCESS;
	}
	
	public String logout(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.removeAttribute("userid"); // 把身分驗證旗號清掉
		session.invalidate();
		session = ServletActionContext.getRequest().getSession();
		session.setAttribute("errMsg", "logout");
		return "logout_s";
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
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
		}
	}
}
