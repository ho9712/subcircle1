package com.subcircle.web.impl;

public class Kb03CollectItemServlet extends Kb03Controller 
{
	@Override
	public String execute() throws Exception 
	{	
		this.update("insertCollection", "����ղ�");
		return ""; //""��ʾ������һҳ��
	}
}
