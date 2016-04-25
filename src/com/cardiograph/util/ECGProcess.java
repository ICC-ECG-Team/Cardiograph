package com.cardiograph.util;

import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileReader;  
import java.io.IOException;
import java.io.DataInputStream;
import java.lang.Math;


public class ECGProcess
{
	static int noSig;			//�ź�ͨ����Ŀ
	static int sFreq;			//���ݲ���Ƶ��
	static int SAMPLES2READ;	//ָ����Ҫ�����������; ��.dat�ļ��д洢������ͨ�����ź�, ����� 2*SAMPLES2READ ������ 
	static int refTime;			//atr�ļ��У�����28�����Ĳο�ʱ�䣻
	static int[] dformat; 		//�źŸ�ʽ; ����ֻ����Ϊ 212 ��ʽ
	static int[] gain;			//ÿ mV ��������������
	static int[] bitres;		//�������ȣ�λ�ֱ��ʣ�
	static int[] zerovalue; 	//ECG �ź������Ӧ������ֵ
	static int[] firstvalue; 	//�źŵĵ�һ������ֵ (����ƫ�����)
	static double[] M;			//��dat�ļ��������ź�
	static double[] ATRTIME;
	static double[] Y;			//�Ľ�С���任��ó����ź�
	static double[] Yabs;		//�Ľ�С���任��ó��ľ���ֵ�ź�
	static double[] Z;			//ʮ��С���任��ó����ź�
	
	static int[] rPosition;		//R����λ��
	static int[] rDirection;	//R���ķ���
	static double[] rValue;		//R����ֵ
	static int nRPosition;		//R��������
	static double[] rrInterval;
	static double[] rrIntervalMean; 
	
	static int[] qPosition;		//Q����λ��
	static double[] qValue;		//Q����ֵ
	static int nQPosition;		//Q��������
	static int[] sPosition;		//S����λ��
	static double[] sValue;		//S����ֵ
	static int nSPosition;		//S��������
	
	static int[] pPosition;		//P����λ��
	static double[] pValue;		//P����ֵ
	static int nPPosition;		//P��������
	static int[] tPosition;		//T����λ��
	static double[] tValue;		//T����ֵ
	static int nTPosition;		//T��������
	
	static double[] baselineValue;
	static int[] baselineStartPosition;
	static int[] baselineEndPosition;
	static int lenBaseline;
	
	static int[] qrsOnPosition;
	static int[] qrsOffPosition;
	static int[] qrsDuration;
	static int lenQRS; 
	
