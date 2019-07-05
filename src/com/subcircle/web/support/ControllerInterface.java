package com.subcircle.web.support;

import java.util.Map;

public interface ControllerInterface 
{
	void setDto(Map<String, Object> dto);
	
	String execute() throws Exception;
	
	Map<String, Object> getAttribute();
}
