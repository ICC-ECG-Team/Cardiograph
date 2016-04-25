package com.cardiograph.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.message.BasicNameValuePair;

import com.cardiograph.constance.Parameters;
import com.cardiograph.model.ECGData;
import com.cardiograph.util.Util;

import android.util.Log;

/**
 * ͨ�÷�����ӿ� ����: ��Ҫ�ṩ����ͨ�õĽӿ�
 * <p/>
 * 
 * <pre>
 * 		�û�ע��
 * 		�û���ͨ
 * 		�û���¼
 * 		��ȡʡ�б�
 * 		��ȡ�����б�
 * 		�ϴ����֤��Ƭ
 * 		���Ͷ���У����
 * 		У�������֤��
 *      �豸���������ϴ�
 * </pre>
 * <p/>
 * �ڵ��ýӿ�ǰ������� ���з�����ʼ�� vercode:<br>
 * generateVercode(); <br>
 * ����ʹ�� getInstance ������ȡʵ����
 * 
 * @author bob
 */
public class CommonServiceManager extends BaseServiceManager {

	private static CommonServiceManager instance = null;

	/**
	 * ���췽��˽�л���ֻ��ͨ����̬����getInstance������ȡ
	 * 
	 * @see #getInstance()
	 */
	private CommonServiceManager() {

	}

	/**
	 * ���� CommonServiceManager ��ʵ��
	 * 
	 * @return
	 * @throws java.security.NoSuchAlgorithmException
	 */
	public static synchronized CommonServiceManager getInstance() throws NoSuchAlgorithmException {

		if (instance == null) {
			instance = new CommonServiceManager();
			instance.generateVercode();
		}
		return instance;
	}

	/******************************************** �û�������� **************************************************/

	/**
	 * @param loginName
	 *            ��¼��
	 * @param password
	 *            ����
	 * @param pwdLevel
	 *            ����ȼ�
	 * @param mobileNum
	 *            �ֻ���
	 * @param realName
	 *            ��ʵ����
	 * @param email
	 *            Email
	 * @param idCardType
	 *            ���֤��ID,����֤��MILITARY_ID,ѧ��֤��STUDENT_CARD,���գ�PASSPORT,������OTHER
	 * @param idCardId
	 *            ���ѡ��֤������,������Ϊ��
	 * @param province
	 * @param city
	 * @param district
	 * @param homeAddr
	 * @param zipCode
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService register(String loginName, // ��¼��
			String password, // ����
			String pwdLevel, String mobileNum, // �ֻ���
			String realName, // ��ʵ����
			String email, // email
			String idCardType, String idCardId, String province, String city, String district, String homeAddr, String zipCode) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("register/");
		url.append(loginName);
		url.append(".json");
		// ���ò���
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("loginName", loginName));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		nameValuePairs.add(new BasicNameValuePair("mobileNum", mobileNum));
		nameValuePairs.add(new BasicNameValuePair("realName", realName));
		nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("pwdSecLevel", pwdLevel));
		nameValuePairs.add(new BasicNameValuePair("idCardType", idCardType));
		nameValuePairs.add(new BasicNameValuePair("idCardInfo.idCardId", idCardId));
		nameValuePairs.add(new BasicNameValuePair("address.province", province));
		nameValuePairs.add(new BasicNameValuePair("address.city", city));
		nameValuePairs.add(new BasicNameValuePair("address.district", district));
		nameValuePairs.add(new BasicNameValuePair("address.homeAddr", homeAddr));
		nameValuePairs.add(new BasicNameValuePair("address.zipCode", zipCode));
		nameValuePairs.add(new BasicNameValuePair("imei", Util.getIMEI()));
		nameValuePairs.add(new BasicNameValuePair("imsi", Util.getIMSI()));

		resultForService = this.postRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * �û���¼
	 * 
	 * @param loginName
	 *            ��¼����
	 * @param password
	 *            ��¼����
	 * @return ResultForService
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	@SuppressWarnings("unused")
	public ResultForService getUserLoginState(String loginName, String password, String sigVerif) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getUserLoginState/");
		url.append(loginName);
		url.append(".json");
		// ���ò���
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("loginName", loginName));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		nameValuePairs.add(new BasicNameValuePair("imei", Util.getIMEI()));
		nameValuePairs.add(new BasicNameValuePair("imsi", Util.getIMSI()));
		int versionCode = Integer.parseInt(Util.getVersionCode());
		if (!Parameters.debug && versionCode >= 13) {
			nameValuePairs.add(new BasicNameValuePair("sigVerif", sigVerif));
		}

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * �޸�����
	 * 
	 * @param loginName
	 *            ��¼��
	 * @param oldPwd
	 *            ������
	 * @param newPwd
	 *            ������
	 * @param pwdLevel
	 *            ����ȼ�
	 * @return ResultForService
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService updateUserPwd(String loginName, String password, String newPassword, String pwdSecLevel) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		// ���ò���
		List<NameValuePair> nameValuePairs = createNameValuePair(true);
		nameValuePairs.add(new BasicNameValuePair("loginName", loginName));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		nameValuePairs.add(new BasicNameValuePair("newPassword", newPassword));
		nameValuePairs.add(new BasicNameValuePair("pwdSecLevel", pwdSecLevel));

		resultForService = this.putRequest(Parameters.serviceURL.concat("updateUserPwd/" + loginName + ".json"), nameValuePairs);

		return resultForService;
	}

	// �޸��ֻ�����,��ʱ��ʵ��

	// �޸��ֻ�����??��Ҫ�޸��ֻ�����?

	/**
	 * ����û��Ƿ����״̬
	 * 
	 * @param loginName
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService getLoginNameState(String loginName) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getLoginNameState/");
		url.append(loginName);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * ���ݲ������ƻ�ȡ����ֵ
	 * 
	 * @param key
	 *            ��������
	 * @param dictCode
	 *            �����ֵ���
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService getParameter(String key, String dictCode) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getParameter.json");

		// ���ò���
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("key", key));
		if (!dictCode.equals("")) {
			nameValuePairs.add(new BasicNameValuePair("dictCode", dictCode));
		}
		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡ�û���Ϣ
	 * 
	 * @param loginName
	 * @param verifyType
	 *            ��ȡ�û���Ϣ������ (password,vercode(��֤��))
	 * @param key
	 *            �·�����ʱʹ�õĹؼ���
	 * @param userToken
	 *            verifyTypeΪ2ʱֵΪ������֤�룬verifyTypeΪ1ʱֵΪ��¼����
	 * @param token
	 *            verifyTypeΪ2ʱtokenֵΪ���ն���ʱ���ص�token
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService getUserInfo(String loginName, String verifyType, String userToken, String token) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		// �ж� userToken �Ƿ���ڡ�
		UserTokenHavingIsValid();

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getUserInfo/");
		url.append(loginName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("verifyType", verifyType));
		nameValuePairs.add(new BasicNameValuePair("token", token));
		if (verifyType.equals("1")) {
			userToken = Parameters.user.token;
		}
		nameValuePairs.add(new BasicNameValuePair("userToken", Parameters.user.token));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/*************************************** �������ֻ���ҵ�� *******************************/

