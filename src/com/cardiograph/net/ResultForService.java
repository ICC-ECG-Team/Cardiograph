package com.cardiograph.net;
/**
 * ҵ�����񷵻صĶ���
 * ����:
 * 	 {
 * 		//����״̬
 * 		retStatus:{ retCode:0,   //����״̬��
 * 					errMsg},	 //����״̬������
 * 		//��������
 * 		retData: Object  //�����ҵ������,����޷���ֵ��Ϊnull 
 *   }
 * @author bob
 *
 */
public class ResultForService {
	public ResultForService(){		
	}
	public String retCode;	//״̬��
	public String errMsg;	//״̬������
	public Object retData;	//��������,��Ҫ���ݷ���ֵ������ JsonObject ,JsonArray �����н�һ���Ĵ���
}
