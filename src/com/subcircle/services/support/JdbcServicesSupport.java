package com.subcircle.services.support;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.subcircle.system.db.DBUtils;

public abstract class JdbcServicesSupport implements ServicesInterface
{
	//注册SQL语句容器
	private final List<PreparedStatementMetaData> psmdList=new ArrayList<>();
	
	/*******************************************************************
	 * 							设置数据传输对象DTO方法
	 *******************************************************************/
	private Map<String, Object> dto=null;
	
	@Override
	public final void setServicesDto(Map<String, Object> dto) 
	{
		this.dto=dto;
	}
	
	/*******************************************************************
	 * 							辅助方法
	 *******************************************************************/
	
	/**
	 * 判断对象是否为空
	 * @param obj
	 * @return
	 */
	protected final boolean isNotNull(Object obj)
	{
		if(obj!=null && !obj.equals(""))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 从DTO中获取数据方法
	 * @param key
	 * @return
	 */
	protected final Object get(String key)
	{
		return this.dto.get(key);
	}
	
	/**
	 * 向DTO中放入数据方法
	 * @param key
	 * @param value
	 */
	protected final void put(String key,Object value)
	{
		this.dto.put(key, value);
	}
	
	/**
	 * 从DTO中获取idlist方法
	 * @param String key  --- id键名
	 * @return Object[] idlist
	 */
	protected final Object[] getIdlist(String key)
	{
		List<Object> res=new ArrayList<>();
		Object idlist=this.dto.get(key);
		if(idlist instanceof java.lang.String[])
		{
			for(Object id:(String[])idlist)
			{
				res.add(id);
			}
		}
		else
		{
			res.add(idlist);
		}
		return res.toArray();
	}
	
	/*******************************************************************
	 * 							查询方法
	 *******************************************************************/
	
	/**
	 * 单一实例查询
	 * @param sql  ---  执行的sql语句
	 * @param args  ---  参数列表
	 * @return
	 * @throws Exception
	 */
	protected final Map<String, String> queryForMap(String sql,Object...args)throws Exception
	{
		PreparedStatement pstm=null;
		ResultSet rs=null;
		try 
		{
			pstm=DBUtils.prepareStatement(sql);
			int index=1;
			for(Object para:args)
			{
				pstm.setObject(index++, para);
			}
			rs=pstm.executeQuery();
			Map<String, String> ins=null;
			if(rs.next())
			{
				ResultSetMetaData rsmd=rs.getMetaData();
				int count=rsmd.getColumnCount();
				int initSize=((int)(count/0.75))+1;
				ins=new HashMap<>(initSize);
				for(int i=1;i<=count;i++)
				{
					ins.put(rsmd.getColumnLabel(i), rs.getString(i));
				}
			}
			return ins;
 		}
		finally 
		{
			DBUtils.close(rs);
			DBUtils.close(pstm);
		}
	}
	
	/**
	 * 多实例多条件查询方法
	 * @param sql  ---  执行的sql语句
	 * @param args  ---  参数列表
	 * @return
	 * @throws Exception
	 */
	protected final List<Map<String, String>> queryForList(String sql,Object...args)throws Exception
	{
		PreparedStatement pstm=null;
		ResultSet rs=null;
		try 
		{
			pstm=DBUtils.prepareStatement(sql);
			int index=1;
			for(Object para:args)
			{
				pstm.setObject(index++, para);
			}
			rs=pstm.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();
			int count=rsmd.getColumnCount();
			int initSize=((int)(count/0.75))+1;
			List<Map<String, String>> rows=new ArrayList<>();
			Map<String,String> ins=null;
			while(rs.next())
			{
				ins=new HashMap<>(initSize);
				for(int i=1;i<=count;i++)
				{
					ins.put(rsmd.getColumnLabel(i), rs.getString(i));
				}
				rows.add(ins);
			}
			return rows;
		}
		finally 
		{
			DBUtils.close(rs);
			DBUtils.close(pstm);
		}
	}
	
	/*******************************************************************
	 * 							更新方法
	 *******************************************************************/
	
	/**
	 * 单表非事务更新方法
	 * @param sql  ---  执行的sql语句
	 * @param args  ---  参数列表
	 * @return
	 * @throws Exception
	 */
	protected final int executeUpdate(String sql,Object...args)throws Exception
	{
		PreparedStatement pstm=null;
		try
		{
			pstm=DBUtils.prepareStatement(sql);
			int index=1;
			for(Object para:args)
			{
				pstm.setObject(index++, para);
			}
			return pstm.executeUpdate();
		}
		finally
		{
			DBUtils.close(pstm);
		}
	}
	
	/**
	 * 非批处理SQL语句注册方法
	 * @param sql
	 * @param args
	 * @throws Exception
	 */
	protected final void appendSql(final String sql,final Object...args)throws Exception
	{
		PreparedStatement pstm=DBUtils.prepareStatement(sql);
		int index=1;
		for(Object para:args)
		{
			pstm.setObject(index++, para);
		}
		PreparedStatementMetaData psmd=new PreparedStatementMetaData(pstm, false);
		psmdList.add(psmd);		
	}
	
	/**
	 * 不更新状态批处理更新SQl语句注册方法
	 * <  delete from table where id=?  >
	 * @param sql
	 * @param idlist
	 * @throws Exception
	 */
	protected final void appendBatch(final String sql,Object...idlist) throws Exception
	{
		PreparedStatement pstm=null;
		pstm=DBUtils.prepareStatement(sql);
		for(Object id:idlist)
		{
			pstm.setObject(1, id);
			pstm.addBatch();
		}
		PreparedStatementMetaData psmd=new PreparedStatementMetaData(pstm, true);
		this.psmdList.add(psmd);
	}
	
	/**
	 * 多状态更新批处理SQL语句注册方法
	 * <  update table set col1=?,col2=?... where id=?  >
	 * @param sql
	 * @param stateList
	 * @param idlist
	 * @throws Exception
	 */
	protected final void appendBatch(final String sql,Object[] stateList,Object...idlist) throws Exception
	{
		PreparedStatement pstm=null;
		pstm=DBUtils.prepareStatement(sql);
		int index=1;
		for(Object state:stateList)
		{
			pstm.setObject(index++, state);
		}
		for(Object id:idlist)
		{
			pstm.setObject(index, id);
			pstm.addBatch();
		}
		PreparedStatementMetaData psmd=new PreparedStatementMetaData(pstm, true);
		this.psmdList.add(psmd);
	}
	
	/**
	 * 事务执行所有已注册SQL语句方法
	 * @return
	 * @throws Exception
	 */
	protected final boolean executeTransaction()throws Exception
	{
		boolean tag=false;
		DBUtils.beginTransaction();
		try
		{
			for(PreparedStatementMetaData psmd:psmdList)
			{
				psmd.executePreparedStatement();
			}
			DBUtils.commit();
			tag=true;
		}
		catch (Exception e) 
		{
			DBUtils.rollback();
			e.printStackTrace();
		}
		finally
		{
			DBUtils.endTransaction();
			for(PreparedStatementMetaData psmd:psmdList)
			{
				psmd.close();
			}
			psmdList.clear();
		}
		return tag;
	}
}
