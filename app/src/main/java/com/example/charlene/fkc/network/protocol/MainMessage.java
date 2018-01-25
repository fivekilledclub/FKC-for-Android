package com.example.charlene.fkc.network.protocol;

import java.util.ArrayList;
import java.util.List;

public class MainMessage{
	
	/*
	 * �淶��һ����׼������Ϣ
	 * 	һ������ռһ��
	 * 
	 * Ŀǰֻ�ܴ����ı����ݣ����ڲ�ȫ���������ݵĴ���
	 * */
	String mainMessage = "";
	private List<String > arr = new ArrayList<>();
	
	/*
	 * @param arr:������ݵ�ArratList����
	 * */
	public MainMessage(ArrayList<String> arr){
		this.arr = arr;
		initMainMessage();
	}
	

	public void initMainMessage(){
		for(int i = 0;i<arr.size();i++){
			mainMessage += arr.get(i)+"\n";
		}
	}
}
