package com.cardiograph.net;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.cardiograph.commom.ApplicationExtension;
import com.cardiograph.constance.Parameters;
import com.cardiograph.log.Debugger;
import com.cardiograph.util.Util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * ����������Ļ�����
 * �ṩͨ�õĹ��߷���
 * @author bob
 *
 */
public class BaseServiceManager {
	private final static char charTable[]=
		{'0','1','2','3','4','5','6','7','8','9',
		 'a','b','c','d','e','f','g','h','i','j',
		 'k','l','m','n','o','p','q','r','s','t',
		 'u','v','w','x','y','z',
		 'A','B','C','D','E','F','G','H','I','J',
		 'K','L','M','N','O','P','Q','R','S','T',
		 'U','V','W','X','Y','Z'};
	
	/**
	 * ΨһID���ͻ��˿ɸ����ֻ�ƽ̨�����ԣ���ѡȡ�豸�ţ�
	 * ESMI��������������Ψһ��ʾ�û�ID���ַ�����
	 */
	private String uid = null;
	

	/**
	 * ��֤�룬uid+�ͻ������� ����MD5��ϢժҪֵ���ͻ�������Ϊ�����
	 * ��ÿ���ֻ�ƽ̨�ͻ���Ӧ�ó���֮��Լ�������롣
	 */
	private String vercode = null;
	
	/**
	 * ������֤�롣
	 * @throws java.security.NoSuchAlgorithmException
	 */
	protected void generateVercode() throws NoSuchAlgorithmException
	{
		//������� UID
		this.uid = generateUID(16);
		
		String origWord = "";
		origWord = uid + Parameters.androidClientID;
		
		//�õ�Md5 ժҪ�㷨ʵ��
		MessageDigest md5 = MessageDigest.getInstance("MD5");
	
		md5.update(origWord.getBytes());
		
		//�õ�ժҪ����
		byte[]  md5Word= md5.digest();
		
		//��ժҪ���ݸ�ʽ�����ַ��������档
		this.vercode = String.format(
				"%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x",
				md5Word[0],
				md5Word[1],
				md5Word[2],
				md5Word[3],
				md5Word[4],
				md5Word[5],
				md5Word[6],
				md5Word[7],
				md5Word[8],
				md5Word[9],
				md5Word[10],
				md5Word[11],
				md5Word[12],
				md5Word[13],
				md5Word[14],
				md5Word[15]);
	}
	
	public String getUid() {
		return uid;
	}

	public String getVercode() {
		return vercode;
	}

	
	/**
	 * ��JSonObject ����ת��Ϊ ResultForService����
	 * ��Ҫ���ڽ�RestWebService��postRequest���صĶ���ת��Ϊҵ���ͳһ�ķ��ض���ResultForService
	 * @param jo  JSONObject����
	 * @return	  ResultForService ����
	 * @throws org.json.JSONException
	 */
	public ResultForService json2ResultForService(JSONObject jo) throws JSONException{
		if (jo == null) {
			throw new  JSONException("json������Ϊnull");
		}
		if (!jo.has("retStatus")|| !jo.getJSONObject("retStatus").has("retCode") || !jo.getJSONObject("retStatus").has("errMsg")) {
			throw new JSONException("json�����ʽ����ȷ");
		}
		ResultForService resultForService = new ResultForService();
		resultForService.retCode = jo.getJSONObject("retStatus").getString("retCode");
		resultForService.errMsg = jo.getJSONObject("retStatus").getString("errMsg");
		//���Խ� retData ת���� JSONObject
		resultForService.retData = jo.optJSONObject("retData");
		//retData == null ���ܷ��ص���һ��JSONArray �������ڴγ��Խ���ת�� JSONArray
		if (resultForService.retData == null)
		{
			resultForService.retData = jo.optJSONArray("retData");
		}
		
		return resultForService;
	}
	
