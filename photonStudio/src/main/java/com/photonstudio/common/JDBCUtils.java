package com.photonstudio.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtils {
	private static String driverClass = "com.mysql.cj.jdbc.Driver";//192.168.114.2//192.168.114.2
	private static String url = "jdbc:mysql://192.168.3.19:3306/db_newg3_main?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true";
	private static String user ="root";//"jieLink";
	private static String password ="root123";//"js*168";

	static {
		System.out.println("链接地址===" + url);
		try {
			Class.forName(driverClass); // 注册加载驱动
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取连接
	 * 
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		System.out.println("链接地址===" + url);
		return DriverManager.getConnection(url, user, password);
	}

	/**
	 * 关闭连接
	 */
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭Statement资源
	 * 
	 * @param stmt
	 */

	public static void close(Statement stmt) {
		if (null != stmt) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭close资源
	 * 
	 * @param conn
	 */
	public static void close(Connection conn) {
		if (null != conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭资源
	 * 
	 * @param rs
	 * @param stmt
	 * @param conn
	 */

	public static void close(ResultSet rs, Statement stmt, Connection conn) {
		close(rs);
		close(stmt);
		close(conn);
	}
}
