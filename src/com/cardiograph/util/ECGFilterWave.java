package com.cardiograph.util;

import java.util.ArrayList;
import java.util.List;

public class ECGFilterWave{
/*	%�����˲�
	%ʹ��ע�����ͨ��������Ľ�ֹƵ��������ʵ�ѡȡ��Χ�ǲ��ܳ��������ʵ�һ��
	%����f1,f3,fs1,fsh,��ֵС�� Fs/2
	%x:��Ҫ��ͨ�˲�������
	% f 1��ͨ����߽�
	% f 3��ͨ���ұ߽�
	% fs1��˥����ֹ��߽�
	% fsh��˥���ֹ�ұ߽�
	%rp���ߴ���˥��DB������
	%rs����ֹ��˥��DB������
	%FS������x�Ĳ���Ƶ��
	% f1=300;f3=500;%ͨ����ֹƵ��������
	% fsl=200;fsh=600;%�����ֹƵ��������
	% rp=0.1;rs=30;%ͨ����˥��DBֵ�������˥��DBֵ
	% Fs=2000;%������*/

	private int f1=45;  //% f 1��ͨ����߽�       ��
	private int f3=55;  //% f 3��ͨ���ұ߽�          
	private int fsl=48; //% fs1��˥����ֹ��߽�        
	private int fsh=52; //% fsh��˥���ֹ�ұ߽�        
	private float rp=0.1f;             //%rp���ߴ���˥��DB������       
	private int rs=1;   //%rs����ֹ��˥��DB������
	
//	public int trapper(int data, int fs){
//		float wp1=2.0*Math.PI*f1/fs;
//		float wp3=2.0*Math.PI*f3/fs;
//		float wsl=2.0*Math.PI*fsl/fs;
//		float wsh=2.0*Math.PI*fsh/fs;
////		wp=[wp1 wp3];
////		ws=[wsl wsh];
//		
//		//% ����б�ѩ���˲�����
//		[n,wn]=cheb1ord(ws/pi,wp/pi,rp,rs);
//		[bz1,az1]=cheby1(n*wn/wn,rp,wp/pi,'stop');
//		y=filter(bz1,az1,x);
//	}
	
	//һ��band stop �˲������˵�50Hz�Ĺ�Ƶ
	public int trapper(int data, int fs){
		//���亯����
//		double s = (data*data+0.987*Math.E+4)/(data*data+6.283*data+0.987*Math.E+4);
		/*ת��Ϊʱ������ѧ���ʽ�����У�dirac������ѧ�����ϵ�delta������
		�˺�����x������0�ĵط���Ϊ0���ڵ���0ʱΪ�����*/
		int dirac = data!=0 ? 0 : 17000000;
		double y = dirac - 6283.0/1000*Math.exp(-6283.0/2000*data)*
				Math.cos(1.0/2000*Math.pow(394760523911.0, 0.5)*data)+
				39476089.0/394760523911.0/1000*Math.pow(394760523911.0, 0.5)*
				Math.exp(-6283.0/2000*data)*Math.sin(1.0/2000*Math.pow(394760523911.0, 0.5));
		return (int) y;
	}
	
	/**
	 *  �������� : Filter
	 *  �������� : ���������յ���ECG���ݽ��й���
	 *  ����������ֵ˵����
	 *  	@param DataList
	 *  	@return
	 *
	 *  �޸ļ�¼��
	 *  	���� ��2015-2-5 ����4:47:02	�޸��ˣ�yjx
	 *  	����	��
	 *
	 */
	public static List<Integer> Filter(List<Integer> DataList) {
		int total_number = DataList.size();
		int temp_Data_One[] = new int[total_number];
		int temp_Data_Two[] = new int[total_number];
		List<Integer> reslut_Data = new ArrayList<Integer>();
		for (int index = 0; index < total_number; index++) {
			temp_Data_One[index] = DataList.get(index);
			temp_Data_Two[index] = 0;
		}
		int sample_Index = 10;
		for (int index = 0; index <= sample_Index; index++) {
			temp_Data_Two[index] = temp_Data_One[index];
			reslut_Data.add(temp_Data_Two[index]);
		}
		for (int index_i = sample_Index + 1; index_i < total_number; index_i++) {
			for (int index_j = 0; index_j <= sample_Index + 1; index_j++) {
				temp_Data_Two[index_i] += temp_Data_One[index_i - index_j];
			}
			int data = (int) (temp_Data_Two[index_i]*1.0/ (sample_Index + 1));
			reslut_Data.add(data);
		}
		return reslut_Data;
	}
}