	/**
	 * ִ�� Put ���󣬲���Ӱ�ȫ��֤�����������С�
	 * @param url
	 * @param parameter
	 * @return
	 * @throws BaseException 
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService putRequest(String url,List<NameValuePair> parameter) throws ParseException, IOException, BaseException
	{
		ResultForService resultForService = null;
		
		//���ڷ����put �������ܴ�body����ȡ�����ݣ����Խ�vercode����url�ϡ�
		List<NameValuePair> vercode = new ArrayList<NameValuePair> ();
		vercode = addVercode(vercode);
		filterUrlAndAppendMac(url, parameter);
		url = url.concat(RestWebService.toUrlParameter(vercode,HttpUtil.EncodingCharset));
		JSONObject json = RestWebService.putRequest(url, parameter,HttpUtil.EncodingCharset);
	
		try {
			resultForService = json2ResultForService(json);
			checkToken(resultForService.retCode);
		} catch (JSONException e) {
			// json �����쳣���񷵻��˴�������ݣ����ⷵ���Զ�����쳣�ࡣ
			throw new BaseException("",ExceptionCode.ServerResultDataError, 0);
		}

		return resultForService;
	}
	
	/**
	 * ִ�� Get ���󣬲���Ӱ�ȫ��֤�����������С�
	 * @param url
	 * @param parameter
	 * @return
	 * @throws BaseException 
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService getRequest(String url,List<NameValuePair> parameter) throws ParseException, IOException, BaseException
	{
		ResultForService resultForService = null;
		
		parameter = addVercode(parameter);
		filterUrlAndAppendMac(url, parameter);
		JSONObject json = RestWebService.getRequest(url, parameter, HttpUtil.EncodingCharset);
		if(Parameters.debug){
			System.out.println("url:"+url);
		}
		try {
			resultForService = json2ResultForService(json);
			checkToken(resultForService.retCode);
		} catch (JSONException e) {
			// json �����쳣���񷵻��˴�������ݣ����ⷵ���Զ�����쳣�ࡣ
			throw new BaseException("", ExceptionCode.ServerResultDataError, 0);
		}

		return resultForService;
	}
	
	/**
	 * ִ�� Get ���󣬲���Ӱ�ȫ��֤�����������С�
	 * @param url
	 * @param parameter
	 * @return ������
	 * @throws BaseException 
	 * @throws java.io.IOException
	 * @throws org.apache.http.client.ClientProtocolException
	 */
	public InputStream getRequestForStream(String url,List<NameValuePair> parameter) 
			throws BaseException, ClientProtocolException, IOException	{	
		parameter = addVercode(parameter);
		filterUrlAndAppendMac(url, parameter);
		InputStream stream = RestWebService.getRequestForStream(url, parameter,HttpUtil.EncodingCharset);
		
		return stream;
	}
	
	/**
	 * ִ�� Post ���󣬲���Ӱ�ȫ��֤�����������С�
	 * @param url
	 * @param parameter
	 * @return
	 * @throws BaseException 
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService postRequest(String url,List<NameValuePair> parameter) throws ParseException, IOException, BaseException
	{
		ResultForService resultForService = null;
		
		parameter = addVercode(parameter);
		filterUrlAndAppendMac(url, parameter);
		JSONObject json = RestWebService.postRequest(url, parameter,HttpUtil.EncodingCharset);
		Log.e(getClass().getName(), "Url = " +  url +  "\n\nParam = " + parameter.toString()+ "\n\nJSON = " + json);
        try {
			resultForService = json2ResultForService(json);
			checkToken(resultForService.retCode);
			
		} catch (JSONException e) {
			// json �����쳣���񷵻��˴�������ݣ����ⷵ���Զ�����쳣�ࡣ
			new Debugger().log(e);
			throw new BaseException("",ExceptionCode.ServerResultDataError, 0);
		}
		
		return resultForService;
	}
	
	/**
	 * ִ�� Post ���󣬲���Ӱ�ȫ��֤�����������С�
	 * @param url
	 * @return
	 * @throws BaseException 
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService postRequest(String url,MultiPartEntity entity,List<NameValuePair> parameter)
			throws ParseException, IOException, BaseException
	{
		ResultForService resultForService = null;

		//���ڷ���� MutilPartPost �������ܴ�body����ȡ�����ݣ����Խ�vercode����url�ϡ�
		parameter = addVercode(parameter);
		filterUrlAndAppendMac(url, parameter);
		url = url.concat(RestWebService.toUrlParameter(parameter,HttpUtil.EncodingCharset));
		if(Parameters.debug){
			System.out.println("url:"+url);
		}
		JSONObject json = RestWebService.postRequest(url, entity);
		
		try {
			resultForService = json2ResultForService(json);
			checkToken(resultForService.retCode);
		} catch (JSONException e) {
			// json �����쳣���񷵻��˴�������ݣ����ⷵ���Զ�����쳣�ࡣ
			throw new BaseException("",ExceptionCode.ServerResultDataError, 0);
		}

		return resultForService;
	}
	
	/**
	 * ִ�� Post ���󣬲���Ӱ�ȫ��֤�����������С�
	 * @param url
	 * @return
	 * @throws BaseException 
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService postRequest(String url,MultiPartEntity entity)
			throws ParseException, IOException, BaseException
	{
		ResultForService resultForService = null;
		
		//���ڷ���� MutilPartPost �������ܴ�body����ȡ�����ݣ����Խ�vercode����url�ϡ�
		List<NameValuePair> vercode = new ArrayList<NameValuePair> ();
		vercode = addVercode(vercode);
		url = url.concat(RestWebService.toUrlParameter(vercode,HttpUtil.EncodingCharset));
		
		JSONObject json = RestWebService.postRequest(url, entity);	
		try {
			resultForService = json2ResultForService(json);
			checkToken(resultForService.retCode);
		} catch (JSONException e) {
			// json �����쳣���񷵻��˴�������ݣ����ⷵ���Զ�����쳣�ࡣ
			throw new BaseException("",ExceptionCode.ServerResultDataError, 0);
		}
		
		return resultForService;
	}
	
	/**
	 * ִ�� Delete ���󣬲���Ӱ�ȫ��֤�����������С�
	 * @param url
	 * @param parameter
	 * @return
	 * @throws BaseException 
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService deleteRequest(String url,List<NameValuePair> parameter) throws ParseException, IOException, BaseException
	{
		ResultForService resultForService = null;
		
		parameter = addVercode(parameter);
		filterUrlAndAppendMac(url, parameter);
		JSONObject json = RestWebService.deleteRequest(url, parameter,HttpUtil.EncodingCharset);
	
		try {
			resultForService = json2ResultForService(json);
			checkToken(resultForService.retCode);
		} catch (JSONException e) {
			// json �����쳣���񷵻��˴�������ݣ����ⷵ���Զ�����쳣�ࡣ
			throw new BaseException("",ExceptionCode.ServerResultDataError, 0);
		}
		
		return resultForService;
	}
	
	/**
	 * ��֤userToken�Ƿ��Ѹ�ֵ�����û�����׳� BaseException �쳣
	 * @throws BaseException
	 */
	public void UserTokenHavingIsValid () throws BaseException
	{
//		if (Parameters.user.token == null || Parameters.user.token =="")
//		{
//			BaseException e = new BaseException("Have not logged in.",ExceptionCode.HaveNotLoggedIn,0);
//			throw e;
//		}
	}
	
