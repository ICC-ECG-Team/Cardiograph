package com.cardiograph.util;

import com.cardiograph.constance.Constance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBUtil
{
    /* ����˽�о�̬ʵ������ֹ�����ã��˴���ֵΪnull��Ŀ����ʵ���ӳټ��� */ 
	private static DBUtil instance = null;
	private MySQLiteOpenHelper helper = null;
	private SQLiteDatabase sdb = null;
	public static Context context;
	/* ˽�й��췽������ֹ��ʵ���� */
	private DBUtil()
	{
	}
	/* ��̬���̷���������ʵ�� */
	public static DBUtil getInstance()
	{
		 if (instance == null) {  
//			synchronized (instance) { 
				if(instance == null)
				{
					instance = new DBUtil();
				}
//			}
		 }
		return instance;
	}
	
	public SQLiteDatabase getSQLiteDatabase()
	{
		if(helper == null)
		{
			helper = new MySQLiteOpenHelper(context, Constance.DB_NAME, null, Constance.DB_VERSION);
		}
		if(sdb == null)
		{
			sdb = helper.getWritableDatabase();
		}
		return sdb;
	}
	
	public void closeDB()
	{
		if(sdb != null)
		{
			sdb.close();
			sdb = null;
		}
	}
	
	/* ����ö����������л������Ա�֤���������л�ǰ�󱣳�һ�� */  
	public Object readDBUtil() {  
	     return instance;  
 }  

}
