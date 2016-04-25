package com.cardiograph.ecg;

public class C2ECGUtil
{
	
	//小波处理每次读取512个数据，画图处理多少？
	//先读入蓝牙接收的数据，需要补上
	
	private static double[] s_orign = new double[512];//用于存放源数据
	private static double[] s_rec = new double[512];

	//十六进制转十进制,这个有问题
	private static double hex2dec()
	{
		//BigInteger bi = null;
		//String enter = null;
		double show = Integer.parseInt("13124");
  
		//System.out.println("请输入你要转换的十六进制数字!");
		//enter = 蓝牙接收的数据;
  
		//bi = new BigInteger(enter, 16);
		//String show = bi.toString(10);
		return show;
	}
	
	//小波处理接收512个数据
	private static void swDataSave()
	{
		for(int i = 0 ; i < 512 ; i++)
		{
			s_orign[i] = hex2dec();
		}
	}
	
	//这个是启动主函数
	public static void main(String[] args) {
		swDataSave();
		Decomposition_1.decom_1(s_orign);//第一层分解
		Decomposition_2.decom_2(Decomposition_1.d1[0]);//第二层分解
		Construction_1.cons_1(Decomposition_2.d2[0],Decomposition_2.d2[1]);//重构
		s_rec=Construction_2.cons_2(Construction_1.a2_rec,Decomposition_1.d1[1]);//重构
		for (int i = 0; i < s_rec.length; i++) {
			System.out.println("s_rec["+i+"]="+s_rec[i]);
		}
	}
	//主函数
	public  double[] main()
	{
		Decomposition_1.decom_1(s_orign);//第一层分解
		Decomposition_2.decom_2(Decomposition_1.d1[0]);//第二层分解
		Construction_1.cons_1(Decomposition_2.d2[0],Decomposition_2.d2[1]);//重构
		s_rec=Construction_2.cons_2(Construction_1.a2_rec,Decomposition_1.d1[1]);//重构
		return s_rec;//处理后的数据
	}
	
}