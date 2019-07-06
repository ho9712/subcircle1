package com.subcircle.web.impl;

public class Kb01FindItemByIdServlet extends Kb01Controller
{
	/**
	 *	跳转商品具体信息页面并为非游客用户生成浏览记录
	 */
	@Override
	public String execute() throws Exception 
	{
		this.showMarketItemInfo();		//查询商品信息
		this.update("insertBrowse", "找到该商品并生成新的浏览记录");	//生成用户的浏览记录
		return "kb/itemInfo";
	}
}
