package com.cardiograph.thread;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cardiograph.constance.Constance;
import com.cardiograph.util.PreferencesUtil;
import com.cardiograph.util.Util;
import com.cardiograph.view.LoginActivity;

public class FileUpLoad extends Thread{
	private String TAG = "FileUpLoad";
    private String strContent;
    private Context context;
    private List<File> lstData;
    
	public FileUpLoad(Context context, List<File> lstData) {
		super();
		this.context = context;
		this.lstData = lstData;
	}

	@Override
	public void run() {
		upLoadFile();
		super.run();
	}

	public void upLoadFile(){
		HttpClient httpClient = new DefaultHttpClient();
		try
		{
			HttpPost httpPost = new HttpPost(Constance.URL_UPLOAD);
//			httpPost.setHeader("Range","bytes="+"");
			//����һ�� MultipartEntity �������ڴ�Ŷ�����ݣ��磺�����Ǳ����ݺ��ϴ����ļ�(����)
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("action", new StringBody("upload", Charset.forName("gbk")));
			String key = PreferencesUtil.getInstance(context).getString("key");
			reqEntity.addPart("key", new StringBody(key, Charset.forName("gbk")));
//			String start = new Date().getTime()/1000+"";
			String name = lstData.get(0).getName();
			String start = name.substring(0, name.lastIndexOf("."));
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			start = format.parse(start).getTime()/1000 + "";
			reqEntity.addPart("start", new StringBody(start, Charset.forName("gbk")));
			reqEntity.addPart("freq", new StringBody("125", Charset.forName("gbk")));
			reqEntity.addPart("version", new StringBody("1", Charset.forName("gbk")));
//			reqEntity.addPart("username", new StringBody("icc", Charset.forName("gbk")));
//			reqEntity.addPart("password", new StringBody("fjsjcdlsjzx", Charset.forName("gbk")));
			for(int i = 0;i<lstData.size();i++){
				//Ҫ�ϴ����ļ�
				FileBody fBody = new FileBody(lstData.get(i));
				System.out.println("====="+lstData.get(i).toString());
				//���ʵ���е�Ԫ�أ�������ӵ���һ���ļ�
//				reqEntity.addPart(lstData.get(i).getName(), fBody);
				reqEntity.addPart("ecgfile", fBody);
			}
//			//Ҫ���ϴ��ļ�һ���ύ�ı����ݣ�ע���������룬�����ڶ�����������ת��
//			ContentBody cbCaseInfoId = new StringBody(proof.getCaseInfoId()+"", Charset.forName("gbk"));
//			ContentBody cbType = new StringBody(proof.getType()+"", Charset.forName("gbk"));
//			ContentBody cbPath = new StringBody(proof.getPath(), Charset.forName("gbk"));
//			
//			
//			//���ʵ���е�Ԫ�أ�������ӵ���һ��������
//			reqEntity.addPart("caseInfoId", cbCaseInfoId);
//			reqEntity.addPart("type", cbType);
//			reqEntity.addPart("path", cbPath);
			
			//�����������Ӱ���
			httpPost.setEntity(reqEntity);
			Log.d(TAG,"start="+start);
			Log.d(TAG,httpPost.getURI().getScheme()+"://"+httpPost.getURI().getHost()+":"+httpPost.getURI().getPort()+httpPost.getURI().getPath());
//			Log.d(TAG,EntityUtils.toString(reqEntity));
			Log.d(TAG,"��ʼ�ϴ��ļ�");
			//��������
			HttpResponse httpResponse = httpClient.execute(httpPost);
			//��ȡ��Ӧ״̬��
			final int statusCode = httpResponse.getStatusLine().getStatusCode();

			Log.d(TAG,"statusCode="+statusCode);
			//����ɹ�
			if(statusCode == HttpStatus.SC_OK)
			{
				HttpEntity httpRespEntity = httpResponse.getEntity();
				
//				//��ȡ��Ӧ���ݷ���һ
//				is = httpRespEntity.getContent();
//				//��ȡ���ݳ���
//				long contentLen = httpRespEntity.getContentLength();
//				System.out.println("���ݳ��� = " + contentLen);
//				//���������ݵ��ֽ�����
//				byte[] arrContent = new byte[(int)contentLen];
//				
//				int iDate;
//				int i = 0;
//				while((iDate = is.read()) != -1)
//				{
//					//�����е�����д�뵽�ֽ�������
//					arrContent[i++] = (byte)iDate;
//				}
//				//��ȡ��������ת���� GBK ���룬��ֹ��������
//				String strContent = new String(arrContent, "gbk");
				
				
				//��ȡʵ�����ݷ��������������ַ�ʽ������
				strContent = EntityUtils.toString(httpRespEntity, "GBK");
				JSONObject jsonObject = new JSONObject(strContent);
				final String result = jsonObject.getString("result");
				if(result.equals(Constance.RESULT_OK)){
					((Activity)context).runOnUiThread(new Runnable() {
						public void run() {
							Util.toast(context, "�ļ��ϴ��ɹ�");
						}
					});
				}else if(result.equals(Constance.RESULT_KEY_EXPIRED) || result.equals(Constance.RESULT_KEY_ERROR)){
					((Activity)context).runOnUiThread(new Runnable() {
						public void run() {
							Util.toast(context, "key������߹��ڣ������µ�¼��");
						}
					});
					PreferencesUtil.getInstance(context).putString("key_time","");
					Intent intent = new Intent(context, LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(intent);
				}else{
					((Activity)context).runOnUiThread(new Runnable() {
						public void run() {
							Util.toast(context, "�ļ��ϴ�ʧ��");
						}
					});
				}
				Log.d(TAG,strContent);
//				String strContent = EntityUtils.toString(httpRespEntity);
				
			}else{
				((Activity)context).runOnUiThread(new Runnable() {
					public void run() {
						Util.toast(context, "�ļ��ϴ�ʧ��");
					}
				});
			}
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				httpClient.getConnectionManager().shutdown();
			}
			catch (Exception ignore)
			{
				ignore.printStackTrace();
			}
		} 
//		processer.process(strContent);
	}
}