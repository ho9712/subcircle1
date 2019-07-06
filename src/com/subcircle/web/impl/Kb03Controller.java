package com.subcircle.web.impl;

import com.subcircle.services.kbimpl.Kb03Services;
import com.subcircle.web.support.AbstractController;

public abstract class Kb03Controller extends AbstractController
{
	public Kb03Controller() 
	{
		this.setServices(new Kb03Services());
	}	
}
