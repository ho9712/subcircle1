package com.subcircle.test;

public class test {

	public test() 
	{
		String className = this.getClass().getName();
		String servicesName = className.substring(0,className.lastIndexOf(".")+5)+"Services";
		System.out.println(servicesName);
		
	}
	
	public static void main(String[] args) 
	{
		new test();
	}

}
