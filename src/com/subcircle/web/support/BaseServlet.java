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
			//����
			String packageName="com.subcircle.web.impl.";
			//��ȡ�������Դ·��  /��Ŀ��/��Դ��.html
			String uri=request.getRequestURI();
			//��ȡ��Դ��
			String baseName=uri.substring(uri.lastIndexOf("/")+1).replace(".html", "");
			//ת����Ŀ��Controller��
			String controllerName=baseName.substring(0, 1).toUpperCase()+baseName.substring(1);
			
			//ͨ���������ʵ����Ŀ��Controller��
			ControllerInterface controller=(ControllerInterface)Class.forName(packageName+controllerName+"Servlet").newInstance();
			//��DTOֲ��controller��
			controller.setDto(this.createDto(request));
			//�ɾ����Controller����ִ�д���
			toPath=controller.execute();
			//��ȡcontrollerִ�к����õı�ǩ
			Map<String, Object> requestAttribute=controller.getAttribute();
			//����ҳ����Ҫ��ʾ�ı�ǩ
			this.setRequestAttribute(request, requestAttribute);
		}
		catch (Exception e) 
		{
			request.setAttribute("msg", "��ʾ��������ϣ�");
			e.printStackTrace();
		}
		//����NPE
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
	 * ͨ���ύ�������Զ�����DTO 
	 * @param request
	 * @return
	 */
	private Map<String, Object> createDto(HttpServletRequest request)
	{
		
		/**�ӱ��л�ȡ���б�����������Map<String,String[]> tmp�У�
		 * ����tmp��key�Ǳ��б�ǩ��name��
		 * value����ΪString[]��������һ������ֵ
		 */
		Map<String, String[]> tmp=request.getParameterMap();
		
		//����dto��ȫ��ʼ����
		int initSize=((int)(tmp.size()/0.75))+1;
		
		//������tmp�����м�ֵ�Եļ���entrySet������tmp�н����м�ֵ�Է�������
		//һ��Entry<String,String[]>����һ����ֵ�ԣ�Map�а������Entry
		Set<Entry<String, String[]>> entrySet=tmp.entrySet();
		
		//����dto,���ڴ洢�����õ�����
		Map<String, Object> dto=new HashMap<>(initSize);

		//����entrySet��������ÿһ����ֵ���е�value String[]ת���ɵ���String��String[]����dto��
		for(Entry<String,String[]> entry:entrySet)
		{
			//�ж�ÿ����ֵ���е�value String[]����Ԫ���ǵ������Ƕ��
			if(entry.getValue().length==1)
			{
				//�жϴ��ݵ�ֵ�Ƿ�Ϊ��
				if(entry.getValue()[0]!=null && !entry.getValue()[0].equals(""))
				{
					//�����ݵ�ֵ��Ϊ�ղŽ������dto��
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
	 * ����ҳ���л�ȡ�ı�ǩ
	 * @param request
	 * @param requestAttribute
	 */
	private void setRequestAttribute(HttpServletRequest request,Map<String, Object> requestAttribute)
	{
		//��ȡ�����ǩ��ļ���
		Set<Map.Entry<String, Object>> entrySet=requestAttribute.entrySet();
		//�������б�ǩ
		for(Map.Entry<String, Object> entry:entrySet)
		{
			//Ϊҳ�����ñ�ǩ
			request.setAttribute(entry.getKey(), entry.getValue());
		}
		//��ձ�ǩ�����Ѵ���ı�ǩ
		requestAttribute.clear();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}

}
