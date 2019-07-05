package com.subcircle.web.impl;

import com.subcircle.services.kbimpl.Kb01Services;
import com.subcircle.web.support.AbstractController;

public abstract class MarketItemsController extends AbstractController
{

	public MarketItemsController() 
	{
		this.setServices(new Kb01Services());
	}
	
}
