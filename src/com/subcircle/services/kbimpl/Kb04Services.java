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
	 * 生成当前登入用户的收藏记录
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
			"1",		//用户ID之后修正
			this.get("kkb402")
		};
		return this.executeUpdate(sql.toString(), args) > 0;
	}
	
	/**
	 *	 显示用户的购物车信息
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
			"1" //用户id待替换	
		};
		
		//从kb04表中取出商品id及数量并合并相同商品(即数量相加)
		List<Map<String, String>> rows = this.queryForList(sql_1.toString(),args);
		//id_countMap中键为kkb101商品ID(利用键的唯一性)值为kkb402(数量)
		Map<String, String> id_countMap = new LinkedHashMap<>(); 
		int size = rows.size();

		//冒泡把rows中kkb101(商品ID)相同的map数量加在一起(未去重)
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
			
			//kkb101去重,保留最先加入的那个商品id(冒泡完最先加入的数量最大)
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
		
		//定义返回容器
		List<Map<String, String>> cartItems = new ArrayList<>();
		//每一个cartItem包含kkb101(ID),kkb102(商品名),kkb103(商品单价),kkb105(缩略图),kkb402(数量)
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
	 * 	根据购物车中商品id删除购物车相应的记录
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
