package com.subcircle.web.impl;

public class Kb01FindItemByIdServlet extends Kb01Controller
{
	/**
	 *	��ת��Ʒ������Ϣҳ�沢Ϊ���ο��û����������¼
	 */
	@Override
	public String execute() throws Exception 
	{
		this.showMarketItemInfo();		//��ѯ��Ʒ��Ϣ
		this.update("insertBrowse", "�ҵ�����Ʒ�������µ������¼");	//�����û��������¼
		return "kb/itemInfo";
	}
}
