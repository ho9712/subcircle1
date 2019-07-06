package com.subcircle.services.kbimpl;

import java.util.List;
import java.util.Map;

import com.subcircle.services.support.JdbcServicesSupport;

public class Kb01Services extends JdbcServicesSupport 
{
	/**
	 * 多实例查询商品表
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> queryByCondition() throws Exception
	{
		StringBuilder sql = new StringBuilder()
				.append(" select k.kkb101,k.kkb102,k.kkb103,k.kkb104,k.kkb105")
				.append("	from kb01 k")
				.append("	where k.kkb110 = 1 limit 12")
				;
		Object args[] = {};
		return this.queryForList(sql.toString(),args);
	}
	
	/**
	 * 根据商品Id找单个商品
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findById()throws Exception
	{
		StringBuilder  sql = new StringBuilder()
				.append(" select k.kkb101,k.kkb102,k.kkb103,k.kkb104,k.kkb105,")
				.append("        k.kkb106,k.kkb107,k.kkb108")
				.append("	from kb01 k")
				.append("	where k.kkb110 = 1")
				.append("	and k.kkb101 = ?")
				;
		Object args[] = {this.get("kkb101")};
		return this.queryForMap(sql.toString(), args);
	}

	
	/**
	 * 查询所有热销商品
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> queryExtral()throws Exception
	{
		StringBuilder  sql = new StringBuilder()
				.append(" select k.kkb101,k.kkb102,k.kkb103,k.kkb104,k.kkb105")
				.append("	from kb01 k")
				.append("	where k.kkb110 = 1")
				.append("	and k.kkb111 = ?")
				;
		Object args[] = {"1"};
		return this.queryForList(sql.toString(), args);
	}
	
	/**
	 * 生成当前登入用户的浏览记录
	 * @return
	 * @throws Exception
	 */
	private boolean insertBrowse() throws Exception
	{
		StringBuilder sql = new StringBuilder()
			.append("insert into kb02(kkb101,kkd101,kkb202)")
			.append("		 values (?,?,current_timestamp)")
			;
		Object args[] =
		{
			this.get("kkb101"),
			"1"		//用户ID之后修正
		};
		return this.executeUpdate(sql.toString(), args) > 0;
	}
	
}
