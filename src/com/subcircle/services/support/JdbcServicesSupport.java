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
	//ע��SQL�������
	private final List<PreparedStatementMetaData> psmdList=new ArrayList<>();
	
	/*******************************************************************
	 * 							�������ݴ������DTO����
	 *******************************************************************/
	private Map<String, Object> dto=null;
	
	@Override
	public final void setServicesDto(Map<String, Object> dto) 
	{
		this.dto=dto;
	}
	
	/*******************************************************************
	 * 							��������
	 *******************************************************************/
	
	/**
	 * �ж϶����Ƿ�Ϊ��
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
	 * ��DTO�л�ȡ���ݷ���
	 * @param key
	 * @return
	 */
	protected final Object get(String key)
	{
		return this.dto.get(key);
	}
	
	/**
	 * ��DTO�з������ݷ���
	 * @param key
	 * @param value
	 */
	protected final void put(String key,Object value)
	{
		this.dto.put(key, value);
	}
	
	/**
	 * ��DTO�л�ȡidlist����
	 * @param String key  --- id����
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
	 * 							��ѯ����
	 *******************************************************************/
	
	/**
	 * ��һʵ����ѯ
	 * @param sql  ---  ִ�е�sql���
	 * @param args  ---  �����б�
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
	 * ��ʵ����������ѯ����
	 * @param sql  ---  ִ�е�sql���
	 * @param args  ---  �����б�
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
	 * 							���·���
	 *******************************************************************/
	
	/**
	 * �����������·���
	 * @param sql  ---  ִ�е�sql���
	 * @param args  ---  �����б�
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
	 * ��������SQL���ע�᷽��
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
	 * ������״̬���������SQl���ע�᷽��
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
	 * ��״̬����������SQL���ע�᷽��
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
	 * ����ִ��������ע��SQL��䷽��
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
