package com.cardiograph.ecg;

public class C2ECGUtil
{
	
	//С������ÿ�ζ�ȡ512�����ݣ���ͼ������٣�
	//�ȶ����������յ����ݣ���Ҫ����
	
	private static double[] s_orign = new double[512];//���ڴ��Դ����
	private static double[] s_rec = new double[512];

	//ʮ������תʮ����,���������
	private static double hex2dec()
	{
		//BigInteger bi = null;
		//String enter = null;
		double show = Integer.parseInt("13124");
  
		//System.out.println("��������Ҫת����ʮ����������!");
		//enter = �������յ�����;
  
		//bi = new BigInteger(enter, 16);
		//String show = bi.toString(10);
		return show;
	}
	
	//С���������512������
	private static void swDataSave()
	{
		for(int i = 0 ; i < 512 ; i++)
		{
			s_orign[i] = hex2dec();
		}
	}
	
	//���������������
	public static void main(String[] args) {
		swDataSave();
		Decomposition_1.decom_1(s_orign);//��һ��ֽ�
		Decomposition_2.decom_2(Decomposition_1.d1[0]);//�ڶ���ֽ�
		Construction_1.cons_1(Decomposition_2.d2[0],Decomposition_2.d2[1]);//�ع�
		s_rec=Construction_2.cons_2(Construction_1.a2_rec,Decomposition_1.d1[1]);//�ع�
		for (int i = 0; i < s_rec.length; i++) {
			System.out.println("s_rec["+i+"]="+s_rec[i]);
		}
	}
	//������
	public  double[] main()
	{
		Decomposition_1.decom_1(s_orign);//��һ��ֽ�
		Decomposition_2.decom_2(Decomposition_1.d1[0]);//�ڶ���ֽ�
		Construction_1.cons_1(Decomposition_2.d2[0],Decomposition_2.d2[1]);//�ع�
		s_rec=Construction_2.cons_2(Construction_1.a2_rec,Decomposition_1.d1[1]);//�ع�
		return s_rec;//����������
	}
	
}