	public static void initial(int int1, int int2){
		noSig = int1;
		sFreq = int2;
		dformat = new int[int1];
		gain = new int[int1];
		bitres = new int[int1];
		zerovalue = new int[int1];
		firstvalue = new int[int1];
		M = new double[SAMPLES2READ*2]; 
		ATRTIME = new double[SAMPLES2READ];
		Y = new double[SAMPLES2READ];
		Yabs = new double[SAMPLES2READ];
		Z = new double[SAMPLES2READ];
		rPosition = new int[3*SAMPLES2READ/sFreq];
		rDirection = new int[3*SAMPLES2READ/sFreq];
		rValue = new double[3*SAMPLES2READ/sFreq];
		qPosition = new int[3*SAMPLES2READ/sFreq];
		qValue = new double[3*SAMPLES2READ/sFreq];
		sPosition = new int[3*SAMPLES2READ/sFreq];
		sValue = new double[3*SAMPLES2READ/sFreq];
		pPosition = new int[3*SAMPLES2READ/sFreq];
		pValue = new double[3*SAMPLES2READ/sFreq];
		tPosition = new int[3*SAMPLES2READ/sFreq];
		tValue = new double[3*SAMPLES2READ/sFreq];
		baselineValue = new double[3*SAMPLES2READ/sFreq];
		baselineStartPosition = new int[3*SAMPLES2READ/sFreq];
		baselineEndPosition = new int[3*SAMPLES2READ/sFreq];
		qrsOnPosition = new int[3*SAMPLES2READ/sFreq];
		qrsOffPosition = new int[3*SAMPLES2READ/sFreq];
		qrsDuration = new int[3*SAMPLES2READ/sFreq];
	}
	
//	%% ------ LOAD HEADER DATA ---------------------------------------
//	%
	public static void readHeadFile(String fileName)
	{  
		File file = new File(fileName);  
		BufferedReader reader = null;  
		try {  
			System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");  
			reader = new BufferedReader(new FileReader(file));  
			String tempString = null;  
			int line = 1; //һ�ζ���һ�У�ֱ������nullΪ�ļ�����  
			while ((tempString = reader.readLine()) != null){  //��ʾ�к� 
				//System.out.println("line " + line + ": " + tempString); 
				if(line == 1){
					String[] tokens = tempString.split(" ");
					initial(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
					//System.out.println(noSig  + "," + sFreq);
				}
				else if((line - noSig)< 2){
					String[] tokens = tempString.split(" ");
					dformat[line-2] = Integer.parseInt(tokens[1]);
					gain[line-2] = Integer.parseInt(tokens[2]);
					bitres[line-2] = Integer.parseInt(tokens[3]);
					zerovalue[line-2] = Integer.parseInt(tokens[4]);
					firstvalue[line-2]= Integer.parseInt(tokens[5]);
					//System.out.println(dformat[line-2] + "," + gain[line-2] + "," + bitres[line-2]+ "," + zerovalue[line-2]+ "," + firstvalue[line-2]);
				}
				line++;  
			}  
			reader.close();  
		} 
		catch (IOException e) {  
			e.printStackTrace();  
		} 
		finally {  
			if (reader != null){  
				try {  
					reader.close();  
				} 
				catch (IOException e1) {  
				}  
			}  
		}  
	}
	
//	%% ------ LOAD BINARY DATA ---------------------------------------
//	%
//	% ���ս��������M�����У�ֱ�ӵ���M����ʹ��
//	%
	public static void readDataFile(String fileName)
	{
		int M1H,M2H,PRL,PRR;
		int Mi[] = new int[SAMPLES2READ*3];  //��.dat�ļ��д洢������ͨ�����ź�, ����� 2*SAMPLES2READ ������
		double TIME[] = new double[SAMPLES2READ];
		int intTmp1, intTmp2, intTmp3;
		if(dformat[1] != 212){
			System.out.println("this script does not apply binary formats different to 212."); 
		}
		File file = new File(fileName);  
		DataInputStream reader = null;  
		int line = 0; //һ�ζ���һ�У�ֱ������nullΪ�ļ�����  
		try {  
			System.out.println("��ʼ��data�ļ���uint8��ʽ");  
			reader = new DataInputStream(new FileInputStream(file));  
			while (line < 3*SAMPLES2READ){
				intTmp1 = reader.readUnsignedShort();
				intTmp2 = reader.readUnsignedShort();
				intTmp3 = reader.readUnsignedShort();
				Mi[line]   = intTmp1/256;
				Mi[line+1] = intTmp1%256;
				Mi[line+2] = intTmp2/256;
				Mi[line+3] = intTmp2%256;
				Mi[line+4] = intTmp3/256;
				Mi[line+5] = intTmp3%256;
				//System.out.println(intTmp1 + ", " + intTmp2 + ", " + intTmp3);
				//for(int j=0;j<6;j++){	
				//	System.out.println((line+j) + ", " + M[line+j]);
				//}
				line+=6;
			} 
			reader.close();
		} 
		catch (IOException e) {  
			e.printStackTrace();  
		}
		finally {  
			if (reader != null){  
				try {  
					reader.close();  
				} 
				catch (IOException e1) {  
				}  
			}  
		}
		for(int j=0;j<SAMPLES2READ;j++){
			M2H = Mi[3*j+1]/16;      	//�ֽ���������λ����ȡ�ֽڵĸ���λ
			M1H = Mi[3*j+1]%16;       	//ȡ�ֽڵĵ���λ
			PRL = (Mi[3*j+1]&8)*512;    //sign-bit   ȡ���ֽڵ���λ�����λ�������ƾ�λ
			PRR = (Mi[3*j+1]&128)*32;  	//sign-bit   ȡ���ֽڸ���λ�����λ����������λ
			Mi[2*j] = (M1H*256)+ Mi[3*j] -PRL;
			Mi[2*j+1] = (M2H*256)+ Mi[3*j+2] -PRR;
			if((Mi[0]!=firstvalue[0])||(Mi[1]!=firstvalue[1])){
				System.out.println("Error: inconsistency in the first bit values"); 
			}
			//System.out.println(j + ", " + M[3*j+1]  + ", " + M2H + ", " + firstvalue[1]  + ", " + M1H);
			//System.out.println(j + ", " + M[3*j+1]  + ", " +M[2*j] + ", " +M[2*j+1]);			
		}
		for(int j=0;j<SAMPLES2READ;j++){
			switch(noSig){
				case 2: 
					M[2*j] = (double)(Mi[2*j]- zerovalue[0])/gain[0];
					M[2*j+1]= (double)(Mi[2*j+1]- zerovalue[1])/gain[1];
					TIME[j] = j/sFreq;
					//System.out.println(j + ", " +M[2*j] + ", " +M[2*j+1] + ", " +TIME[j]);	
					break;
			    case 1:
			        M[2*j] = (double)Mi[2*j]- zerovalue[0];
			        M[2*j+1]= (double)Mi[2*j+1]- zerovalue[0];
			        M[2*j] = (double)Mi[2*j]/gain[0];
			        M[2*j+1] = (double)Mi[2*j+1]/gain[0];
			        TIME[j] = j/sFreq;
			        break;
			    default:  // this case did not appear up to now!
			              // here M has to be sorted!!!
			        System.out.println("Sorting algorithm for more than 2 signals not programmed yet!");
			        break;
			}
			//System.out.println(j + ", " +M[2*j] + ", " +M[2*j+1] + ", " +TIME[j]);	
		}
	}
	
//	%% ------ LOAD ATTRIBUTES DATA -----------------------------------
//	%
//	% ���յ�ע�ͱ�����ANNOTD�У�ʱ����Ϣ������ATRTIMED�У�ֱ�ӵ��ü���ʹ�ã�
//	% ����ע�͵����ֶ�Ӧ����Ϣ������ecgcodes.h�ļ��У��鿴���ɡ�
//	%
	public static void readAtrFile(String fileName)
	{
		int MM[] = new int[SAMPLES2READ];
		File file = new File(fileName);  
		DataInputStream reader = null; 
		int annoth, hilfe, temp, line = 0; //һ�ζ���һ�У�ֱ������nullΪ�ļ����� 
		int iAtr = 0;//ATRTIMEר��
		try {  
			System.out.println("��ʼ��atr�ļ���uint16��ʽ");  
			reader = new DataInputStream(new FileInputStream(file));  
			while (line < SAMPLES2READ){
				MM[line] = reader.readUnsignedShort();
				annoth = (MM[line]%256)/4;//����λ
				if(annoth == 28){//���ɱ任
				    refTime = (MM[line]%4)*256 + MM[line]/256;
				}
				else if(annoth == 59){//
					//ANNOT = [ANNOT;bitshift(A(i+3,2),-2)];
				    //ATRTIME = [ATRTIME;A(i+2,1)+bitshift(A(i+2,2),8)+ bitshift(A(i+1,1),16)+bitshift(A(i+1,2),24)];
					//System.out.println(line + ", " + M[line] + ", " + annoth);
					iAtr++;
				}
				else if(annoth == 60){}//nothing to do!
				else if(annoth == 61){}// nothing to do!
				else if(annoth == 62){}// nothing to do!
				else if(annoth == 63){//������Ϣ��������Ϣ����
					hilfe = (MM[line]%4)*256 + MM[line]/256;//��ʮλ���ַ���;
				    hilfe = hilfe/2 + hilfe%2;
				    //System.out.println(line + ", " + M[line] + ", " + annoth + ",   hilfe  = " + hilfe);
				    while(hilfe>0){
				    	temp = reader.readUnsignedShort();
				    	//System.out.println(temp);
				    	hilfe--;
				    }
				}
				else{//ʱ����Ϣ
				    ATRTIME[iAtr] = (double)((MM[line]%4)*256 + MM[line]/256 + refTime)/sFreq;//��ʮλ��ʱ����Ϣ;
				    //System.out.println(line + ", " + M[line] + ", " + annoth + ", " + refTime + ", " + ATRTIME[iAtr] );
				    iAtr++;
				}
				line++;
			} 
			reader.close();
		} 
		catch (IOException e) {  
			e.printStackTrace();  
		}
		finally {  
			if (reader != null){  
				try{  
					reader.close();  
				} 
				catch (IOException e1) {  
				}  
			}  
		}
	}	

//	%% ------ CWT  -----------------------------------
	public static void cwt(int delta){
		int N = delta*20;
		int n = M.length/2;
		int SS, EE;
		double x, phi_x_sum=0.0;
		double phi_x[] = new double[N];
		for(int i=0; i<N; i++){
			x = i-0.5*(N-1);
			phi_x[i]=(Math.pow(Math.PI,-0.25)*2.0/Math.sqrt(3.0))*(1.0 - x*x/(delta*delta))*Math.exp(-(x*x)/(2*delta*delta));
			phi_x_sum += phi_x[i];
		}

		phi_x_sum = Math.sqrt(Math.abs(phi_x_sum));//������һ��
		for(int i=0; i<N; i++)
			phi_x[i] /= phi_x_sum;

		 //���,�ұ����źų���
		if(delta == 4){
			for(int j=0;j<n;j++){	
				Y[j] = 0.0;
				if(j<N/2)
					EE = N/2+j ;
				else
					EE = N;	
				if(j >= n-N/2-1)
					SS = N/2-(n-j-1);
				else
					SS = 0;
				for(int k=SS;k<EE;k++)
					Y[j] += M[2*(j-k)+N+1]*phi_x[k];
				//System.out.println(j +"y, "+ Y[j]);
				Yabs[j] = Math.abs(Y[j]);
			}
			for(int j=0;j<delta*2;j++){	
				Y[j] = 0.0;
				Y[n-j-1] = 0.0;
			}
		}
		else if(delta == 10){
			for(int j=0;j<n;j++){	
				Z[j] = 0.0;
				if(j<N/2)
					EE = N/2+j ;
				else
					EE = N;	
				if(j >= n-N/2-1)
					SS = N/2-(n-j-1);
				else
					SS = 0;
				for(int k=SS;k<EE;k++)
					Z[j] += M[2*(j-k)+N+1]*phi_x[k];
				//System.out.println(j +"z, "+ Z[j]);
			}
			for(int j=0;j<delta*2;j++){	
				Z[j] = 0.0;
				Z[n-j-1] = 0.0;
			}
		}
		else{
			//do nothing!!
		}
	}
//	%% ------Ceil-----------------------------------
	public static int ceil(double dIn){
		int out = (int)dIn;
		if(((int)dIn*10.0)%10 != 0)
			out++;
		return out;

	}
	
//	%% ------ R PEAK DETECTION ---------------------------------------
//	%
//	% �Ƚϼ򵥵�һ�ִ����㷨������С���任��ĳ�߶Ƚ��з�ֵ���
//	% ���������3��������
//	% rPosition����R���������Ϣ
//	% r_value����R��ķ�ֵ
//	% r_direction����R��ķ���1Ϊ���ϣ�0Ϊ���¡�
//	% ����R��һ�������ϵģ����������ַ������£���һ��Ϊ��졣
//	% len_r����R���ĸ���
//	%
	public static void rPeakDet(String[] args){
		int ii;
		double temp;
		double[] tranY = new double[SAMPLES2READ];
		double[] sigMax = new double[SAMPLES2READ*2];//����sigMax��λ������ֵ
		int nSigMax = 0;
		ECGProcess.cwt(4);

		//% ��ֵ���
		System.arraycopy(Y, 0, tranY, 0, Y.length); 
		for(int i=0; i<M.length/2-2; i++){     
		    if(((Y[i+1]>Y[i])&&(Y[i+1]>Y[i+2]))||((Y[i+1]<Y[i])&&(Y[i+1]<Y[i+2]))){          
		        sigMax[nSigMax*2+1] = Math.abs(tranY[i+1]);
		        sigMax[nSigMax*2] = i+1;
		        nSigMax++;  
		    }
		}    
		
		//% ȡ��ֵ,��ֵΪ��Է�ֵ�Ĳ��30%    
		for(int i=0;i<nSigMax;i++){  //��sigMaxð������
	        for(int j=i+1;j<nSigMax;j++){  
	        	if(sigMax[2*i+1]>sigMax[2*j+1]){  
	        		temp=sigMax[2*i+1];  
	        		sigMax[2*i+1]=sigMax[2*j+1];  
	        		sigMax[2*j+1]=temp;
	        		temp=sigMax[2*i];  
	        		sigMax[2*i]=sigMax[2*j];  
	        		sigMax[2*j]=temp; 
	        	}
	        }  
	    }  
		double thr = 0, zerovalue = 0;
		for (int i=0;i<8;i++){     
		    thr += sigMax[2*(nSigMax-i)-1]; 
		} 
		
		for(int i=0;i<tranY.length;i++){  //��tranYð������
	        for(int j=i+1;j<tranY.length;j++){  
	        	if(tranY[i]>tranY[j]){  
	        		temp=tranY[i];  
	        		tranY[i]=tranY[j];  
	        		tranY[j]=temp; 
	        	}
	        }  
	    }  
		for(int i=0;i<100;i++){ 	//��С����ƽ��ֵ���������ȣ�
			zerovalue += tranY[i]; 
		}      
		thr /= 8;               	//������ƽ��ֵ��8������ֵ���ƽ��ֵ    
		zerovalue /= 100;     		//100����С��ֵ���ƽ��ֵ    
		thr = (thr-zerovalue)*0.3; 	//�����С���ȵĲ�ֵ��30%Ϊ�б�R������ֵ
		//System.out.println( "thr = " + thr + "nSigMax = " + nSigMax);

		//% ��λR��  
		nRPosition = 0;
		for(int i=0;i<nSigMax;i++){    
		    if(sigMax[2*i+1]>thr){          
		        rPosition[nRPosition] = (int)sigMax[2*i];
		        //System.out.println( "thr = " + thr + "nSigMax = " + nSigMax);
		        nRPosition++;
			} 
		}
		for(int i=0;i<nRPosition;i++){  //��rPositionð������
	        for(int j=i+1;j<nRPosition;j++){  
	        	if(rPosition[i]>rPosition[j]){  
	        		ii=rPosition[i];  
	        		rPosition[i]=rPosition[j];  
	        		rPosition[j]=ii; 
	        	}
	        }  
	    } 
    	//for(int j=0;j<nRPosition;j++)
    	//	System.out.println("Line" + j + ": " + rPosition[j]); 
		
		//% �ų���죬���������������ֵ���С��0.4����ȥ�����Ƚ�С��һ�� 
		ii = 1;  
		while(ii < nRPosition){       
		    if((rPosition[ii]-rPosition[ii-1]) < 0.4*sFreq){          
		        if(Yabs[rPosition[ii]]>Yabs[rPosition[ii-1]])               
		        	rPosition[ii-1] = rPosition[ii];           
		    	for(int j=ii;j<nRPosition-1;j++)
		    		rPosition[j] = rPosition[j+1];  
		    	rPosition[nRPosition-1]=0; 
		    	nRPosition--;
		        ii--;       
		    }      
		    ii++; 
		}
    	//for(int j=0;j<nRPosition;j++)
    	//	System.out.println("Line" + j + ": " + rPosition[j]); 

		//% ��ԭ�ź��Ͼ�ȷУ׼ 
    	for(int j=0;j<nRPosition;j++){
		    if(Y[rPosition[j]]>0){
		    	ii = -5;
		    	for(int i=-4;i<5;i++) //��rPositionð������
			        if(M[2*rPosition[j]+2*i+1]>M[2*rPosition[j]+2*ii+1])  
			        	ii = i;
		    	rPosition[j] += ii;
		    	rDirection[j] = 1;
		    	//System.out.println("Line" + j + ": " + rPosition[j] + ", " + ii); 
		    }
		    else{       
		    	ii = -5;
		    	for(int i=-4;i<5;i++) //��rPositionð������
			        if(M[2*rPosition[j]+2*i+1]<Y[2*rPosition[j]+2*ii+1])  
			        	ii = i;
		    	rDirection[j] = 0;
		    	System.out.println("ERROR: R�����");
		    }
    	}

		//% ��ȡR���ֵ��Ϣ
    	for(int j=0;j<nRPosition;j++)
		    rValue[j] = M[2*rPosition[j]+1];
	}
	
//	%% ------ RR INTERVAL --------------------------------------------
//	%
//	% RR���ڵ�ƽ��ֵ����Ǹ���10�����ڵ�RR����ֵȷ����
//	% rr_interval������������RR���ڵľ���
//	% rrIntervalMean����RR���ڵ�ƽ��ֵ
//	% rr_interval�ĳ��ȱ�rPosition������1,rrIntervalMean�ĳ�����rPosition��ȣ�rrIntervalMean(1) = rrIntervalMean(2) 
//	%
	public static void rrInterval(String[] args)
	{
		// �����ʼ��rrIntervalMean
		int num, n;
		double rrim = 0;
		rrInterval = new double[nRPosition];
		rrIntervalMean = new double[nRPosition];
		if(nRPosition<= 10){
		    num = nRPosition/2;
		    n = nRPosition/2 + nRPosition%2;
	    	for(int j=0;j<num;j++)
	    		rrim += rPosition[nRPosition-j-1] - rPosition[j];
		    rrim /= (num*n);
		}
		else{
	    	for(int j=0;j<5;j++)
	    		rrim += rPosition[9-j] - rPosition[j];
		    rrim /= 25;
		}
		// ����ȫ����rr_interval��ʣ���rrIntervalMean
		for(int i=0; i<nRPosition-1; i++){
		    rrInterval[i] = rPosition[i+1] - rPosition[i];
		    if(i <= 10)
		    	rrIntervalMean[i+1] = rrim;
		    else
		    	rrIntervalMean[i+1] = rrIntervalMean[i] + (rPosition[i] + rPosition[i-10] - 2 * rPosition[i-5])/25.0;
		    //System.out.println("Line" + i + ": " + rPosition[i] + ", " + rValue[i]+ ", " + rrIntervalMean[i+1]);
		}
		rrIntervalMean[0] = rrIntervalMean[1];
		System.out.println("RR INTERVAL CALCULATION FINISHED ");
	}
	
//	%% ------ Q & S WAVE DETECTION------------------------------------
//	%
//	% ʱ�䴰ɨ���㷨�������50ms
//	% ���������4��������
//	% qPosition��s_posotion����Q��S���Ĳ���������Ϣ
//	% qValue��sValue����Q��S���ķ�ֵ��Ϣ
//	% len_q��len_s�ֱ𱣴�Q����S���ĸ���
//	%
	public static void qsWaveDet(String[] args)
	{
		int qsWindowWidth = sFreq/20;       // width of the window: 50ms 
		int flagQ = 0, flagS = 0;
		int minTemp = 0;
		nQPosition = 0;
		nSPosition = 0;
		// first Q wave detection
		// �����һ��R��С��QS���Ŀ�ȣ���������һ�����������������һ����
		if(rPosition[0] < qsWindowWidth){
	    	for(int i=1;i<rPosition[0];i++){ //�Ե�һ��R֮ǰð����������Сֵ
		        if(M[2*i+1]<M[2*minTemp+1])  
		        	minTemp = i;
	    	}
		    if(minTemp>3){
		        if(rDirection[0] == 1){
		            if((M[2*minTemp+1]<M[2*minTemp+3])&&(M[2*minTemp+1]<M[2*minTemp-1])){
		                qPosition[0] = minTemp;
		                qValue[0] = M[2*minTemp+1];
		                flagQ= 1;     //��־��⵽�˵�һ��Q��
		                nQPosition ++;
		            }
		        }
		        else{// nothing to do!
		        }
			}
		    System.out.println("Line : " + qPosition[0] + ", " + qValue[0]);
		}
		//% last S wave detection
		//% ������һ��R�������ź��ն�С��QS���Ŀ�ȣ�����������һ�����������������һ����
		minTemp = rPosition[nRPosition-1];
		if((M.length/2 - rPosition[nRPosition-1]) < qsWindowWidth){
	    	for(int i=rPosition[nRPosition-1]+1;i<M.length/2;i++){ //�Ե�һ��R֮ǰð����������Сֵ
		        if(M[2*i+1]<M[2*minTemp+1])  
		        	minTemp = i;
	    	}
		    if((M.length/2 - minTemp)<4){
		        if(rDirection[0] == 1){
		            if((M[2*minTemp+1]<M[2*minTemp+3])&&(M[2*minTemp+1]<M[2*minTemp-1])){
		                sPosition[nRPosition-1] = minTemp;
		                sValue[nRPosition-1] = M[2*minTemp+1];
		                flagS= 1;     //��־��⵽�����һ��Q��
		                nSPosition ++;
		            }
		        }
		        else{// nothing to do!
		        }
			}
		    System.out.println("Line : " + qPosition[1] + ", " + qValue[1]);
		}
		
		//other Q & S wave detection
		for(int i=0;i<nRPosition;i++){
		    if(!((flagQ==1)&&(i==0))){
		    	minTemp = rPosition[i]- qsWindowWidth;
		    	for(int j=rPosition[i]- qsWindowWidth+1;j<rPosition[i];j++){ //�Ե�i��R֮ǰð�����������ֵ
			        if(M[2*j+1]<M[2*minTemp+1])  
			        	minTemp = j;
		    	}
		        qPosition[i] = minTemp;
		        qValue[i] = M[2*qPosition[i]+1];
		        nQPosition ++;
			}
		    if(!((flagS==1)&&(i==nRPosition-1))){
		    	minTemp = rPosition[i];
		    	for(int j=rPosition[i]+1;j<rPosition[i]+qsWindowWidth;j++){ //�Ե�i��R֮ǰð�����������ֵ
			        if(M[2*j+1]<M[2*minTemp+1])  
			        	minTemp = j;
		    	}
		        sPosition[i] = minTemp;
		        sValue[i] = M[2*sPosition[i]+1];
		        nSPosition ++;
		    }
		    //System.out.println("Line" + i + ": " + qPosition[i] + ", " + qValue[i] + ", " + sPosition[i] + ", " + sValue[i]);
		}
		
		//% �����һ��Q�������ڣ���ɾ����һ����λ
		if ((qPosition[0] == qValue[0])&&(qValue[0] == 0)){
			for(int i=0;i<nQPosition;i++){
				qPosition[i] = qPosition[i+1];
		    	qValue[i] = qValue[i+1];
		    	nQPosition--;
			}
		}
	}	

//	%% ------ P & T DETECTION ----------------------------------------
//	%
//	% �õ�ʮ��С�����������
//	% T����⣺�ٶ�ST��ʱ��Ϊ0.05-0.12s����˴�S���Ҳ��0.05s����ʼ��⣬��0.4��rr_interval��ʱ�䴰�ڼ���ֵ�㡣
//	% t_position��p_posotion����T��P���Ĳ���������Ϣ
//	% t_value��p_value����T��P���ķ�ֵ��Ϣ
//	% len_t��len_p�ֱ𱣴�T����P���ĸ���
//	% 
	public static void ptWaveDet(String[] args)
	{
		ECGProcess.cwt(10);
		int stMiniWidth = sFreq/20;        // width of the window: 50ms
		int pWindowWidth;
		int baselineWindowWidth;
		int flagP = 0, flagT = 0;
		int minTemp = 0, ii;
		nPPosition = 0;
		nTPosition = 0;
		
		// ����һ��P��
		// �����һ��Q��С��0.4����rr_interval����ô��Ҫ������⡣�����������һ���⡣
		if(qPosition[0] < 0.4*rrIntervalMean[0]){
	    	for(int i=1;i<qPosition[0];i++){ //�Ե�һ��R֮ǰð����������Сֵ
		        if(M[2*i+1]>M[2*minTemp+1])  
		        	minTemp = i;
	    	}
		    if(minTemp>3){
		    	if((M[2*minTemp+1]>M[2*minTemp+3])&&(M[2*minTemp+1]>M[2*minTemp-1])){
		            pPosition[0] = minTemp;
		            pValue[0] = M[2*minTemp+1];
		            flagP= 1;     //��־��⵽�˵�һ��Q��
		            nPPosition ++;
		        }
		        else{// nothing to do!
		        }
		    	System.out.println("Line0: " + pPosition[0] + ", " + pValue[0]); 
			}
		}
		// ���P����T������һ��P�����⣩
		for(int i=0;i<nRPosition;i++){
			if (i<nQPosition){    
				if((i!= 0)||(flagP!= 1)){ 
			        pWindowWidth = (int)(0.4*rrIntervalMean[i]);
			        minTemp = qPosition[i]- pWindowWidth;
			        for(int j=qPosition[i]- pWindowWidth+1;j<qPosition[i];j++){ //�Ե�һ��R֮ǰð����������Сֵ
			        	if(Z[j]>Z[minTemp])  
					    minTemp = j;
			        }
				    ii = -10;
				    for(int j=-9;j<10;j++) //��pPositionð������
					    if(M[2*minTemp+2*j+1]>M[2*minTemp+2*ii+1])  
					       	ii = j;
				    pPosition[i] = minTemp + ii;
				    pValue[i] = M[2*pPosition[i]+1];
				    nPPosition++;
				    //System.out.println("Line" + i + ": " + pPosition[i] + ", " + pValue[i]  + ", " + ii); 
			    }
			}
		    //% ������һ��S�����ź��ն�С��0.4����rr_interval����ô��Ҫ������⡣�����������һ���⡣
			if((i<nRPosition-1)||((i==nRPosition-1)&&((M.length/2-sPosition[nSPosition-1])>=(0.4*rrIntervalMean[i])))){
		        baselineWindowWidth = (int)(0.4 * rrIntervalMean[i]);
		        minTemp = sPosition[i]+stMiniWidth;
		        for(int j=sPosition[i]+stMiniWidth+1;j<sPosition[i]+stMiniWidth+baselineWindowWidth;j++){ //ð����������Сֵ
		        	if(Z[j]>Z[minTemp])  
		        		minTemp = j;
		        }
		        ii = -10;
			    for(int j=-9;j<10;j++) //��pPositionð������
				    if(M[2*minTemp +2*j+1]>M[2*minTemp+2*ii+1])  
				       	ii = j;
			    tPosition[i] = minTemp + ii;
			    tValue[i] = M[2*tPosition[i]+1];
			    nTPosition++;
		    }
		    else if((i==nRPosition)&&((M.length/2-sPosition[nSPosition-1])<(0.4*rrIntervalMean[i]))){
		    	//% ���һ��T���ĵ������
		        if(nSPosition == nRPosition){   //% ���һ��S�����ڣ�T���ſ��ܴ���
		        	minTemp = sPosition[nSPosition-1];
		        	for(int j=sPosition[nSPosition-1]+1;j<M.length/2;j++){ //ð����������Сֵ
			        	if(M[2*j+1]>M[2*minTemp+1])  
					    minTemp = j;
			        }
		        	if ((M.length/2- minTemp) < 4){
		                if((M[2*minTemp+1] > M[2*minTemp+3]) &&(M[2*minTemp+1]> M[2*minTemp-1])){
		                    tPosition[nRPosition] = minTemp;
		                    tValue[nRPosition] = M[2*minTemp+1];
		                    flagT = 1;     //% ��־��⵽�����һ��T��
		                }
		             }
		        }
		    }
		    //System.out.println("Line" + i + ": "+ pPosition[i] + ", " + pValue[i]  + ", " + tPosition[i] + ", " + tValue[i]);
		}
	}
	
//	%% ------ BASE LINE DETECTION ------------------------------------
//	% 
//	% ��ȡ���ߣ�ʹ��ǰһ������T�˽�������ǰ��P����ʼ���ʱ�䡣
//	% baselineValue���沨�εĻ���ֵ
//	% baselineStartPosition������߿�ʼʱ��ֵ
//	% baselineEndPosition������߽���ʱ��ֵ
//	% len_baseline�����⵽�Ļ��ߵĸ���
//	%
	public static void baseLineDet(String[] args)
	{
		int  minTemp1, minTemp2 = 0,pp, k;
		int startBaseline, endBaseline;
		int baselineWindowWidth;
		double temp;
		lenBaseline = 0;
		for(int i=0; i<nTPosition;i++){
		    baselineWindowWidth = ceil(rrIntervalMean[i]*0.3);
		    
		    //% Ѱ�һ������
		    if((tPosition[i] + baselineWindowWidth) > Z.length){
		        break;
		    }
	        minTemp1 = tPosition[i];
	        for(int j=tPosition[i]+1;j<tPosition[i] + baselineWindowWidth;j++){ //�Ե�һ��R֮ǰð����������Сֵ
	        	if(Z[j]>Z[minTemp1])  
			    minTemp1 = j;
	        }
		    for(int j=tPosition[i]+10;j<tPosition[i] + baselineWindowWidth;j++){
		        if((Z[j] < Z[j+1]) && (Z[j]<Z[j-1]) ){
		        	minTemp2 = j;
		            break;
		        }
		    }
		    //System.out.println("Line" + i + ": "+ minTemp1 + ", " + minTemp2);
		    if(minTemp1 > minTemp2){
		        startBaseline = minTemp1;
		    }
		    else{
		        startBaseline = minTemp2;
		    }
		    
		    //% Ѱ�һ����յ�
		    if(i>nPPosition-1)
		        break;
		    if(pPosition[i] < tPosition[i]){
		        if(i> nPPosition)
		            break;
		        else
		            pp = pPosition[i+1];
		    }
		    else{
		        pp = pPosition[i];
		    }
	        minTemp1 = pp - baselineWindowWidth;
	        for(int j=pp - baselineWindowWidth+1;j<pp;j++){ //�Ե�һ��R֮ǰð����������Сֵ
	        	if(Z[j]>Z[minTemp1])  
			    minTemp1 = j;
	        }
		    k = pp - 10;
		    while(k > pp - baselineWindowWidth-1){
		        if((Z[k] < Z[k+1]) && (Z[k]<Z[k-1]) ){
		        	minTemp2 = k;
		            break;
		        }
		        k--;
		    }
		    if(minTemp1  > minTemp2)
		        endBaseline = minTemp2;
		    else{
		        endBaseline = minTemp1;
		    }
		    
		    //% �������ֵ
		    temp = 0.0;
		    for(int j=startBaseline+5;j<endBaseline-5;j++){
		    	temp += M[2*j+1];
		    }
		    baselineValue[i] = temp/(endBaseline-startBaseline-10);
		    baselineStartPosition[i] = startBaseline + 5;
		    baselineEndPosition[i] = endBaseline - 5;
		    lenBaseline++;
		    //System.out.println("Line" + i + ": "+ baselineValue[i] + ", " + baselineStartPosition[i]  + ", " + baselineEndPosition[i]);
		}	
	}
//	%% ------ ONSET, OFFSET AND DURATION OF QRS COMPLEX --------------
//	%
//	% ���QRS���ϲ�����ֹ��ͳ���ʱ�䡣
//	% ʱ�䴰��ȣ���㴰��35ms���յ㴰��20ms��
//	% ����һ����Q��������ֵΪQ��1/8�ĵ�Ϊ��㣬S���Ҳ��ֵΪS��1/5�ĵ�Ϊ�յ㣻
//	% ����������Q���������ӽ����ߵĵ�Ϊ��㣬S���Ҳ���ӽ����ߵĵ�Ϊ�յ㣻
//	% ���������������ѡ������R���ĵ���Ϊ�����յ㡣
//	% �����������qrsOnPosition,qrsOffPosition��qrsDuration�С�
//	% len_qrs�����⵽����ЧQRS���ϲ��ĸ�������һ��QRS���ϲ�����û�л��߿��ܱ����ԣ�
//	%	
	public static void qrsComplex(String[] args)
	{
		int qrsOnWindowWidth = ceil(35*sFreq/1000);
		int qrsOffWindowWidth = ceil(sFreq/50);
		int flagQRS = 1;
		int qInd = 0, sInd = 0;
		int i = 0, index, minTemp;
		lenQRS = 0;

		while(qPosition[qInd] < baselineEndPosition[0]){
		    qInd++;
		    flagQRS = 0;
		}

		while ((qInd <= nQPosition) &&( sInd <= nSPosition) &&(i <= lenBaseline)){
		    while(qPosition[qInd] > sPosition[sInd]){
		        sInd++;
		    }
		    //% ȷ��onset
		    index = qPosition[qInd];
		    while ( index >= (qPosition[qInd] - qrsOnWindowWidth) ){
		        if(qValue[qInd] < baselineValue[i]){
		            if ( (baselineValue[i] - M[2*index+1]) < (baselineValue[i] - qValue[qInd])/8 ){
		                break;
		            }
		        }
		        else{
		            index = qPosition[qInd];
		            break;
		        }
		        index--;
		    }
		    minTemp = qPosition[qInd]- qrsOnWindowWidth;
	        for(int j=qPosition[qInd]- qrsOnWindowWidth+1; j<qPosition[qInd];j++){ //ð����������Сֵ
	        	if(Math.abs(M[2*j+1]-baselineValue[i])>Math.abs(M[2*minTemp+1]-baselineValue[i])) 
	        		minTemp = j;
	        }
		    if(minTemp < index){
		        qrsOnPosition[i] = index;
		    }
		    else{
		        qrsOnPosition[i] = minTemp;
		    }
		       
		    //ȷ��offset
		    index = sPosition[sInd];
		    while ( index <= sPosition[sInd] + qrsOffWindowWidth ){
		        if ( sValue[sInd] < baselineValue[i] ){
		            if ( (baselineValue[i] - M[2*index+1]) < (baselineValue[i] - sValue[sInd])/5 ){
		                break;
		            }
		        }
		        else{
		            index = sPosition[sInd];
		            break;
		        }
		        index++;
		    }
	        minTemp = sPosition[sInd];
	        for(int j=sPosition[sInd]+1; j<sPosition[sInd]+ qrsOffWindowWidth;j++){ //ð����������Сֵ
	        	if(Math.abs(M[2*j+1]-baselineValue[i])>(Math.abs(M[2*minTemp+1]-baselineValue[i]))) 
	        		minTemp = j;
	        }
		    if(minTemp < index)
		        qrsOffPosition[i] = minTemp;
		    else
		        qrsOffPosition[i] = index;
		    
		    // ȷ��duration
		    qrsDuration[i] = qrsOffPosition[i] - qrsOnPosition[i];
		    System.out.println("Line" + i + ": "+ qrsDuration[i] + ","+ qrsOnPosition[i] + ", "+ qrsOffPosition[i]);
		    lenQRS++;
		    qInd++;
		    sInd++;
		    i++;
		}
	}
	
//	%% ------ ONSET, OFFSET AND DURATION OF P & T WAVE ---------------
//	%
//	% ���P����T������ֹ��ͳ���ʱ�䡣
//	%
//	% P����ֹ����ʹ�����׷�����
//	% ����1���ֱ��P������һ������ڼ��ԭʼ�źŷ�ֵΪP��1/8�ĵ���Ϊ��ֹ�㣻
//	% ����2���ֱ��P������һ������ڼ��ԭʼ�źŲ��ιյ㣬�����׵��ӽ���ĵ㣻������������ܴ��ų����ã�
//	% ����3���ֱ��P������һ������ڼ��ԭʼ�ź���ӽ����ߵĵ���Ϊ��ֹ�㣻
//	% ���������������ѡ������P���ĵ���Ϊ�����յ㡣
//	% �����������pOnPosition,pOffPosition��pDuration�С�len_pw���������
//	%
//	% T���ļ���ʹ������˼·��
//	% ����1����ʵ������˷�������ʸߣ��ų����ã�
//	% ������Ե�ʮ��С���������������T������һ������ڲ��ιյ㣬�����׵��ӽ���ĵ㣻
//	% Ȼ������յ�����10�����ڣ�����ԭʼ�ź�����ӽ����ߵĵ���ΪT���������յ㡣
//	% ����2��
//	% ������Ե�ʮ��С�������������T������һ������ڼ���źŷ�ֵΪT��1/8�ĵ㣻
//	% Ȼ���ڸõ�����10�����ڣ�����ԭʼ�ź�����ӽ����ߵĵ���ΪT���������յ㡣
//	% ����3��
//	% ������Ե�ʮ��С�������������T������һ������ڼ���źż�ֵ�㣨����T����⼫С������T����⼫�󣩣�
//	% Ȼ���ڸõ�����10�����ڣ�����ԭʼ�ź�����ӽ����ߵĵ���ΪT���������յ㡣
//	% �����������tOnPosition,tOffPosition��tDuration�С�len_tw���������
//	%
/*	public static void ptWave(String[] args)
	{
		pOnPosition = [];
		pOffPosition = [];
		pDuration = [];
		pOnWindowWidth = ceil(sFreq/125);
		pOffWindowWidth = ceil(sFreq/125);
		tOnPosition = [];
		tOffPosition = [];
		tDuration = [];

		//% ��ʼ��P���������
		int bInd = 1, pInd = 1, i = 1;

		//% �ҵ���һ�����л�����Ϣ��P��
		while p_position(pInd) < baselineStartPosition(bInd)
		    pInd = pInd + 1;
		end

		//% ���P������ֹ��ͳ���ʱ��
		while ( bInd <= len_baseline && pInd <= len_p )
		    % onset of P wave
		    tmp1 = p_position(pInd);
		    while tmp1 > p_position(pInd) - pOnWindowWidth
		        if p_value(pInd) < baseline_value(bInd)
		            if ( baseline_value(bInd) - sig(tmp1) < 1/8*(baseline_value(bInd) - p_value(pInd)) )
		                break;
		            end
		        else
		            if ( baseline_value(bInd) - sig(tmp1) > 1/8*(baseline_value(bInd) - p_value(pInd)) )
		                break;
		            end
		        end
		        tmp1 = tmp1 - 1;
		    end
		    pOnPosition(i) = tmp1;
		    
		    target = sig( (p_position(pInd) - pOnWindowWidth) : p_position(pInd) );

		    [~, tmp3] = min( abs(target - baseline_value(bInd)) );
		    tmp3 = tmp3 + p_position(pInd) - pOnWindowWidth - 1;
		    if pOnPosition(i) < tmp3
		        pOnPosition(i) = tmp3;
		    end

		    % offset of P wave
		    tmp1 = p_position(pInd);
		    while tmp1 < p_position(pInd) + pOffWindowWidth
		        if p_value(pInd) < baseline_value(bInd)
		            if ( baseline_value(bInd) - sig(tmp1) < 1/8*(baseline_value(bInd) - p_value(pInd)) )
		                break;
		            end
		        else
		            if ( baseline_value(bInd) - sig(tmp1) > 1/8*(baseline_value(bInd) - p_value(pInd)) )
		                break;
		            end
		        end
		        tmp1 = tmp1 + 1;
		    end
		    pOffPosition(i) = tmp1;
		    
		    target = sig(p_position(pInd) : p_position(pInd) + pOffWindowWidth);

		    [~, tmp3] = min( abs(target - baseline_value(bInd)) );
		    tmp3 = tmp3 + p_position(pInd);
		    if pOffPosition(i) > tmp3
		        pOffPosition(i) = tmp3;
		    end

		    // % ȷ��duration
		    pDuration(i) = pOffPosition(i) - pOnPosition(i);
		    
		    pInd = pInd + 1;
		    bInd = bInd + 1;
		    i = i+1;
		end

		//% ��ʼ��T���������
		bInd = 1;
		t_ind = 1;
		qrs_ind = 1;
		i = 1;

		//% �ҵ���һ�����л�����Ϣ��T��
		while t_position(t_ind) < baselineStartPosition(bInd)
		    t_ind = t_ind + 1;
		end
		while t_position(t_ind) < qrsOffPosition(qrs_ind)
		    t_ind = t_ind + 1;
		end

		//% ���T������ֹ��ͳ���ʱ��
		while ( bInd <= len_baseline && t_ind <= len_t )
		    % onset of T wave
		    target = wtsig10( qrsOffPosition(qrs_ind)+5 : t_position(t_ind) );
		    
		    tmp = t_position(t_ind);
		    while tmp >= qrsOffPosition(qrs_ind)+5
		        if t_value(t_ind) < baseline_value(bInd)
		            if wtsig10(tmp) > 0
		                break;
		            end
		        else
		            if wtsig10(tmp) < 0
		                break;
		            end
		        end
		        tmp = tmp - 1;
		    end
		    [~, tmp2] = min( abs( sig(tmp-20 : tmp+15) - baseline_value(bInd) ) );
		    tmp2 = tmp2 + tmp - 21;
		    tOnPosition(i) = tmp2;
		    
		    tmp = t_position(t_ind);
		    while tmp >= qrsOffPosition(qrs_ind)+5
		        if t_value(t_ind) < baseline_value(bInd)
		            if ( wtsig10(tmp) > wtsig10(tmp-1) && wtsig10(tmp) > wtsig10(tmp+1) )
		                break;
		            end
		        else
		            if ( wtsig10(tmp) < wtsig10(tmp-1) && wtsig10(tmp) < wtsig10(tmp+1) )
		                break;
		            end
		        end
		        tmp = tmp - 1;
		    end
		    [~, tmp3] = min( abs( sig(tmp-10 : tmp+10) - baseline_value(bInd) ) );
		    tmp3 = tmp3 + tmp - 11; 
		    if tmp3 > pOnPosition(i)
		        tOnPosition(i) = tmp3;
		    end

		    % offset of T wave
		    if ( t_ind == len_t || bInd == len_baseline )
		        rri = rr_interval(i-1);
		    else
		        rri = rr_interval(i);
		    end
		    target = wtsig10( t_position(t_ind) : ceil(t_position(t_ind) + 0.125 * rri) );
		    
		    tmp = t_position(t_ind);
		    while tmp <= ceil(t_position(t_ind) + 0.125 * rri)
		        if t_value(t_ind) < baseline_value(bInd)
		            if wtsig10(tmp) > 0
		                break;
		            end
		        else
		            if wtsig10(tmp) < 0
		                break;
		            end
		        end
		        tmp = tmp + 1;
		    end
		    [~, tmp2] = min( abs( sig(tmp-10 : tmp+15) - baseline_value(bInd) ) );
		    tmp2 = tmp2 + tmp - 11;

		    tmp = t_position(t_ind);
		    while tmp <= ceil(t_position(t_ind) + 0.125 * rri)
		        if t_value(t_ind) < baseline_value(bInd)
		            if ( wtsig10(tmp) > wtsig10(tmp-1) && wtsig10(tmp) > wtsig10(tmp+1) )
		                break;
		            end
		        else
		            if ( wtsig10(tmp) < wtsig10(tmp-1) && wtsig10(tmp) < wtsig10(tmp+1) )
		                break;
		            end
		        end
		        tmp = tmp + 1;
		    end
		    [~, tmp3] = min( abs( sig(tmp-10 : tmp+15) - baseline_value(bInd) ) );
		    tmp3 = tmp3 + tmp - 11;
		    if tmp3 < pOnPosition(i)
		        tOffPosition(i) = tmp3;
		    end
		    
		    % duration of T wave
		    tDuration(i) = tOffPosition(i) - tOnPosition(i);
		    
		    bInd = bInd + 1;
		    t_ind = t_ind + 1;
		    qrs_ind = qrs_ind + 1;
		    i = i + 1;
		end

		len_pw = length (pDuration);
		len_tw = length (tDuration);
	}*/
	
//	%% ------ OTHER FEATURES DETECTION -------------------------------
//	%
//	% �����Ѿ��õ�����Ϣ��ȡ��������Ҫ����Ϣ��
//	% ��ȡ����Ϣ������
//	% ��ֵ��Ϣ��P��Q��R��S��T��U����U����ʱ�����ǣ����壬QRS�������ֵ
//	% ���ڣ�P����PR��Q��QR���ұڼ���ʱ�䣩��QRS��ST��QT��QTc��T��U��U����ʱ�����ǣ���rr_interval��PPI
//	% ����Ľ���������嵥�����з��ȵĵ�λΪ������mv����ʱ����ص����ĵ�λΪ�루s����
//	%
	public static void otherFeature(String[] args)
	{
		
	}	
	
	public static void main(String[] args) {  
		SAMPLES2READ = 5000;
		ECGProcess.readHeadFile("F:/Java/117.hea");
		ECGProcess.readDataFile("F:/Java/117.dat");
		ECGProcess.readAtrFile("F:/Java/117.atr");
		ECGProcess.rPeakDet(args);
		ECGProcess.rrInterval(args);
		ECGProcess.qsWaveDet(args);
		
		ECGProcess.ptWaveDet(args);
		ECGProcess.baseLineDet(args);
		ECGProcess.qrsComplex(args);
	} 
}