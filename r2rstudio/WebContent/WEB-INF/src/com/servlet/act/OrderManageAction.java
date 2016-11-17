package com.servlet.act;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.db.DBUtils;

public class OrderManageAction extends OrderManageBean {

	private static final long serialVersionUID = 1L;
	private boolean flag = false;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private StringBuffer sql = new StringBuffer("");
	private List<OrderManageBean> list = new ArrayList<OrderManageBean>();
	private OrderManageBean form = null;

	public String execute() {
		return "success";
	}

	public boolean qry() {
		flag = false;
		try {
			conn = DBUtils.getConnection();
			sql.setLength(0);
			sql.append(" select a.seq,a.user_id,a.get_date, ");
			sql.append(" ( select fb_name from sys_user where user_id = a.user_id ) fb_name,");
			sql.append(" a.phone, a.order_towel, a.order_bracelet, a.price,");
			sql.append(" ( select sub_desc from sys_cd where main_code = 'place' and sub_code = a.get_place ) get_place,");
			sql.append(" ( select sub_desc from sys_cd where main_code = 'paysts' and sub_code = a.pay_status ) pay_status");
			sql.append(" from order_record a ");
			//logger.info(sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				form = new OrderManageBean();
				form.setSeq(rs.getString("seq"));
				form.setUserid(rs.getString("user_id"));
				form.setCname(rs.getString("fb_name"));
				form.setCon_tel(rs.getString("phone"));
				form.setOrder_towel(rs.getInt("order_towel"));
				form.setOrder_bracelet(rs.getInt("order_bracelet"));
				form.setTotal_price(rs.getInt("price"));
				form.setGet_place(rs.getString("get_place"));
				form.setPay_status(rs.getString("pay_status"));
				form.setGet_date(rs.getString("get_date"));
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

	public String edit() {
		if (this.getStatus().equals("edit")) {
			get();
		}
		return "input";
	}

	public String updAction() throws SQLException {
		flag = upd();
		if (flag)
			this.setMsg_ext("upd_success");
		else
			this.setMsg_ext("upd_error");
		this.setStatus("edit");
		this.setSeq(this.getSeq());
		return "upd";
	}

	protected boolean upd() throws SQLException {
		flag = false;
		try {
			conn = DBUtils.getConnection();
			conn.setAutoCommit(false);
			sql.setLength(0);
			sql.append(" update order_record set ");
			sql.append(" check_yn = '"+this.getCheck_yn()+"'");
			sql.append(" ,get_place ='"+this.getGet_place()+"'");
			sql.append(" ,get_date ='"+this.getGet_date().replaceAll("/", "")+"'");
			sql.append(" ,pay_status ='"+this.getPay_status()+"'");
			sql.append(" ,rel_get_date='"+this.getRel_get_date().replaceAll("/", "")+"'");
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
		} catch (SQLException e) {
			flag = false;
			conn.rollback();
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
			sql.append(" select a.seq,a.user_id,a.get_date,a.phone, a.order_towel, a.order_bracelet, a.price ,convert(varchar,a.cre_date,112) order_date");
			sql.append(" ,a.check_yn,a.rel_get_date,a.pay_status,b.email,b.fb_pic,b.fb_link");
			sql.append(" ,( select fb_name from sys_user where user_id = a.user_id ) fb_name");
			sql.append(" ,a.get_place");
			sql.append(" from order_record a left join sys_user b on a.user_id = b.user_id where a.seq = '" + this.getSeq() + "'");
			//logger.info(sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				this.setSeq(rs.getString("seq"));
				this.setUserid(rs.getString("user_id"));
				this.setCname(rs.getString("fb_name"));
				this.setCon_tel(rs.getString("phone"));
				this.setOrder_towel(rs.getInt("order_towel"));
				this.setOrder_bracelet(rs.getInt("order_bracelet"));
				this.setTotal_price(rs.getInt("price"));
				this.setGet_place(rs.getString("get_place"));
				this.setPay_status(rs.getString("pay_status"));
				this.setGet_date(rs.getString("get_date"));
				this.setCon_mail(rs.getString("email"));
				this.setFb_link(rs.getString("fb_link"));
				this.setFb_pic(rs.getString("fb_pic"));
				this.setOrder_date(rs.getString("order_date"));
				this.setCheck_yn(rs.getString("check_yn"));
				this.setRel_get_date(rs.getString("rel_get_date"));
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