	/**
	 * ����http��������ԣ�������� addUsertToken Ϊ����������������userToken,termid
	 * @param addUsertToken
	 * @return
	 * @throws BaseException 
	 */
	public  List<NameValuePair> createNameValuePair(boolean addUsertToken) throws BaseException
	{
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		if (addUsertToken)
		{
			//�ж� userToken �Ƿ���ڡ�
			UserTokenHavingIsValid();
			
			nameValuePairs.add(new BasicNameValuePair("userToken", Parameters.user.token));	
			String pasmNO = "";
			if (Util.isEmpty(Parameters.swiperNo)) {//���ˢ��������Ϊ�գ�ʹ��Ǯ��id
//              pasmNO = Parameters.user.walletTerminalNO;
            }else {//ˢ�������Ų�Ϊ�գ���ʹ��ˢ��������
              pasmNO = Parameters.swiperNo;
            }
			//�����Ϊ�գ������termid���������url����ʱ����ָ���쳣�����������û�������û�������ն˺ţ�
			if (!Util.isEmpty(pasmNO)) {
				nameValuePairs.add(new BasicNameValuePair("termid", pasmNO));
			}
		}
		
		return nameValuePairs;
	}
	
	/**
	 * ��http�����б������ȫ�ֲ������÷�����ӵĲ�������:<br>
	 * ver<br>
	 * uid<br>
	 * vercode<br>
	 * debug<br>
	 * userToken<br> 
	 * mac<br>
	 * @param parameter				�����б�
	 * @param isAddLoginParameter   �Ƿ���ӵ�¼������ ���û�δ��¼��˲�����Ч��
	 * @param isAddMac              �Ƿ���� MAC ����
	 */
	public void fillGlobalTransactionParameter(
			List<NameValuePair> parameter,
			boolean isAddLoginParameter,
			boolean isAddMac){
		
		parameter = addVercode(parameter);
		
//		if (isAddLoginParameter && !Util.isEmpty(Parameters.user.token)){
//			parameter.add(new BasicNameValuePair("userToken", Parameters.user.token));	
//		}
//		
//		if (isAddMac){
//			MAC.mac(parameter);
//		}
	}
	
