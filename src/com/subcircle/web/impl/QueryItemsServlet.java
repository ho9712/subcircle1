package com.subcircle.web.impl;

public class QueryItemsServlet extends MarketItemsController {

	@Override
	public String execute() throws Exception 
	{
		this.queryAndShowMarketData();
		return "kb/hello";
	}
}