	/**
	 * ��Ӳ��
	 * 
	 * @param loginName
	 *            ��¼��
	 * @param psamNo
	 *            ����
	 * @param telecomOperators
	 *            ��Ӫ�̺Ŷ� //��ֵͬΪIMSI���������ɷ������֤
	 * @param mobilePhoneModel
	 *            �ֻ���ʶ
	 * @param mobilePhoneProduct
	 *            �ֻ��ͺ�
	 * @param mobilePhoneManuFacturer
	 *            �ֻ�����
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService bindPsamCardToUser(String loginName, String psamNo, String telecomOperators, String mobilePhoneModel, String mobilePhoneProduct, String mobilePhoneManuFacturer) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		// �ж� userToken �Ƿ���ڡ�
		UserTokenHavingIsValid();

		// ���ò���
		String deviceId = Util.getIMSI();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("loginName", loginName));
		nameValuePairs.add(new BasicNameValuePair("psamNo", psamNo));
		nameValuePairs.add(new BasicNameValuePair("deviceId", deviceId));
		nameValuePairs.add(new BasicNameValuePair("telecomOperators", telecomOperators));
		nameValuePairs.add(new BasicNameValuePair("mobilePhoneModel", mobilePhoneModel));
		nameValuePairs.add(new BasicNameValuePair("mobilePhoneProduct", mobilePhoneProduct));
		nameValuePairs.add(new BasicNameValuePair("mobilePhoneManuFacturer", mobilePhoneManuFacturer));

		nameValuePairs.add(new BasicNameValuePair("userToken", Parameters.user.token));

		Log.d("yjx", Parameters.serviceURL.concat("bindPsamCardToUser.json"));
        Log.d("yjx", nameValuePairs.toString());
		resultForService = this.postRequest(Parameters.serviceURL.concat("bindPsamCardToUser.json"), nameValuePairs);
		Log.d("yjx", Parameters.serviceURL.concat("bindPsamCardToUser.json"));
        Log.d("yjx", nameValuePairs.toString());
		return resultForService;
	}

	/**
	 * ��鿨���Ƿ����
	 * 
	 * @param psamNo
	 *            ����
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService getPsamCardState(String psamNo) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getPsamCardState/");
		url.append(psamNo);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * ��ȡ���б�
	 * 
	 * @param loginName
	 *            ��¼��
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService getPsamCardBindList(String loginName) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		// �ж� userToken �Ƿ���ڡ�
		UserTokenHavingIsValid();

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("userToken", Parameters.user.token));

		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getPsamCardBindList/");
		url.append(loginName);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/*************************************** �����ӿ� ******************************************/

	/**
	 * ��ȡʡ���б�
	 * 
	 * @return ResultForService
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService getProvinceList() throws ParseException, IOException, BaseException {

		ResultForService resultForService = null;
		resultForService = this.getRequest(Parameters.serviceURL.concat("getProvinceList.json"), null);

		return resultForService;
	}

	/**
	 * ��ȡ���б�
	 * 
	 * @param provinceId
	 *            ʡ�� ID
	 * @return ResultForService
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService getCityListOfProvince(String provinceId) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getCityListOfProvince/");
		url.append(provinceId);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @param cityId
	 *            ���� ID
	 * @return ResultForService
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService getDistrictList(String cityId) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getDistrictList/");
		url.append(cityId);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * ��ȡ��������б�
	 * 
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService getNGOList() throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		resultForService = this.getRequest(Parameters.serviceURL.concat("getNGOList.json"), null);

		return resultForService;
	}

	/**
	 * ��ȡ�����Ŀ�б�
	 * 
	 * @param ngoId
	 *            ������� ID
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService getProjectListByNGO(String ngoId) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getProjectListByNGO/");
		url.append(ngoId);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * �ϴ����֤��Ƭ
	 * 
	 * @param loginName
	 *            ��¼��
	 * @param idcard1
	 *            ���֤��Ƭ�ļ�����1
	 * @param idcard2
	 *            ���֤��Ƭ�ļ�����2
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService idCardImageUpload(String loginName, byte[] idcard1, byte[] idcard2) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		MultiPartEntity mpe = new MultiPartEntity();
		mpe.addPart("idcard1", "idcard1.jpg", idcard1);
		mpe.addPart("idcard2", "idcard2.jpg", idcard2);

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("idCardImageUpload/");
		url.append(loginName);
		url.append(".json");

		resultForService = this.postRequest(url.toString(), mpe);

		return resultForService;
	}

	/**
	 * �տ��ͨ�̻��ϴ����֤��Ƭ
	 * 
	 * @param loginName
	 *            ��¼��
	 * @param idcard1
	 *            ���֤��Ƭ�ļ�����1
	 * @param idcard2
	 *            ���֤��Ƭ�ļ�����2
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService idCardImageUploadPos(String loginName, byte[] idcard1, byte[] idcard2, String userName, String cardType, String cardNo, String email) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		MultiPartEntity mpe = new MultiPartEntity();
		mpe.addPart("idcard1", "idcard1.jpg", idcard1);
		mpe.addPart("idcard2", "idcard2.jpg", idcard2);
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("idCardImageUploadPOS/");
		url.append(loginName);
		url.append(".json");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("userName", userName));
		nameValuePairs.add(new BasicNameValuePair("idCardType", cardType));
		nameValuePairs.add(new BasicNameValuePair("idCardId", cardNo));
		nameValuePairs.add(new BasicNameValuePair("email", email));
		resultForService = this.postRequest(url.toString(), mpe, nameValuePairs);

		return resultForService;
	}

	/**
	 * �ϴ���־�ļ�
	 * 
	 * @param loginName
	 *            �û���
	 * @param imei
	 *            �ֻ�imei����
	 * @param logLevel
	 *            ��־�ȼ�----
	 * @param version
	 *            ����汾��---
	 * @param desc
	 *            ��־����
	 * @param logdata
	 *            ��־�ļ�����
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService uploadErrorLog(String loginName, String imei, String logLevel, String version, String desc, String fileName, byte[] logdata) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		MultiPartEntity mpe = new MultiPartEntity();
		mpe.addPart("loginName", loginName);
		mpe.addPart("imei", imei);
		mpe.addPart("logLevel", logLevel);
		mpe.addPart("version", version);
		mpe.addPart("desc", desc);
		mpe.addPart("logdata", fileName + Util.date2() + ".txt", logdata);

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("uploadErrorLog.json");

		resultForService = this.postRequest(url.toString(), mpe);

		return resultForService;
	}

	/**
	 * @param imei
	 *            �ֻ��豸��
	 * @param infoStr
	 *            �ϴ��ַ���
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService insertStaticesInfo(String imei, String infoStr) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		MultiPartEntity mpe = new MultiPartEntity();
		mpe.addPart("imei", imei);
		mpe.addPart("data", infoStr);

		StringBuffer url = new StringBuffer();
		url.append(Parameters.statisticsURL);
		// url.append("insertStaticesInfo.json");
		url.append("android/data/");

		resultForService = this.postRequest(url.toString(), mpe);

		return resultForService;
	}

	/**
	 * ���Ͷ���У����
	 * 
	 * @param phoneNumber
	 *            �绰����
	 * @param smsType
	 *            ����ģ�� = 1����ͨ��֤�����;2�������������;
	 * @return ResultForService
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService getMobileVerifyCode(String phoneNumber, String smsType) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("smsType", smsType));

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getMobileVerifyCode/");
		url.append(phoneNumber);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), nameValuePairs);
		Log.d("yjx", url.toString());
        Log.d("yjx", nameValuePairs.toString());
		return resultForService;
	}

	/**
	 * У�������֤��
	 * 
	 * @param phoneNumber
	 *            �绰����
	 * @param token
	 *            ��֤����
	 * @param verCode
	 *            ��֤��
	 * @return ResultForService
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService getMobileVerifyState(String phoneNumber, String token, String verCode) throws ParseException, IOException, BaseException {

		ResultForService resultForService = null;
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("token", token));
		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getMobileVerifyState/");
		url.append(token);
		url.append("/");
		url.append(verCode);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * �������������кŻ�ȡ���ֻ���
	 * 
	 * @param psamNo
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService getMobileByPsamNo(String psamNo) throws ParseException, IOException, BaseException {

		ResultForService resultForService = null;
		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getMobileByPsamNo/");
		url.append(psamNo);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * �������������кŻ�ȡ���ֻ���
	 * 
	 * @param psamNo
	 *            ˢ������psam��
	 * @param deviceId
	 *            IMSI���ں�
	 * @param telecomOperators
	 *            ��Ӫ�̺Ŷ� //��ֵͬΪIMSI���������ɷ������֤
	 * @param mobilePhoneModel
	 *            �ֻ���ʶ
	 * @param mobilePhoneProduct
	 *            �ֻ��ͺ�
	 * @param mobilePhoneManuFacturer
	 *            �ֻ�����
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService checkUserDeviceInfo(String psamNo, String deviceId, String telecomOperators, String mobilePhoneModel, String mobilePhoneProduct, String mobilePhoneManuFacturer) throws ParseException, IOException, BaseException {

		ResultForService resultForService = null;
		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("checkUserDeviceInfo.json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("loginName", Parameters.user.userName));
		nameValuePairs.add(new BasicNameValuePair("psamNo", psamNo));
		nameValuePairs.add(new BasicNameValuePair("deviceId", deviceId));
		nameValuePairs.add(new BasicNameValuePair("telecomOperators", telecomOperators));
		nameValuePairs.add(new BasicNameValuePair("mobilePhoneModel", mobilePhoneModel));
		nameValuePairs.add(new BasicNameValuePair("mobilePhoneProduct", mobilePhoneProduct));
		nameValuePairs.add(new BasicNameValuePair("mobilePhoneManuFacturer", mobilePhoneManuFacturer));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡ��ֵ���
	 * 
	 * @param mobile
	 *            ��ֵ�ֻ���
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public ResultForService getRechargeAmount(String mobile) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getRechargeAmount.json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("mobile", mobile));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡ��ֵ�ֻ���ʷ��¼
	 * 
	 * @param mobile
	 *            �û���¼��
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getRechargeMobile(String mobile) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getRechargeMobile.json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("mobile", mobile));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * �޸��ֻ���ʶ
	 * 
	 * @param mobile
	 *            �û���¼��
	 * @param mobileNo
	 *            �ֻ���---�ֻ���ֵ��ʷ����ֻ���
	 * @param mobileMark
	 *            �ֻ���ʶ----�ֻ���ֵ�ı�ʶ
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService updateRechargeMobileMark(String mobile, String mobileNo, String mobileMark) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("updateRechargeMobileMark.json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
		nameValuePairs.add(new BasicNameValuePair("mobileNo", mobileNo));
		nameValuePairs.add(new BasicNameValuePair("mobileMark", mobileMark));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ɾ����ʷ��ֵ�ֻ�
	 * 
	 * @param mobile
	 *            �û���¼��
	 * @param mobileNo
	 *            �ֻ���ֵ��ʷ����ֻ���
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService deleteRechargeMobileHis(String mobile, String mobileNo) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("deleteRechargeMobileHis.json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
		nameValuePairs.add(new BasicNameValuePair("mobileNo", mobileNo));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡ��������б�
	 * 
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getBankListForRemittance() throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getBankListForRemittance.json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * ��ȡ���п���֧���б�
	 * 
	 * @param bankCode
	 *            ����code
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 * @see com.chinamobile.schebao.lakala.bll.service.CommonServiceManager#getBankListByKey(String,
	 *      String)
	 */
	public ResultForService getBankListForPay(String bankCode) throws ParseException, IOException, BaseException {
		ResultForService resultForService = getBankListForPay(bankCode, "");
		return resultForService;
	}

