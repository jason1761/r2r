package com.controller.comm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.comm.FmtBean;
import com.db.DBUtils;

public class GetInComeDetail extends FormBean {

	private static final long serialVersionUID = 1L;
	public static Logger logger = Logger.getLogger("LI");
	private Connection conn = null;
	private Statement stmt = null;
	private Statement stmt2 = null;
	private ResultSet rs = null;
	private ResultSet rs2 = null;
	private boolean flag = false;
	private StringBuffer sql = new StringBuffer("");
	private Map map = null;
	private List list = new ArrayList<>();

	public String qryIncomeTotal() {
		Map detailmap = null;
		List detaillist = null;
		try {
			conn = DBUtils.getConnection();
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();

			sql.setLength(0);
			sql.append(" select substring (getdate, 1, 6) as getmonth, sum (income) income, sum (outcome) outcome ");
			sql.append(" from income_detail group by substring (getdate, 1, 6) order by 1 desc");
//			logger.info(sql.toString());
			rs = stmt.executeQuery(sql.toString());
			while(rs.next()){
				map = new TreeMap();
				map.put("getmonth", FmtBean.transDate(rs.getString("getmonth"),1));
				map.put("income", FmtBean.numSep(rs.getInt("income"),0));
				map.put("outcome", FmtBean.numSep(rs.getInt("outcome"),0));
				
				sql.setLength(0);
				sql.append(" select getdate,income,outcome,subtitle from income_detail where substring(getdate,1,6) = '"+rs.getString("getmonth")+"'");
				rs2 = stmt2.executeQuery(sql.toString());
				detaillist = new ArrayList<>();
				while(rs2.next()){
					detailmap = new TreeMap<>();
					detailmap.put("getdate", FmtBean.transDate(rs2.getString("getdate"),1));
					detailmap.put("subtitle", rs2.getString("subtitle"));
					detailmap.put("income", FmtBean.numSep(rs2.getInt("income"),0));
					detailmap.put("outcome", FmtBean.numSep(rs2.getInt("outcome"),0));
					detaillist.add(detailmap);
				}
				map.put("detail", detaillist);
				if(rs2!=null){
					rs2.close();
				}
				list.add(map);
			}
			flag = true;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}catch(Exception e ){
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}finally{
			this.clsConn();
		}
		jsonMap.put("flag", flag);
		jsonMap.put("result", list);
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
			if (rs2 != null) {
				rs2.close();
				rs2= null;
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
