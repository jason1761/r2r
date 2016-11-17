package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
	public static final String sadbName = "proxool.sadb";
	
	/**
	 * 連結Db connection的參數，檔案在Proxool.properties
	 */
	public static Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(sadbName);
		return conn;
	}
	
}
