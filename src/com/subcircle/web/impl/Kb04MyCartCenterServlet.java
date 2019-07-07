package com.subcircle.web.impl;

public class Kb04MyCartCenterServlet extends Kb04Controller
{
	@Override
	public String execute() throws Exception
	{
		this.queryAndShowMyCartData();
		return "kb/myCartCenter";
	}

}
