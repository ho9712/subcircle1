package com.subcircle.web.support;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.subcircle.services.support.ServicesInterface;

public abstract class AbstractController implements ControllerInterface 
{
	/***********************************************************
	 * 					���þ����Servicesʵ��
	 ***********************************************************/
	
	private ServicesInterface services=null;
	
	protected final void setServices(ServicesInterface services)
	{
		this.services=services;
	}
	
	/***********************************************************
	 * 					ΪServices����DTO
	 ***********************************************************/
	
	private Map<String, Object> dto=null;
	
	@Override
	public final void setDto(Map<String, Object> dto) 
	{
		this.dto=dto;
		//ΪServices����DTO
		this.services.setServicesDto(dto);
	}
	
	/***********************************************************
	 * 					Ϊҳ�洫�����ݷ���
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
	 * 			ͨ��Serviceʵ�־���ҵ�����������ҳ���ǩ
	 ***********************************************************/
	
	/**
	 * ������ѯ����������ҳ����ʾ�ı�ǩ
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
			this.saveAttribute("msg", "��ƥ������");
		}
	}
	
	/**
	 * ��һ��ѯ����������ҳ����ʾ�ı�ǩ
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
			this.saveAttribute("msg", "��ʾ���������ѱ�ɾ�����ֹ���ʣ�");
		}
	}
	
	/**
	 * ���²����������ò�����ʾ��Ϣ
	 * @param updateName
	 * @param typeMsg
	 * @throws Exception
	 */
	protected final void update(String methodName,String typeMsg)throws Exception
	{
		String msg=typeMsg+(this.executeMethod(methodName)?"�ɹ�":"ʧ��");
		this.saveAttribute("msg", msg);
	}
	
	protected final void update(String methodName,String typeMsg,String extMsg,String key)throws Exception
	{
		String msg=typeMsg+(this.executeMethod(methodName)?"�ɹ�":"ʧ��");
		msg+=extMsg+this.dto.get(key);
		this.saveAttribute("msg", msg);
	}
	
	//ͨ��������ƣ����ݷ�����ִ��Services�еķ���
	private boolean executeMethod(String methodName)throws Exception
	{
		Method method=this.services.getClass().getDeclaredMethod(methodName);
		//���÷���Ȩ�ޣ�ʹ�ܹ�����private����
		method.setAccessible(true);
		return (boolean)method.invoke(this.services);
	}
	
	/**
	 * ���²��������²�ѯ
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
	 * 						������Ҫ�ڴ�д����
	 *********************************************************************/

	
}
