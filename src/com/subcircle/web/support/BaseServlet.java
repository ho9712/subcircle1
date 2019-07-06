package com.subcircle.web.support;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subcircle.web.support.ControllerInterface;

@WebServlet("*.html")
public class BaseServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String toPath=null;
		try 
		{
			//包名
			String packageName="com.subcircle.web.impl.";
			//获取请求的资源路径  /项目名/资源名.html
			String uri=request.getRequestURI();
			//提取资源名
			String baseName=uri.substring(uri.lastIndexOf("/")+1).replace(".html", "");
			//转换成目标Controller名
			String controllerName=baseName.substring(0, 1).toUpperCase()+baseName.substring(1);
			
			//通过反射机制实例化目标Controller类
			ControllerInterface controller=(ControllerInterface)Class.forName(packageName+controllerName+"Servlet").newInstance();
			//将DTO植入controller中
			controller.setDto(this.createDto(request));
			//由具体的Controller对象执行处理
			toPath=controller.execute();
			//获取controller执行后设置的标签
			Map<String, Object> requestAttribute=controller.getAttribute();
			//设置页面中要显示的标签
			this.setRequestAttribute(request, requestAttribute);
		}
		catch (Exception e) 
		{
			request.setAttribute("msg", "提示：网络故障！");
			e.printStackTrace();
		}
		//避免NPE
		if(toPath != null && toPath.equals(""))
		{
			//System.out.println(request.getHeader("referer"));
			response.sendRedirect(request.getHeader("Referer"));
		}
		else
		{
			request.getRequestDispatcher("/"+toPath+".jsp").forward(request, response);
		}
	}

	/**
	 * 通过提交的请求自动生成DTO 
	 * @param request
	 * @return
	 */
	private Map<String, Object> createDto(HttpServletRequest request)
	{
		
		/**从表单中获取所有变量，并放入Map<String,String[]> tmp中，
		 * 其中tmp的key是表单中标签的name，
		 * value类型为String[]，可以有一个或多个值
		 */
		Map<String, String[]> tmp=request.getParameterMap();
		
		//计算dto安全初始容量
		int initSize=((int)(tmp.size()/0.75))+1;
		
		//定义存放tmp中所有键值对的集合entrySet，并从tmp中将所有键值对放入其中
		//一个Entry<String,String[]>代表一个键值对，Map中包含多个Entry
		Set<Entry<String, String[]>> entrySet=tmp.entrySet();
		
		//定义dto,用于存储传递用的数据
		Map<String, Object> dto=new HashMap<>(initSize);

		//遍历entrySet，将其中每一个键值对中的value String[]转换成单个String或String[]存入dto中
		for(Entry<String,String[]> entry:entrySet)
		{
			//判断每个键值对中的value String[]包含元素是单个还是多个
			if(entry.getValue().length==1)
			{
				//判断传递的值是否为空
				if(entry.getValue()[0]!=null && !entry.getValue()[0].equals(""))
				{
					//若传递的值不为空才将其存入dto中
					dto.put(entry.getKey(), entry.getValue()[0]);
				}
			}
			else
			{
				dto.put(entry.getKey(), entry.getValue());
			}
		}
		return dto;
	}
	
	/**
	 * 设置页面中获取的标签
	 * @param request
	 * @param requestAttribute
	 */
	private void setRequestAttribute(HttpServletRequest request,Map<String, Object> requestAttribute)
	{
		//获取存入标签组的集合
		Set<Map.Entry<String, Object>> entrySet=requestAttribute.entrySet();
		//遍历所有标签
		for(Map.Entry<String, Object> entry:entrySet)
		{
			//为页面设置标签
			request.setAttribute(entry.getKey(), entry.getValue());
		}
		//清空标签组内已存入的标签
		requestAttribute.clear();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}

}