	/**
	 * ������б�����Ӱ�ȫ��֤����
	 * @param parameter
	 * @return
	 */
	private List<NameValuePair> addVercode(List<NameValuePair> parameter)
	{
		if (parameter == null)
		{
			parameter = new ArrayList<NameValuePair>();
		}
		
		//���Ա��
//		if (Parameters.debug)
//		{
//			parameter.add(new BasicNameValuePair("debug","1"));
//		}
		
		//App �ڲ��汾��
		parameter.add(new BasicNameValuePair("ver",Util.getVersionCode()));
//		parameter.add(new BasicNameValuePair("ver","34"));
		
//		String time = sdf.format(System.currentTimeMillis());
//		parameter.add(new BasicNameValuePair("tdtm", time));
//		parameter.add(new BasicNameValuePair("p_v", Util.getAppVersionCode()));
		
		
		if (this.vercode == null || this.vercode.length() == 0)
		{
			//����û�����ð�ȫ��֤��,�����������һЩ�������硰���쳣����
			return parameter;
		}
		
		//��Ӱ�ȫ��֤����
		parameter.add(new BasicNameValuePair("uid",this.uid));
		parameter.add(new BasicNameValuePair("vercode",this.vercode));
		System.out.println("Util.getVersionCode() = "+Util.getVersionCode()+", uid = "+this.uid);
		
		//��ӿͻ������ʶ������¼��ͻ�������С�
//		if (!Util.isEmpty(Parameters.user.custlev)){
//			parameter.add(new BasicNameValuePair("custlev",Parameters.user.custlev));
//		}
		
		return parameter;
	}
	
	private String generateUID(int length)
	{
		long t = System.nanoTime() ^ System.currentTimeMillis();
		Random random = new Random(t);
		char[] uid = new char[length];
		
		for (int i=0;i< uid.length;i++)
		{
			uid[i] =  charTable[random.nextInt(charTable.length)];
		}
		
		return String.valueOf(uid);
	}
	
	/**
	 * ��¼token�Ƿ����
	 */
	private void checkToken(String code){
		if (code.equals(Parameters.tokenOutOfDate)) {
            Parameters.user.token = "";
			if (Parameters.httpResponseAfterCheckToken){
				//������ʾToken �쳣�Ի��򣬵������ת����ҳ��
				Context context= ApplicationExtension.getInstance();
//				Intent intent=new Intent(context, TokenOutOfDateActivity.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(intent);
			}
			else{
				//��������ʾToken �쳣�Ի���ֱ�������¼��Ϣ��
				Parameters.clear();	
			}
		}
	}

	/**
	 * ����url�������Ƿ���Ҫ����mac����
	 * 20130609�������޸�
	 * ����Ǯ��ʱ������֧������ʹ��������ܣ����ڴ˽ӿڣ�activeEWallet������mac��
	 * �����Ϊ���ϵĽӿڣ�ֻ��һ����ܣ��ⲻ�����������������ӿڼ���mac����
	 * @param url
	 * @return
	 */
	private boolean shouldUseMac(String url){
		return (url.contains("queryTrans")
				||url.contains("commitTransaction")
				||url.contains("queryRemitTrans")
				||url.contains("commitRemitTransaction")
				||url.contains("updateSZBItem")
				||url.contains("activeEWallet")
				||url.contains("resetPayPwd")
				||url.contains("payPwdSwitch"));	
				
	}
	
	/**
	 * ����url���������mac�ֶ�
	 * ����putRequest�����е�addVercode����û��ʹ��parameter����������updateSZBItem��mac�����©����
	 * �˴������mac���߼�������װһ����������addVercode����������������ÿ��addVercode����֮��ʹ�ô˷���
	 * @param url
	 * @param parameter
	 */
	private void filterUrlAndAppendMac(String url,List<NameValuePair> parameter){
		if (shouldUseMac(url)) {
			
			//���ں�˶Խ���ǰ��հ汾�Ŵ���ģ�����updateSZBItem �ӿڣ�������ˢ�����Ƿ����ţ�
			//���ͳһ�÷�������,������Ҫ��ȡ mab����������н�Ԫת��Ϊ�� updateSZBItem ����
			//�������в�����ִ������ӿڣ����޸��˵���Ϣ��ˢ��֧��������֪ͨ���ֶ���ɵȲ���
			//�˽ӿ���Ҫ��һ�����ˣ�ֻ��ˢ��֧������Ҫ����mac ��֤
			if (url.contains("updateSZBItem")) {
				for (NameValuePair nameValuePair : parameter) {
					if("payType".equals(nameValuePair.getName()) && !"1".equals(nameValuePair.getValue())){
						//payType Ϊ1ʱ��Ϊˢ�������Ҫ��macУ�飬�����Ϊ 1��������ʽ����ҪmacУ��
						return;
					}
				}
			}
			
//			MAC.mac(parameter);
		}
	}
	
}
