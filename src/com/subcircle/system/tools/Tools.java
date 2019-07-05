package com.subcircle.system.tools;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.subcircle.system.db.DBUtils;

public class Tools 
{

	/**
	 * ˽�л�����������ֹ���౻ʵ����
	 */
	private Tools() {}
	

	/**
	 * ��ȡ��������Ӧ�ĵ�ǰ����ֵ
	 * @param pkname  ---  ��Ҫ��ȡ����ֵ��Ӧ��������
	 * @return
	 * @throws Exception
	 */
	public static int getPk(String pkname)throws Exception
	{
		PreparedStatement pstm1=null;  //��ѯ��Ӧ�������Ƿ��������ֵ
		PreparedStatement pstm2=null;  //��������ֵ�����������ֵ
		ResultSet rs=null;
		try
		{
			int pk=0;
			String sql1="select s.pkvalue from sequence s where pkname=?";
			pstm1=DBUtils.prepareStatement(sql1);
			pstm1.setObject(1, pkname);
			rs=pstm1.executeQuery();
			if(rs.next())  //����Ѵ��ڴ�����ֵ����ֱ�ӽ�������ֵ��ȡ�������´�����ֵ+1
			{
				pk=rs.getInt(1);
				String sql2="update sequence set pkvalue=? where pkname=?";
				pstm2=DBUtils.prepareStatement(sql2);
				pstm2.setObject(1, ++pk);
				pstm2.setObject(2, pkname);
				pstm2.executeUpdate();
			}
			else  //�������ڴ�����ֵ�������һ���µ�����ֵ
			{
				String sql2="insert into sequence(pkname,pkvalue) values(?,?)";
				pstm2=DBUtils.prepareStatement(sql2);
				pstm2.setObject(1, pkname);
				pstm2.setObject(2, ++pk);
				pstm2.executeUpdate();
			}
			return pk;
		}
		finally
		{
			DBUtils.close(rs);
			DBUtils.close(pstm1);
			DBUtils.close(pstm2);
		}
	}

	/**
	 * ��dto�е��ַ�������ת���������������ַ���
	 * @param value
	 * @return
	 */
	public static String joinStringArray(Object value) 
	{
		//���ؿ�ֵ
		if(value==null || value.equals(""))
		{
			return "";
		}
		//�ж���String����String[]
		if(value instanceof String[])  //��ΪString[]�����ַ��������е�Ԫ��ͨ��","ƴ�ӳ�һ��String������
		{
			//��������Object��������ת��ΪString[]
			String[] tmp=(String[])value;
			StringBuilder res=new StringBuilder(tmp[0]);
			for(int i=1;i<tmp.length;i++)
			{
				res.append(",").append(tmp[i]);
			}
			return res.toString();
		}
		else  //��ΪString����ֱ��ת�����ַ�������
		{
			return value.toString();
		}
	}

}
