package com.example.charlene.fkc.network.protocol;

import java.util.Date;

public class MessageHead{
	
	/*
	 * �淶��һ����׼����Ϣͷ
	 * 	1��ʱ��
	 * 	2��������ʽ
	 * */
	
	//����
	public static final String LINEBREAKS = "\n";
	//��Ϣͷ
	public String messageHead = "";
	
	/*
	 * ����˴����Ϣͷ
	 * @param type:��Ϣ��ʽ
	 * */
	public MessageHead(int type){
		messageHead += new Date().toString()+LINEBREAKS;
		messageHead += type+LINEBREAKS;
	}
	
	/*
	 * �ͻ��˴����Ϣͷ
	 * @param type:��Ϣ��ʽ
	 * */
	public MessageHead(String date,String type){
		messageHead += date+LINEBREAKS;
		messageHead += type+LINEBREAKS;
	}
}