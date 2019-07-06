package com.subcircle.web.impl;

public class Kb03CollectItemServlet extends Kb03Controller 
{
	@Override
	public String execute() throws Exception 
	{	
		this.update("insertCollection", "添加收藏");
		return ""; //""表示返回上一页面
	}
}
