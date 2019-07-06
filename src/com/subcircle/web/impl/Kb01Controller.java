package com.subcircle.web.impl;

import com.subcircle.services.kbimpl.Kb01Services;
import com.subcircle.web.support.AbstractController;

public abstract class Kb01Controller extends AbstractController
{
	public Kb01Controller()
	{
		this.setServices(new Kb01Services());
	}
	
}
