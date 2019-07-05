package com.subcircle.system.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class DBUtils 
{

	private static String driver=null;
	private static String url=null;
	private static String username=null;
	private static String password=null;
	
	private static final ThreadLocal<java.sql.Connection> threadLocal=new ThreadLocal<>();
	
	static
	{
		ResourceBundle bundle=ResourceBundle.getBundle("DBInfo");
		driver=bundle.getString("DRIVER");
		url=bundle.getString("URL");
		username=bundle.getString("USERNAME");
		password=bundle.getString("PASSWORD");
		
		try
		{
			Class.forName(driver);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private DBUtils() {}

	private static Connection getConnection()throws Exception
	{
		Connection conn=threadLocal.get();
		if(conn==null || conn.isClosed())
		{
			conn=DriverManager.getConnection(url,username,password);
			threadLocal.set(conn);
		}
		return conn;
	}
	
	public static PreparedStatement prepareStatement(String sql)throws Exception
	{
		return DBUtils.getConnection().prepareStatement(sql);
	}
	
	public static void beginTransaction()throws Exception
	{
		DBUtils.getConnection().setAutoCommit(false);
	}
	
	public static void commit()throws Exception
	{
		DBUtils.getConnection().commit();
	}
	
	public static void rollback()
	{
		try 
		{
			DBUtils.getConnection().rollback();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void endTransaction()
	{
		try 
		{
			DBUtils.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void close()
	{
		try 
		{
			Connection conn=threadLocal.get();
			if(conn!=null && !conn.isClosed())
			{
				threadLocal.remove();
				conn.close();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs)
	{
		try
		{
			if(rs!=null)
			{
				rs.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement pstm)
	{
		try
		{
			if(pstm!=null)
			{
				pstm.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try
		{
			Connection conn=DBUtils.getConnection();
			System.out.println(conn);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
