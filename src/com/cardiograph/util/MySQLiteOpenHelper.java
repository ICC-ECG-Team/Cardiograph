package com.cardiograph.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.example.cardiograph.R;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper
{
	private Context context;
	
	/**
	 * ���캯����
	 * ��������:
	 * ����˵����
	 * 		@param context	�����Ļ���
	 * 		@param name		���ݿ�����
	 * 		@param factory	cursor����
	 * 		@param version	���ݿ�İ汾��
	 */
	public MySQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version)
	{
		super(context, name, factory, version);
		this.context = context;
	}

	/**
	 *  �������� ��onCreate
	 *  �������� ���������ݿ⻹û�б�������ʱ�򣬲Żᱻ����  
	 *  ����˵�� ��
	 *  	@param db
	 *  ����ֵ��
	 *  	
	 *  �޸ļ�¼��
	 *  ���� ��2013-10-25 ����7:00:10	�޸��ˣ�gy
	 *  ���� ��
	 * 					
	 */
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		System.out.println("������ onCreate ����");
		//�����ݿ�ĳ�ʼ���Ĳ���
		Resources res = context.getResources();
		BufferedReader br = null;
		br = new BufferedReader(
				new InputStreamReader(res.openRawResource(R.raw.cardiograph)));
		String strSql = null;
		try
		{
			while((strSql = br.readLine()) != null)
			{
				db.execSQL(strSql);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 *  �������� ��onUpgrade
	 *  �������� �� ���ݿ��Ѿ����������ˣ��������ݿ�İ汾��ԭ�������ݿ�汾��һ��ʱ���ŵ���������� 
	 *  ����˵�� ��
	 *  	@param db
	 *  	@param oldVersion
	 *  	@param newVersion
	 *  ����ֵ��
	 *  	
	 *  �޸ļ�¼��
	 *  ���� ��2013-10-25 ����7:00:10	�޸��ˣ�gy
	 *  ���� ��
	 * 					
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		System.out.println("������ onUpgrade ����");
		System.out.println("������ onCreate ����");
		//�����ݿ�ĳ�ʼ���Ĳ���
		Resources res = context.getResources();
		BufferedReader br = null;
		br = new BufferedReader(
				new InputStreamReader(res.openRawResource(R.raw.cardiograph)));
		String strSql = null;
		try
		{
			while((strSql = br.readLine()) != null)
			{
				db.execSQL(strSql);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
