package com.cardiograph.jni;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��Ҷ����
 *  ����ʱ��	��2014-11-9 ����7:01:36 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2014-11-9 ����7:01:36 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class JNI_Interface {
	//native�����ء�ԭ��̬
	public static native int getCInt();
	public static native String getCString();
	public static native float trapper(float x, float fs);
}