	/**
	 * ��ȡ���п���֧���б�
	 * 
	 * @param bankCode
	 *            ����code
	 * @param busId
	 *            ҵ��Id
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getBankListForPay(String bankCode, String busId) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getBankListForPay.json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("bankCode", bankCode));

		if (!Util.isEmpty(busId)) {
			/*
			 * ��Ϊ 2.2.0 ��ǰ�汾�������б�����ʾһ������û��ͼ�������ʱ��������ʾ����ͼ�꣬ ���Դ� 3.0.0 �濪ʼ�� busid
			 * �������"_1",�����Ͳ���Ӱ���ϰ汾�Ŀͻ��ˡ�
			 */
			busId = busId.concat("_1");
			nameValuePairs.add(new BasicNameValuePair("busId", busId));
		}

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡ�տ��������
	 * 
	 * @param keyWord
	 *            �����ؼ���
	 * @param bankCode
	 *            ����Code
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getBankListByKey(String keyWord, String bankCode) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getBankListByKey.json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("keyWord", keyWord));
		nameValuePairs.add(new BasicNameValuePair("bankCode", bankCode));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡͼƬ��֤��
	 * 
	 * @param token
	 *            �ͻ�������һ���Ựid��������ʶͼƬ��֤�����������.
	 * @return
	 * @throws org.apache.http.client.ClientProtocolException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public InputStream getVerCodeImg(String token) throws ClientProtocolException, BaseException, IOException {

		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getVerCodeImg/");
		url.append(token);
		url.append(".do");

		InputStream stream = this.getRequestForStream(url.toString(), null);

		return stream;
	}

	/**
	 * ��֤ͼƬ��֤��
	 * 
	 * @param token
	 *            �ỰId
	 * @param verCode
	 *            ��֤��
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getImgVerCodeVerifyResult(String token, String verCode) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getImgVerCodeVerifyResult/");
		url.append(token);
		url.append("/");
		url.append(verCode);
		url.append(".json");
		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * �޸ĸ�������
	 * 
	 * @param loginName
	 * @param realName
	 * @param idCardType
	 *            ���֤��ID,����֤��MILITARY_ID,ѧ��֤��STUDENT_CARD,���գ�PASSPORT,������OTHER
	 * @param idCardId
	 *            ���ѡ��֤������,������Ϊ��
	 * @param email
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService updateUserInfo(String loginName, String realName, String idCardType, String idCardId, String email) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		// �ж� userToken �Ƿ���ڡ�
		UserTokenHavingIsValid();

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("updateUserInfo/");
		url.append(loginName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("loginName", loginName));
		nameValuePairs.add(new BasicNameValuePair("realName", realName));
		nameValuePairs.add(new BasicNameValuePair("idCardType", idCardType));
		nameValuePairs.add(new BasicNameValuePair("idCardId", idCardId));
		nameValuePairs.add(new BasicNameValuePair("email", email));

		nameValuePairs.add(new BasicNameValuePair("userToken", Parameters.user.token));

		resultForService = this.putRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡ��������Ʒ�б�
	 * 
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getProductsList() throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getProductsList");
		url.append(".json");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��Ϸ�㿨���Ͳ�ѯ
	 * 
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getGameCard() throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getGameCard.json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * ��Ϸ�㿨���Ͳ�ѯ
	 * 
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getGameCardType() throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getGameCardType.json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * ��Ϸ�㿨���Ͳ�ѯ
	 * 
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getGameCardAmountByCard(String cardType) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getGameCardType.json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("cardType", cardType));
		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * �û�����Ϣ��ѯ
	 * 
	 * @param loginName
	 * @param cardId
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getUserCardRecord(String loginName, String cardId) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		// �ж� userToken �Ƿ���ڡ�
		UserTokenHavingIsValid();

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("cardId", cardId));

		nameValuePairs.add(new BasicNameValuePair("userToken", Parameters.user.token));

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getUserCardRecord/");
		url.append(loginName);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * �û�����Ϣ��ѯ
	 * 
	 * @param loginName
	 * @param cardId
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getWoYaoShouKuanUserCard() throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		// �ж� userToken �Ƿ���ڡ�
		UserTokenHavingIsValid();

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getUserCardRecord/");
		url.append("getUserCard.json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("loginName", Parameters.user.userName));
		nameValuePairs.add(new BasicNameValuePair("usage", "M50010"));
		nameValuePairs.add(new BasicNameValuePair("userToken", Parameters.user.token));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/*
	 * /** �û�����Ϣ���
	 * 
	 * @param loginName �û���¼��
	 * 
	 * @param password ��¼����
	 * 
	 * @param accountName ���п��˻���
	 * 
	 * @param bankName ������
	 * 
	 * @param cardNo ��������ʡ
	 * 
	 * @param bankCity ����
	 * 
	 * @param cardNo ����
	 * 
	 * @param bankCode ����Code
	 * 
	 * @param cityCode ��������ʡCode
	 * 
	 * @param cardType ���п����� 001����ǿ���002�����ÿ�
	 * 
	 * @param subBankFullNameCode �¼�����ȫ��Code�������б��sub_code
	 * 
	 * @param bankFullNameCode ����ȫ��Code�������б�� bank_code
	 * 
	 * @param bankFullName ����ȫ�ƣ������б��bank_name
	 * 
	 * @return
	 * 
	 * @throws ParseException
	 * 
	 * @throws IOException
	 * 
	 * @throws BaseException
	 * 
	 * public ResultForService postUserCard( String loginName, String password,
	 * String accountName, String bankName, String bankProvince, String
	 * bankCity, String cardNo, String bankCode, String cityCode, String
	 * cardType, String subBankFullNameCode, String bankFullNameCode, String
	 * bankFullName) throws ParseException, IOException, BaseException {
	 * ResultForService resultForService = null;
	 * 
	 * List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	 * nameValuePairs.add(new BasicNameValuePair("accountName", accountName));
	 * nameValuePairs.add(new BasicNameValuePair("password", password));
	 * nameValuePairs.add(new BasicNameValuePair("bankName", bankName));
	 * nameValuePairs.add(new BasicNameValuePair("bankProvince", bankProvince));
	 * nameValuePairs.add(new BasicNameValuePair("bankCity", bankCity));
	 * nameValuePairs.add(new BasicNameValuePair("cardNo", cardNo));
	 * nameValuePairs.add(new BasicNameValuePair("bankCode", bankCode));
	 * nameValuePairs.add(new BasicNameValuePair("cityCode", cityCode));
	 * nameValuePairs.add(new BasicNameValuePair("cardType", cardType));
	 * nameValuePairs.add(new BasicNameValuePair("subBankFullNameCode",
	 * subBankFullNameCode)); nameValuePairs.add(new
	 * BasicNameValuePair("bankFullNameCode", bankFullNameCode));
	 * nameValuePairs.add(new BasicNameValuePair("bankFullName", bankFullName));
	 * 
	 * StringBuffer url = new StringBuffer(); url.append(Parameters.serviceURL);
	 * url.append("postUserCard/"); url.append(loginName); url.append(".json");
	 * 
	 * resultForService = this.postRequest(url.toString(), nameValuePairs);
	 * 
	 * return resultForService; }
	 */

	/**
	 * �û�����Ϣ����
	 * 
	 * @param cardId
	 *            �� ID
	 * @param loginName
	 *            �û���¼��
	 * @param cardMemo
	 *            ���ÿ�����
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService updateUserCard(String cardId, String loginName, String cardMemo) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		// �ж� userToken �Ƿ���ڡ�
		UserTokenHavingIsValid();

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("cardMemo", cardMemo));

		nameValuePairs.add(new BasicNameValuePair("userToken", Parameters.user.token));

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("updateUserCard/");
		url.append(loginName);
		url.append("/");
		url.append(cardId);
		url.append(".json");

		resultForService = this.putRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * �û�����Ϣɾ��
	 * 
	 * @param cardId
	 *            �� ID
	 * @param loginName
	 *            �û���¼��
	 * @param password
	 *            ��¼����
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService deleteUserCard(String cardId, String loginName) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		// �ж� userToken �Ƿ���ڡ�
		UserTokenHavingIsValid();

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("userToken", Parameters.user.token));

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("deleteUserCard/");
		url.append(loginName);
		url.append("/");
		url.append(cardId);
		url.append(".json");

		resultForService = this.deleteRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ѯ���ÿ���������
	 * 
	 * @param creditcard
	 *            ���ÿ�����
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getBankInfo(String creditcard) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		// �ж� userToken �Ƿ���ڡ�
		UserTokenHavingIsValid();

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("userToken", Parameters.user.token));

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getBankInfo/");
		url.append(Util.formatString(creditcard));
		url.append(".json");

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * �û���ÿ��ת���޶���
	 * 
	 * @param amount
	 *            ���
	 * @param loginName
	 *            �û���¼��
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService checkAmountLimit(String amount, String loginName) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		// �ж� userToken �Ƿ���ڡ�
		UserTokenHavingIsValid();

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("userToken", Parameters.user.token));

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("checkAmountLimit/");
		url.append(loginName);
		url.append("/");
		url.append(amount);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * �����û���¼����
	 * 
	 * @param loginName
	 * @param password
	 *            �µ�¼����
	 * @param re_password
	 *            �µ�¼����
	 * @param pwdSecLevel
	 *            ����ȼ�
	 * @param token
	 *            ��֤���� �ɷ������·�
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService resetUserPassword(String loginName, String password, String re_password, String pwdSecLevel, String token) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("password", password));
		nameValuePairs.add(new BasicNameValuePair("re_password", re_password));
		nameValuePairs.add(new BasicNameValuePair("pwdSecLevel", pwdSecLevel));
		nameValuePairs.add(new BasicNameValuePair("token", token));

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("resetUserPassword/");
		url.append(loginName);
		url.append(".json");

		resultForService = this.putRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * �������Ͳ�ѯ
	 * 
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getTradeType() throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getTradeType.json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * ���׼�¼��ѯ
	 * 
	 * @param loginName
	 * @param startPage
	 *            ��ʼҳ
	 * @param pageSize
	 *            ÿҳ��ʾ����,Ĭ�ϣ�20��
	 * @param startTime
	 *            ��ʼʱ�� ʱ���ʽ��yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 *            ����ʱ�� ʱ���ʽ��yyyy-MM-dd HH:mm:ss
	 * @param tradeType
	 *            ��������
	 * @param psamNo
	 *            ˢ��������
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getTradeHisList(String loginName, String startPage, String pageSize, String startTime, String endTime, String tradeType, String psamNo) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		// �ж� userToken �Ƿ���ڡ�
		UserTokenHavingIsValid();

		List<NameValuePair> nameValuePairs = createNameValuePair(true);
		nameValuePairs.add(new BasicNameValuePair("startPage", startPage));
		nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize));
		nameValuePairs.add(new BasicNameValuePair("startTime", startTime));
		nameValuePairs.add(new BasicNameValuePair("endTime", endTime));
		nameValuePairs.add(new BasicNameValuePair("tradeType", tradeType));
		nameValuePairs.add(new BasicNameValuePair("psamNo", psamNo));

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getTradeHisList/");
		url.append(loginName);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡת�˻��������
	 * 
	 * @param feeId
	 *            0����ȡ���������� 1����ת�� 2������ת�� 3��ʵʱת��
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 * @type ҵ��Id false string
	 * @level ֧������ false string
	 * @channel ֧������ false string
	 */
	public ResultForService getFee(String feeId, String type, String level, String channel) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getFee/");
		url.append(feeId);
		url.append(".json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("type", type));
		nameValuePairs.add(new BasicNameValuePair("level", level));
		nameValuePairs.add(new BasicNameValuePair("channel", channel));
		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ����ʷ����¼��Ϣ��ѯ
	 * 
	 * @param loginName
	 * @param cardId
	 *            ����ϢId,���ڵ�����¼��ѯ
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getUserCardRecordForXYKHK(String loginName, String cardId) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		// �ж� userToken �Ƿ���ڡ�
		UserTokenHavingIsValid();

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getUserCardRecordForXYKHK/");
		url.append(loginName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("userToken", Parameters.user.token));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * �汾���¼��
	 * 
	 * @param version
	 *            ��ǰ�û��汾
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService checkAppUpdate(String version) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getShuakaqiList.json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("version", version));
		resultForService = this.getRequest(url.toString(), nameValuePairs);
		Log.d("yjx", url.toString());
        Log.d("yjx", nameValuePairs.toString());

		return resultForService;
	}

	// =======================================���ʱ�����=======================================

	/**
	 * ��ȡ���˱��տ��˺�
	 * 
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getSZBAccounts() throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getSZBAccounts/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ������˱��տ��˺�
	 * 
	 * @param cardNo
	 *            ���п���
	 * @param accountName
	 *            �տ�������
	 * @param bankName
	 *            ����������
	 * @param subBankFullNameCode
	 *            �����е������к�
	 * @param bankFullNameCode
	 *            �����е������к�
	 * @param bankFullName
	 *            ����ȫ��
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService addSZBAccount(String cardNo, String accountName, String bankName, String bankCode, String subBankFullNameCode, String bankFullNameCode, String bankFullName) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("addSZBAccount/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		nameValuePairs.add(new BasicNameValuePair("cardNo", Util.formatString(cardNo)));
		nameValuePairs.add(new BasicNameValuePair("accountName", accountName));
		nameValuePairs.add(new BasicNameValuePair("bankName", bankName));
		nameValuePairs.add(new BasicNameValuePair("bankCode", bankCode));
		nameValuePairs.add(new BasicNameValuePair("subBankFullNameCode", subBankFullNameCode));
		nameValuePairs.add(new BasicNameValuePair("bankFullNameCode", bankFullNameCode));
		nameValuePairs.add(new BasicNameValuePair("bankFullName", bankFullName));

		resultForService = this.postRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * �޸����˱��տ��˺�
	 * 
	 * @param cardId
	 *            �˺�ID
	 * @param cardNo
	 *            ���п���
	 * @param accountName
	 *            �տ�������
	 * @param bankName
	 *            ����������
	 * @param subBankFullNameCode
	 *            �����е������к�
	 * @param bankFullNameCode
	 *            �����е������к�
	 * @param bankFullName
	 *            ����ȫ��
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService updateSZBAccount(String cardId, String cardNo, String accountName, String bankName, String bankCode, String subBankFullNameCode, String bankFullNameCode, String bankFullName) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("updateSZBAccount/");
		url.append(Parameters.user.userName);
		url.append(".json");

		Util.log("err", "cardId   " + cardId + " cardNo " + Util.formatString(cardNo) + " accountName " + accountName + " bankName " + bankName + " bankCode " + bankCode + " subBankFullNameCode  " + subBankFullNameCode + " bankFullNameCode  " + bankFullNameCode + " bankFullName" + bankFullName);

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		nameValuePairs.add(new BasicNameValuePair("cardId", Util.formatString(cardId)));
		nameValuePairs.add(new BasicNameValuePair("cardNo", Util.formatString(cardNo)));
		nameValuePairs.add(new BasicNameValuePair("accountName", accountName));
		nameValuePairs.add(new BasicNameValuePair("bankName", bankName));
		nameValuePairs.add(new BasicNameValuePair("bankCode", bankCode));
		nameValuePairs.add(new BasicNameValuePair("subBankFullNameCode", subBankFullNameCode));
		nameValuePairs.add(new BasicNameValuePair("bankFullNameCode", bankFullNameCode));
		nameValuePairs.add(new BasicNameValuePair("bankFullName", bankFullName));

		resultForService = this.putRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡ���˱��û�ģ��
	 * 
	 * @param templateId
	 *            ģ��Id�����ڵ�����¼��ѯ
	 * @param state
	 *            ��Ŀ״̬ 0��������տ1��δ����տ�
	 * @param startPage
	 *            ��ʼҳ���ӵ�һҳ��ʼ��
	 * @param pageSize
	 *            ÿҳ������Ĭ��20��
	 * @param orderBy
	 *            ����1�����ʱ�䡢2������ʱ��
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getSZBTemplateList(String templateId, String state, String startPage, String pageSize, String orderBy) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getSZBTemplateList/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		nameValuePairs.add(new BasicNameValuePair("templateId", templateId));
		nameValuePairs.add(new BasicNameValuePair("state", state));
		nameValuePairs.add(new BasicNameValuePair("startPage", startPage));
		nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize));
		nameValuePairs.add(new BasicNameValuePair("orderBy", orderBy));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ������˱��û�ģ��
	 * 
	 * @param groupName
	 *            ��Ŀ��
	 * @param totalAmount
	 *            �ܽ�Ĭ��0
	 * @param defaultItems
	 *            Ĭ������������Ĭ��1
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService addSZBTemplate(String groupName, String totalAmount, String defaultItems) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("addSZBTemplate/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		nameValuePairs.add(new BasicNameValuePair("groupName", groupName));
		nameValuePairs.add(new BasicNameValuePair("totalAmount", totalAmount));
		nameValuePairs.add(new BasicNameValuePair("defaultItems", defaultItems));

		resultForService = this.postRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * �޸����˱��û�ģ��
	 * 
	 * @param templateId
	 *            ģ��ID
	 * @param groupName
	 *            ��Ŀ���ܽ��
	 * @param totalAmount
	 *            �ܽ�Ĭ��0
	 * @param defaultItems
	 *            Ĭ������������Ĭ��1
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService updateSZBTemplate(String templateId, String groupName, String totalAmount, String defaultItems) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("updateSZBTemplate/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		nameValuePairs.add(new BasicNameValuePair("templateId", templateId));
		nameValuePairs.add(new BasicNameValuePair("groupName", groupName));
		nameValuePairs.add(new BasicNameValuePair("totalAmount", totalAmount));
		nameValuePairs.add(new BasicNameValuePair("defaultItems", defaultItems));

		resultForService = this.putRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ɾ�����˱��û�ģ��
	 * 
	 * @param templateId
	 *            ģ��ID
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService deleteSZBTemplate(String templateId) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("deleteSZBTemplate/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		nameValuePairs.add(new BasicNameValuePair("templateId", templateId));

		resultForService = this.deleteRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡ���˱��տ���Ŀ
	 * 
	 * @param groupId
	 *            ��ĿId�����ڵ�����¼��ѯ��
	 * @param state
	 *            ��Ŀ״̬ 0��������տ1��δ����տ�
	 * @param startPage
	 *            ��ʼҳ���ӵ�һҳ��ʼ��
	 * @param pageSize
	 *            ÿҳ������Ĭ��20��
	 * @param orderBy
	 *            ����1�����ʱ�䡢2������ʱ��
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getSZBGroupList(String groupId, String state, String startPage, String pageSize, String orderBy) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getSZBGroupList/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		nameValuePairs.add(new BasicNameValuePair("groupId", groupId));
		nameValuePairs.add(new BasicNameValuePair("state", state));
		nameValuePairs.add(new BasicNameValuePair("startPage", startPage));
		nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize));
		nameValuePairs.add(new BasicNameValuePair("orderBy", orderBy));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ������˱��տ���Ŀ
	 * 
	 * @param groupName
	 *            ��Ŀ���ܽ��
	 * @param totalAmount
	 *            �ܽ�Ĭ��0
	 * @param defaultItems
	 *            Ĭ������������Ĭ��1
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService addSZBGroup(String groupName, String cardId, String totalAmount, String defaultItems) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("addSZBGroup/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		nameValuePairs.add(new BasicNameValuePair("groupName", groupName));
		nameValuePairs.add(new BasicNameValuePair("cardId", cardId));
		nameValuePairs.add(new BasicNameValuePair("totalAmount", totalAmount));
		nameValuePairs.add(new BasicNameValuePair("defaultItems", defaultItems));

		resultForService = this.postRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * �޸����˱��տ���Ŀ
	 * 
	 * @param groupId
	 *            ��Ŀ����
	 * @param cardId
	 *            �տ��˺�ID
	 * @param totalAmount
	 *            �ܽ��
	 * @param defaultItems
	 *            Ĭ����������
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService updateSZBGroup(String groupId, String cardId, String totalAmount, String defaultItems) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("updateSZBGroup/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		nameValuePairs.add(new BasicNameValuePair("groupId", groupId));
		nameValuePairs.add(new BasicNameValuePair("cardId", cardId));
		nameValuePairs.add(new BasicNameValuePair("totalAmount", totalAmount));
		nameValuePairs.add(new BasicNameValuePair("defaultItems", defaultItems));

		resultForService = this.putRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ������˱��տ�
	 * 
	 * @param groupId
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService completeSZBGroup(String groupId, ArrayList<Map<String, String>> noPayList) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("completeSZBGroup/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		nameValuePairs.add(new BasicNameValuePair("groupId", groupId));
		String prefix = "tabSzbItems[index].";
		for (int i = 0; i < noPayList.size(); i++) {
			Map<String, String> map = noPayList.get(i);
			String itemId = map.get("itemId");
			String amount = map.get("amount");
			String state = map.get("state");
			String payType = map.get("payType");
			String itemDesc = map.get("itemDesc");
			String prefixString = prefix.replaceAll("index", i + "");
			nameValuePairs.add(new BasicNameValuePair(prefixString + "itemId", itemId));
			nameValuePairs.add(new BasicNameValuePair(prefixString + "amount", amount));
			nameValuePairs.add(new BasicNameValuePair(prefixString + "state", state));
			nameValuePairs.add(new BasicNameValuePair(prefixString + "payType", payType));
			nameValuePairs.add(new BasicNameValuePair(prefixString + "itemDesc", itemDesc));
		}

		resultForService = this.putRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡ�տ��˵��б�
	 * 
	 * @param groupId
	 *            ��Ŀ����
	 * @param itemId
	 *            ��Ŀ����
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getSZBItems(String groupId, String itemId) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getSZBItems/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		nameValuePairs.add(new BasicNameValuePair("groupId", groupId));
		nameValuePairs.add(new BasicNameValuePair("itemId", itemId));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ����˵���Ϣ
	 * 
	 * @param groupId
	 *            ��Ŀ����
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService addSZBItem(String groupId, String amount) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("addSZBItem/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		nameValuePairs.add(new BasicNameValuePair("groupId", groupId));
		nameValuePairs.add(new BasicNameValuePair("amount", amount));

		resultForService = this.postRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * �޸�������Ϣ
	 * 
	 * @param groupId
	 *            ��Ŀ����
	 * @param itemId
	 *            �˵�����
	 * @param amount
	 *            ���
	 * @param state
	 *            ֧��״̬��0����֧����1��δ֧��
	 * @param payType
	 *            ֧�����ͣ�1��ˢ��֧����2������֧����9��δָ��
	 * @param termid
	 *            �ն˺ţ����֧������ѡ��"1",������ϴ��ն˺�
	 * @param itemDesc
	 *            ������Ϣ
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService updateSZBItem(String groupId, String itemId, String amount, String state, String payType, String termid, String itemDesc, String mobile) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("updateSZBItem/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		nameValuePairs.add(new BasicNameValuePair("groupId", groupId));
		nameValuePairs.add(new BasicNameValuePair("itemId", itemId));
		nameValuePairs.add(new BasicNameValuePair("amount", amount));
		nameValuePairs.add(new BasicNameValuePair("state", state));
		nameValuePairs.add(new BasicNameValuePair("payType", payType));
		nameValuePairs.add(new BasicNameValuePair("termid", termid));
		nameValuePairs.add(new BasicNameValuePair("itemDesc", itemDesc));
		nameValuePairs.add(new BasicNameValuePair("busid", "M50011"));
		nameValuePairs.add(new BasicNameValuePair("mobile", mobile));

		resultForService = this.putRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ���˱���ѯ
	 * 
	 * @param groupId
	 *            ��Ŀ����
	 * @param itemId
	 *            �˵�����
	 * @param amount
	 *            ���
	 * @param state
	 *            ֧��״̬��0����֧����1��δ֧��
	 * @param payType
	 *            ֧�����ͣ�1��ˢ��֧����2������֧����9��δָ��
	 * @param termid
	 *            �ն˺ţ����֧������ѡ��"1",������ϴ��ն˺�
	 * @param itemDesc
	 *            ������Ϣ
	 * @param tranType
	 *            ת�˷�ʽ ��ͨ�����٣�ʵʱ����
	 * @param billno
	 *            �տ�� false string �汾��>=17,����Ϊ��������
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService querySZBItemTrans(String groupId, String itemId, String amount, String state, String payType, String termid, String itemDesc, String mobile, String tranType, String billno) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("updateSZBItem/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		nameValuePairs.add(new BasicNameValuePair("groupId", groupId));
		nameValuePairs.add(new BasicNameValuePair("itemId", itemId));
		nameValuePairs.add(new BasicNameValuePair("amount", amount));
		nameValuePairs.add(new BasicNameValuePair("state", state));
		nameValuePairs.add(new BasicNameValuePair("payType", payType));
		nameValuePairs.add(new BasicNameValuePair("billno", Util.formatString(billno)));
		nameValuePairs.add(new BasicNameValuePair("itemDesc", itemDesc));
		nameValuePairs.add(new BasicNameValuePair("busid", "M50011"));
		nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
		nameValuePairs.add(new BasicNameValuePair("tranType", tranType));

		resultForService = this.putRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ɾ��������Ϣ
	 * 
	 * @param groupId
	 *            ��Ŀ����
	 * @param itemId
	 *            ��Ŀ����
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService deleteSZBItem(String groupId, String itemId) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("deleteSZBItem/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		nameValuePairs.add(new BasicNameValuePair("groupId", groupId));
		nameValuePairs.add(new BasicNameValuePair("itemId", itemId));

		resultForService = this.deleteRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ����ֻ����տ��Ƿ�ͨ
	 * 
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService checkMobilePayAcct() throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("checkMobilePayAcct/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	public ResultForService openMobilePayAcct(String accountName, String bankName, String bankFullName, String cardNo, String bankCode, String bankFullNameCode, String subBankFullNameCode) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("openMobilePayAcct/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);
		nameValuePairs.add(new BasicNameValuePair("accountName", accountName));
		nameValuePairs.add(new BasicNameValuePair("bankName", bankName));
		nameValuePairs.add(new BasicNameValuePair("bankFullName", bankFullName));
		nameValuePairs.add(new BasicNameValuePair("cardNo", cardNo));
		nameValuePairs.add(new BasicNameValuePair("bankCode", bankCode));
		nameValuePairs.add(new BasicNameValuePair("bankFullNameCode", bankFullNameCode));
		nameValuePairs.add(new BasicNameValuePair("subBankFullNameCode", subBankFullNameCode));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ����ֻ����տ��Ƿ�ͨ
	 * 
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService sendSMS(String mobile, String content) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("sendSMS/");
		url.append(mobile);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(false);
		nameValuePairs.add(new BasicNameValuePair("content", content));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡ���˱�����ģ��
	 * 
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getSZBSMSTemplate(String amount) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getSZBSMSTemplate/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);
		nameValuePairs.add(new BasicNameValuePair("amount", amount));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ɾ�����˱���Ŀ
	 * 
	 * @param groupId
	 *            ��ĿId
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService deleteSZBGroup(String groupId) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("deleteSZBGroup/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);
		nameValuePairs.add(new BasicNameValuePair("groupId", groupId));

		resultForService = this.deleteRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡ���֤��Ƭ
	 * 
	 * @param loginName
	 *            �û���
	 * @param userToken
	 *            �ͻ�������һ���Ựid��������ʶͼƬ��֤�����������.
	 * @param idCardImageName
	 *            ���֤��Ƭ����
	 * @return
	 * @throws org.apache.http.client.ClientProtocolException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public InputStream getIdcardImg(String loginName, String userToken, String idCardImageName) throws ClientProtocolException, BaseException, IOException {

		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getUserIdCardImage/");
		url.append(loginName);
		url.append(".do?");
		List<NameValuePair> nameValuePairs = createNameValuePair(true);
		nameValuePairs.add(new BasicNameValuePair("userToken", userToken));
		nameValuePairs.add(new BasicNameValuePair("idCardImageName", idCardImageName));
		InputStream stream = this.getRequestForStream(url.toString(), nameValuePairs);

		return stream;
	}

	/**
	 * ��ȡ���г����б�
	 * 
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getDictAddress() throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getDictAddress.json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * ��ȡ���˱���Ŀ�����
	 * 
	 * @param busId
	 *            ҵ��Id ���ָ�����ѯ����ҵ��������б�
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getBusMaxAmount(String busId) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getBusMaxAmount.json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);
		nameValuePairs.add(new BasicNameValuePair("busId", busId));
		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡ���ɳ����б�
	 * 
	 * @param business
	 *            ҵ������
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getCityList(String business) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getCityList/");
		url.append(business);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * ��ȡ�ɷ�������Ϣ
	 * 
	 * @param business
	 *            ҵ������
	 * @param city
	 *            �ɷѳ���
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getBizConfig(String business, String city) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getBizConfig/");
		url.append(business);
		url.append("/");
		url.append(city);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * ��ȡˢ�����б�
	 * 
	 * @param business
	 *            ҵ������
	 * @param city
	 *            �ɷѳ���
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getUnitList() throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getUnitList");
		url.append(".json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * �µĻ�ȡˢ�����б�ӿ�
	 * 
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getNewUnitList() throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getUnitListNew");
		url.append(".json");
		resultForService = this.getRequest(url.toString(), null);
		return resultForService;
	}

	/**************************************************************************
	 * �����ͼ�ӿڽ���
	 ***************************************************************************/
	/**
	 * IP��λ����
	 * 
	 * @param ip
	 *            IP��ַ
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getUserPositionByIP(String ip) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getUserPositionByIP.json");

		List<NameValuePair> nameValuePairs = createNameValuePair(false);
		nameValuePairs.add(new BasicNameValuePair("ip", ip));
		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ҳ��ʾ�û�����������������
	 * 
	 * @param swx
	 *            ���ϽǾ���
	 * @param swy
	 *            ���Ͻ�γ��
	 * @param nex
	 *            �����Ǿ���
	 * @param ney
	 *            ������γ��
	 * @param centx
	 *            ���ĵ㾭��
	 * @param centy
	 *            ���ĵ�γ��
	 * @param height
	 *            ����߶�
	 * @param scale
	 *            ������
	 * @param pageSize
	 *            ÿҳ����
	 * @param pageStart
	 *            ��ʼҳ��
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getStoreInfos(String swx, String swy, String nex, String ney, String centx, String centy, String height, String scale, String pageSize, String pageStart) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getStoreInfos.json");

		List<NameValuePair> nameValuePairs = createNameValuePair(false);
		nameValuePairs.add(new BasicNameValuePair("swx", swx));
		nameValuePairs.add(new BasicNameValuePair("swy", swy));
		nameValuePairs.add(new BasicNameValuePair("nex", nex));
		nameValuePairs.add(new BasicNameValuePair("ney", ney));
		nameValuePairs.add(new BasicNameValuePair("centx", centx));
		nameValuePairs.add(new BasicNameValuePair("centy", centy));
		nameValuePairs.add(new BasicNameValuePair("height", height));
		nameValuePairs.add(new BasicNameValuePair("scale", scale));
		nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize));
		nameValuePairs.add(new BasicNameValuePair("pageStart", pageStart));
		nameValuePairs.add(new BasicNameValuePair("phone", "phone"));
		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡ��������б�
	 * 
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getMapCityList() throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getMapCityList");
		url.append(".json");

		List<NameValuePair> nameValuePairs = createNameValuePair(false);
		nameValuePairs.add(new BasicNameValuePair("hasSotre", "true"));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡ��������
	 * 
	 * @param sno
	 *            ������
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getStoreInfo(String sno) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getStoreInfo/");
		url.append(sno);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * ��ȡ������ʾ��Ϣ
	 * 
	 * @param sno
	 *            ������
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getStoreName(String sno) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getStoreName/");
		url.append(sno);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**
	 * ��ȡ����·��ͼ
	 * 
	 * @param sno
	 *            ������
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getStoreRoute(String sno) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getStoreRoute/");
		url.append(sno);
		url.append(".json");

		resultForService = this.getRequest(url.toString(), null);

		return resultForService;
	}

	/**************************************************************************
	 * �����ͼ�ӿڽ���
	 ***************************************************************************/

	/**
	 * ��ȡҵ����ܹ�棬���ڽ���ҵ����Ϣ,����HTML��Ϣ
	 * 
	 * @param busid
	 *            ҵ�����ID
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getBusiDesc(String busId) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getBusiDesc.json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("busId", busId));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡ�˻���Ϣ
	 * 
	 * @param billNo
	 *            ���׵���
	 * @param mobile
	 *            �ֻ�����
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getAccountInfo(String billNo, String mobile) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getAccountInfo/");
		url.append(billNo);
		url.append(".json");

		// �ж� userToken �Ƿ���ڡ�
		UserTokenHavingIsValid();

		List<NameValuePair> nameValuePairs = createNameValuePair(true);
		nameValuePairs.add(new BasicNameValuePair("mobile", mobile));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * �������п��Ż�ȡ����Ϣ
	 * 
	 * @param cardNo
	 *            ���п���
	 * @return
	 * @throws BaseException
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 */
	public ResultForService getbankByCardNo(String cardNo, String bankCode) throws BaseException, ParseException, IOException {
		ResultForService resultForService = null;
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getbankByCardNo/");
		url.append(Util.formatString(cardNo));
		url.append(".json");

		// �ж� userToken �Ƿ���ڡ�
		UserTokenHavingIsValid();

		List<NameValuePair> nameValuePairs = createNameValuePair(false);
		nameValuePairs.add(new BasicNameValuePair("bankCode_tl", bankCode));
		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**************************************************************************
	 * �˵����ڽӿڿ�ʼ
	 ***************************************************************************/

	/**
	 * ��ȡ�˵�����֧�ֵ������б�
	 * 
	 * @param bankcode
	 *            ����code
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService getZDFQCredits(String bankcode) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getZDFQCredits.json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("bankcode", bankcode));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ��ȡ�������˵�������ʷ��¼
	 * 
	 * @param startPage
	 *            ��ʼҳ----��ʼҳ1�����������ȡ��������
	 * @param pageSize
	 *            ÿҳ���� ----Ĭ��20��
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */

	public ResultForService getZDFQRecords(String startPage, String pageSize) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		// �ж� userToken �Ƿ���ڡ�
		UserTokenHavingIsValid();

		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("getZDFQRecords/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("userToken", Parameters.user.token));
		nameValuePairs.add(new BasicNameValuePair("startPage", startPage));
		nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize));

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ɾ��������ʷ��¼
	 * 
	 * @param recordId
	 *            ��¼����
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService deleteZDFQRecord(String recordId) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		// �ж� userToken �Ƿ���ڡ�
		UserTokenHavingIsValid();
		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("deleteZDFQRecord/");
		url.append(Parameters.user.userName);
		url.append(".json");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("userToken", Parameters.user.token));
		nameValuePairs.add(new BasicNameValuePair("recordId", recordId));

		resultForService = this.deleteRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**
	 * ���ڷ��ʲ�ѯ
	 * 
	 * @param billno
	 *            �˵���
	 * @param mobileno
	 *            ֪ͨ�ֻ�����
	 * @param bankcode
	 *            ���д���
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService queryTrans(String billno, String mobileno, String bankcode) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		// ���ò���
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("queryTrans.json");

		List<NameValuePair> nameValuePairs = createNameValuePair(true);
		nameValuePairs.add(new BasicNameValuePair("busid", "16L"));
		nameValuePairs.add(new BasicNameValuePair("mobile", Parameters.user.userName));
		nameValuePairs.add(new BasicNameValuePair("billno", billno));
		nameValuePairs.add(new BasicNameValuePair("mobileno", mobileno));
		nameValuePairs.add(new BasicNameValuePair("bankcode", bankcode));

		resultForService = this.postRequest(url.toString(), nameValuePairs);

		return resultForService;
	}

	/**************************************************************************
	 * �˵����ڽӿڽ���
	 ***************************************************************************/

	/**
	 * ��ȡ�ֻ�ˢ������Ʒid
	 * 
	 * @return
	 * 
	 * @throws org.apache.http.ParseException
	 * 
	 * @throws java.io.IOException
	 * 
	 * @throws BaseException
	 */
	public ResultForService getShuaKaQiProductId() throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		StringBuffer url = new StringBuffer();
		url.append(Parameters.bianlitehuiServiceUrl);
		url.append("getValue");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("key", "lklskq.productid"));
		resultForService = this.getRequest(url.toString(), nameValuePairs);
		return resultForService;
	}

	/**
	 * �ϴ���־�ļ�
	 * 
	 * @param loginName
	 *            �û���
	 * @param imei
	 *            �ֻ�imei����
	 * @param logLevel
	 *            ��־�ȼ�----
	 * @param version
	 *            ����汾��---
	 * @param desc
	 *            ��־����
	 * @param logdata
	 *            ��־�ļ�����
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws BaseException
	 */
	public ResultForService uploadStatisticsLog(String imei, String fileName, byte[] logdata) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		MultiPartEntity mpe = new MultiPartEntity();
		mpe.addPart("imei", imei);
		mpe.addPart("ip", "");
		mpe.addPart("logdata", fileName + ".txt", logdata);

		StringBuffer url = new StringBuffer();
		url.append(Parameters.statisticsURL);
		// url.append("uploadCsvFile.json");
		url.append("android/file/");

		resultForService = this.postRequest(url.toString(), mpe);

		return resultForService;
	}

	/**
	 * ��ȡ�ֻ��ͻ���Splashҳ��
	 * 
	 * @param resulation
	 *            �ֻ���Ļ�ֱ��ʣ�������Ļ�ܶȣ�ָ��һ����Ӧ�ķֱ���
	 * @return
	 * @throws BaseException
	 * @throws IOException
	 */
	public ResultForService getMobilehomePic(String resulation) throws BaseException, IOException {

		// ���ò���
		List<NameValuePair> nameValuePairs = createNameValuePair(false);
		nameValuePairs.add(new BasicNameValuePair("resulation", resulation));

		ResultForService resultForService = this.getRequest(Parameters.serviceURL.concat("getMobilehomePic.json"), nameValuePairs);

		return resultForService;
	}
	
	/**
	 * �豸���������ϴ�
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @param seid
	 *            ��ȫ��ID
	 * @param imsi
	 *            �ƶ��û�ʶ����
	 * @return ResultForService
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService uploadDeviceData(String mobile, String seid, String imsi) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
		nameValuePairs.add(new BasicNameValuePair("SEID", seid));
		nameValuePairs.add(new BasicNameValuePair("IMSI", imsi));
		
		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("NFCActive.json");

		resultForService = this.getRequest(url.toString(), nameValuePairs);

		return resultForService;
	}
	
	/**
	 * �ֻ���У��
	 * 
	 * @param mobile
	 *            �ֻ���
	 * @return ResultForService
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService isChinaMobile(String mobile) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
		
		resultForService = this.getRequest(
				Parameters.serviceURL.concat("isChinaMobile.json"), 
				nameValuePairs);
		Log.d("yjx", Parameters.serviceURL.concat("isChinaMobile.json"));
        Log.d("yjx", nameValuePairs.toString());
		return resultForService;
	}
	
	/**
	 * �ϴ��ĵ�����
	 * 
	 * @param loginName
	 *            �û���
	 * @param imei
	 *            �ֻ�imei����
	 * @param logLevel
	 *            ��־�ȼ�----
	 * @param version
	 *            ����汾��---
	 * @param desc
	 *            ��־����
	 * @param logdata
	 *            ��־�ļ�����
	 * @return
	 * @throws org.apache.http.ParseException
	 * @throws java.io.IOException
	 * @throws BaseException
	 */
	public ResultForService uploadECGData(String loginName, String imei, String logLevel, String version, String desc, List<ECGData> lstData) throws ParseException, IOException, BaseException {
		ResultForService resultForService = null;
		MultiPartEntity mpe = new MultiPartEntity();
		mpe.addPart("loginName", loginName);
		mpe.addPart("imei", imei);
		mpe.addPart("logLevel", logLevel);
		mpe.addPart("version", version);
		mpe.addPart("desc", desc);
//		mpe.addPart("logdata", fileName + Util.date2() + ".txt", logdata);

		StringBuffer url = new StringBuffer();
		url.append(Parameters.serviceURL);
		url.append("uploadErrorLog.json");

		resultForService = this.postRequest(url.toString(), mpe);

		return resultForService;
	}
}
