package com.subcircle.web.impl;

public class Kb04DelFromMyCartServlet extends Kb04Controller 
{
	@Override
	public String execute() throws Exception 
	{
		this.update("deleteFromMyCart","移出购物车");
		return "";	//返回上一页面
	}

}
