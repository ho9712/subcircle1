package com.subcircle.web.impl;

import com.subcircle.services.kbimpl.Kb02Services;
import com.subcircle.web.support.AbstractController;

public abstract class Kb02Controller extends AbstractController
{
	public Kb02Controller() 
	{
		this.setServices(new Kb02Services());
	}	
}
