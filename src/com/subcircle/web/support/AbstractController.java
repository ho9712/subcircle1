package com.subcircle.web.support;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.subcircle.services.support.ServicesInterface;

public abstract class AbstractController implements ControllerInterface 
{
	/***********************************************************
	 * 					设置具体的Services实例
	 ***********************************************************/
	
	private ServicesInterface services=null;
	
	protected final void setServices(ServicesInterface services)
	{
		this.services=services;
	}
	
	/***********************************************************
	 * 					为Services传递DTO
	 ***********************************************************/
	
	private Map<String, Object> dto=null;
	
	@Override
	public final void setDto(Map<String, Object> dto) 
	{
		this.dto=dto;
		//为Services传递DTO
		this.services.setServicesDto(dto);
	}
	
	/***********************************************************
	 * 					为页面传递数据方法
	 ***********************************************************/
	
	private Map<String, Object> attribute=new HashMap<>();
	
	@Override
	public final Map<String, Object> getAttribute() 
	{
		return this.attribute;
	}
	
	private final void saveAttribute(String key,Object value)
	{
		this.attribute.put(key, value);
	}
	
	/***********************************************************
	 * 			通过Service实现具体业务操作并设置页面标签
	 ***********************************************************/
	
	/**
	 * 批量查询操作并设置页面显示的标签
	 * @throws Exception
	 */
	protected final void queryAndShow()throws Exception
	{
		List<Map<String, String>> rows=this.services.queryByCondition();
		if(rows.size()>0)
		{
			this.saveAttribute("rows", rows);
		}
		else
		{
			this.saveAttribute("msg", "无匹配结果！");
		}
	}
	
	/**
	 * 单一查询操作并设置页面显示的标签
	 * @throws Exception
	 */
	protected final void findByIdAndShow()throws Exception
	{
		Map<String, String> ins=this.services.findById();
		if(ins!=null)
		{
			this.saveAttribute("ins", ins);
		}
		else
		{
			this.saveAttribute("msg", "提示：该数据已被删除或禁止访问！");
		}
	}
	
	/**
	 * 更新操作，并设置操作提示信息
	 * @param updateName
	 * @param typeMsg
	 * @throws Exception
	 */
	protected final void update(String methodName,String typeMsg)throws Exception
	{
		String msg=typeMsg+(this.executeMethod(methodName)?"成功":"失败");
		this.saveAttribute("msg", msg);
	}
	
	protected final void update(String methodName,String typeMsg,String extMsg,String key)throws Exception
	{
		String msg=typeMsg+(this.executeMethod(methodName)?"成功":"失败");
		msg+=extMsg+this.dto.get(key);
		this.saveAttribute("msg", msg);
	}
	
	//通过反射机制，传递方法名执行Services中的方法
	private boolean executeMethod(String methodName)throws Exception
	{
		Method method=this.services.getClass().getDeclaredMethod(methodName);
		//设置访问权限，使能够访问private方法
		method.setAccessible(true);
		return (boolean)method.invoke(this.services);
	}
	
	/**
	 * 更新操作后重新查询
	 * @throws Exception
	 */
	protected final void queryForDelAndShow()throws Exception
	{
		List<Map<String, String>> rows=this.services.queryByCondition();
		if(rows.size()>0)
		{
			this.saveAttribute("rows", rows);
		}
	}

	
	/*********************************************************************
	 * 						根据需要在此写方法
	 *********************************************************************/

	
}
