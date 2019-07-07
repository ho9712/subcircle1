package com.subcircle.web.impl;

import com.subcircle.services.kbimpl.Kb04Services;
import com.subcircle.web.support.AbstractController;

public abstract class Kb04Controller extends AbstractController
{
	public Kb04Controller()
	{
		this.setServices(new Kb04Services());
	}
	
}
