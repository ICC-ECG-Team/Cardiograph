package com.cardiograph.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

/**
 * ������Ϣ��
 * ׷��ִ�й켣����ȡ�쳣λ�ã���ӡ������־��Ϣ
 * @author 
 *
 */
public class Debugger {
	
	public  final String LAKALATAG = "lakalademotag";
	public  boolean logAvailable = true;
	
	/**
	 * ��ȡ�����쳣����λ��
	 * @param exception �쳣
	 * @return   ��ȡ�����쳣�Ĵ���λ�ã��ļ�--����--������--�����У�
	 */
	private  String getCodePosition(Exception exception){
		StackTraceElement entry= exception.getStackTrace()[0];
		String message = String.format("[%s]\n	FileName:%s\n	ClassName:%s\n	MethodName:%s\n	Line:%s\n",
					new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss ").format(new Date()),
					entry.getFileName(),
					entry.getClassName(),
					entry.getMethodName(),
					entry.getLineNumber()
					);
				
		return message;
	}
	
	
	/**
	 * ��logcatд���쳣��Ϣ
	 * �����쳣���浽��־�ļ�
	 * @see #getCodePosition(Exception)
	 */
	public  void log(Exception exception){
		try {
			String msg = getCodePosition(exception).concat("\n	"+exception.toString());
			Log.e(LAKALATAG,msg); 
			Logger.instance.logout(msg);
			exception.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ���Log.i  ,�������λ�� 
	 * @see #getCodePosition(Exception)
	 */
	public  void log(Exception exception, String str){
		if (!logAvailable) return;
		
		String msg = getCodePosition( exception).concat(str);
		Log.i(LAKALATAG,msg);
	}
	
	
	/**
	 * ���Log.i  ,�������λ�� 
	 * @see #getCodePosition(Exception)
	 */
	public  void log(Exception exception, int i){
		if (!logAvailable) return;
		String msg = getCodePosition( exception).concat(String.valueOf(i));
		Log.i(LAKALATAG,msg);
	}
	
	
	/**
	 * ���Log.i  ,�������λ�� 
	 * @see #getCodePosition(Exception)
	 */
	public  void log(Exception exception, long l){
		if (!logAvailable) return;
		String msg = getCodePosition( exception).concat(String.valueOf(l));
		Log.i(LAKALATAG,msg);	
	}
	
	
	/**
	 * ���Log.i  ,�������λ�� 
	 * @see #getCodePosition(Exception)
	 */
	public  void log(Exception exception, boolean b){
		if (!logAvailable) return;
		String msg = getCodePosition(exception).concat(String.valueOf(b));
		Log.i(LAKALATAG,msg);
	}
	
	
	/**
	 * ���Log.i  ,�������λ�� 
	 * @see #getCodePosition(Exception)
	 */
	public  void log(Exception exception, Object obj){
		if (!logAvailable) return;
		String msg = getCodePosition( exception).concat(obj.toString());
		Log.i(LAKALATAG,msg);
	}
	
	/**
	 * ��ӡ����ִ�еķ�������
	 * @param traceElement ׷��Ԫ��
	 * 
	 */
	public  void log(StackTraceElement traceElement) {
		if (!logAvailable) return;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(traceElement.getClassName());
		stringBuilder.append(".");
		stringBuilder.append(traceElement.getMethodName());
		stringBuilder.append("()");
		Log.i(LAKALATAG,stringBuilder.toString());
	}
	
	/**
	 * ��ӡ����ִ�еķ������ƣ���������Ϣ
	 * @param traceElement ׷��Ԫ��
	 * @param message		������Ϣ 
	 */
	public  void log(StackTraceElement traceElement,String message) {
		if (!logAvailable) return;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(traceElement.getClassName());
		stringBuilder.append(".");
		stringBuilder.append(traceElement.getMethodName());
		stringBuilder.append("()");
		stringBuilder.append("->"+message);
		Log.i(LAKALATAG,stringBuilder.toString());
	}
}
