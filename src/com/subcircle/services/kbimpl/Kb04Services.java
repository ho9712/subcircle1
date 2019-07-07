package com.subcircle.services.kbimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import com.subcircle.services.support.JdbcServicesSupport;
import com.subcircle.system.db.DBUtils;

public class Kb04Services extends JdbcServicesSupport 
{	
	/**
	 * ���ɵ�ǰ�����û����ղؼ�¼
	 * @return
	 * @throws Exception
	 */
	private boolean addToMyCart() throws Exception
	{
		StringBuilder sql = new StringBuilder()
			.append("insert into kb04(kkb101,kkd101,kkb402,kkb403)")
			.append("		 values (?,?,?,current_timestamp)")
			;
		Object args[] =
		{
			this.get("kkb101"),
			"1",		//�û�ID֮������
			this.get("kkb402")
		};
		return this.executeUpdate(sql.toString(), args) > 0;
	}
	
	/**
	 *	 ��ʾ�û��Ĺ��ﳵ��Ϣ
	 *	
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> queryByCondition() throws Exception
	{
		StringBuilder sql_1 = new StringBuilder()
				.append(" select k.kkb101,k.kkb402")
				.append("	from kb04 k")
				.append("  where k.kkd101 = ?")
				;
		Object args[] = 
		{
			"1" //�û�id���滻	
		};
		
		//��kb04����ȡ����Ʒid���������ϲ���ͬ��Ʒ(���������)
		List<Map<String, String>> rows = this.queryForList(sql_1.toString(),args);
		//id_countMap�м�Ϊkkb101��ƷID(���ü���Ψһ��)ֵΪkkb402(����)
		Map<String, String> id_countMap = new LinkedHashMap<>(); 
		int size = rows.size();

		//ð�ݰ�rows��kkb101(��ƷID)��ͬ��map��������һ��(δȥ��)
		for(int i = 0;i < size;i++) 
		{
			int count = Integer.parseInt(rows.get(i).get("kkb402"));
			for(int j = i + 1;j < size;j++) 
			{
				if (rows.get(i).get("kkb101").equals(rows.get(j).get("kkb101")))
				{
					count += Integer.parseInt(rows.get(j).get("kkb402"));
				}
			}
			
			//kkb101ȥ��,�������ȼ�����Ǹ���Ʒid(ð�������ȼ�����������)
			if (!id_countMap.containsKey(rows.get(i).get("kkb101"))) 
			{
				id_countMap.put(rows.get(i).get("kkb101"), String.valueOf(count));
			}
		}


//		System.out.println(rows);
//		System.out.println(id_countMap);
		
		StringBuilder sql_2 = new StringBuilder()
				.append(" select k.kkb101,k.kkb102,k.kkb103,k.kkb105")
				.append("	from kb01 k")
				.append("  where k.kkb110 = 1")
				.append("  	 and k.kkb101 = ?")
				;
		
		//���巵������
		List<Map<String, String>> cartItems = new ArrayList<>();
		//ÿһ��cartItem����kkb101(ID),kkb102(��Ʒ��),kkb103(��Ʒ����),kkb105(����ͼ),kkb402(����)
		int initSize = (int)(5/0.75)+1;
		
		for (Entry<String, String> entry : id_countMap.entrySet())
		{
			Map<String,String> cartItem = new HashMap<>(initSize);
			cartItem = this.queryForMap(sql_2.toString(),entry.getKey());
			cartItem.put("kkb402", entry.getValue());
			cartItems.add(cartItem); 
		  }
	
		return cartItems;
	}
	
	/**
	 * 	���ݹ��ﳵ����Ʒidɾ�����ﳵ��Ӧ�ļ�¼
	 * @return
	 * @throws Exception
	 */
	private boolean deleteFromMyCart() throws Exception
	{
		String sql = "delete from kb04 where kkb101 = ?";
		Object args[] = 
			{
				this.get("kkb101")	
			};
		return this.executeUpdate(sql, args) > 0;
	}
}
