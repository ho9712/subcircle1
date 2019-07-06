package com.subcircle.test;

import com.subcircle.services.kbimpl.Kb01Services;

public class kb01Test 
{

	public static void main(String[] args) 
	{
		Kb01Services services = new Kb01Services();
		try
		{
			System.out.println(services.findById());
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
