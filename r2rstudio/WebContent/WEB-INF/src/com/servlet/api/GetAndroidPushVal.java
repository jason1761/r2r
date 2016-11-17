package com.servlet.api;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.db.DBUtils;
import com.servlet.comm.FormBean;

public class GetAndroidPushVal extends FormBean{

	private static final long serialVersionUID = 1L;
	private boolean flag = false;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String pushval = "";
	private StringBuffer sql = new StringBuffer("");
	
	
	
	public String getPushval() {
		return null2Str(pushval);
	}

	public void setPushval(String pushval) {
		this.pushval = pushval;
	}

	public String pushAndroidCode() throws SQLException{
		String errorMsg = "";
		int cnt =0;
		try{
			if(this.getPushval()!=null && !this.getPushval().equals("")){
				conn = DBUtils.getConnection();
				conn.setAutoCommit(false);
				
				this.setPushval(URLDecoder.decode(this.getPushval(),"UTF-8"));
				
				sql.setLength(0);
				sql.append(" select count(*)  cnt from pushcode_list where push_code = '" + this.getPushval() +"'");
				pstmt = conn.prepareStatement(sql.toString());
				
				rs = pstmt.executeQuery();
				if(rs.next()){
					cnt = rs.getInt("cnt");
				}
				
				if(cnt >0){
					flag = false;
					errorMsg = "資料重複";
				}else{
					sql.setLength(0);
					sql.append(" insert into pushcode_list (push_code,os_type,get_date) ");
					sql.append(" values(?,1,getDate())");
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, this.getPushval());
					//logger.info(sql.toString() +" --> " + this.getPushval() );
					if(pstmt.executeUpdate() == 1){
						flag = true;
						conn.commit();
					}else{
						flag = false;
						conn.rollback();
						errorMsg = "資料新增錯誤";
					}
				}
			}else{
				flag = false;
				errorMsg = "資料格式錯誤";
			}
		}catch(Exception e){
			flag = false;
			conn.rollback();
			errorMsg = "資料新增錯誤";
			logger.error(e.getMessage(),e);
		}finally{
			this.clsConn();
		}
		
		this.getJsonMap().put("flag", flag);
		this.getJsonMap().put("msg", errorMsg);
		return SUCCESS;
	}
	
	/**
	 * 關閉DB 注意:若有多個rs、stmt、conn，在此處須要加上
	 */
	private void clsConn() {
		try {
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
