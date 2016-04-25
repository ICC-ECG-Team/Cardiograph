package com.cardiograph.util;

import com.cardiograph.commom.ApplicationExtension;
import com.cardiograph.log.Debugger;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * �绰��ش���(�Ƿ���Ҫ��PhoneNumberInputWatcherŪ����)
 * 
 * @author jack
 * 
 */
public class PhoneUtils {

	public static final String SCHEME_TEL = "tel:";
	private static final String CMCC_ISP = "46000";// �й��ƶ�
	private static final String CMCC2_ISP = "46002"; // �й��ƶ�
	private static final String CU_ISP = "46001"; // �й���ͨ
	private static final String CT_ISP = "46003"; // �й�����

    private static TelephonyManager telephonyManager;

    static {
        telephonyManager = (TelephonyManager) ApplicationExtension
				.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
    }

	/**
	 * ��������
	 */
	public enum NetWorkType {
		NONE(""),
		WIFI("2"), 
		MOBILE("1");
		
		String type;
		
		NetWorkType(String type) {
			this.type = type;
		}
		
		@Override
		public String toString() {
			return type;
		}
	}

	/**
	 * ����绰
	 * 
	 * @param context
	 * @param phoneNumber
	 *            �绰����
	 */
	public static void callPhone(final Context context, final String phoneNumber) {
		try {
			Uri uri = Uri.parse(SCHEME_TEL + phoneNumber);
			Intent intent = new Intent(Intent.ACTION_DIAL, uri);
			context.startActivity(intent);
		} catch (Exception e) {
			new Debugger().log(e);
		}
	}

	/**
	 * ��ȡ�ֻ�imei����
	 * 
	 * @return
	 */
	public static String getIMEI() {
		TelephonyManager manager = (TelephonyManager) ApplicationExtension
				.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
		String IMEI = manager.getDeviceId();
		IMEI = Util.trim(IMEI);
		return IMEI;
	}

	/**
	 * ��ȡIMSI��
	 * 
	 * @return
	 */
	public static String getIMSI() {
		TelephonyManager manager = (TelephonyManager) ApplicationExtension
				.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = manager.getSubscriberId();
		if (imsi == null) {
			imsi = "eeeeeeeeeeeeeee";
		}
		return imsi;
	}

	/**
	 * ��ȡ�ֻ�������Ӫ������
	 * 
	 * @return
	 */
	public static String getPhoneISP() {
		TelephonyManager manager = (TelephonyManager) ApplicationExtension
				.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
		String teleCompany = "";
		/*
		 * MCC+MNC(mobile country code + mobile network code) ע�⣺�����û���������ע��ʱ��Ч��
		 * ��CDMA�����н��Ҳ���ɿ���
		 */
		String np = manager.getNetworkOperator();// String
		if (np != null) {
			if (np.startsWith(CMCC_ISP) || np.startsWith(CMCC2_ISP)) {// ��Ϊ�ƶ�������46000�µ�IMSI�Ѿ����꣬����������һ��46002��ţ�134/159�Ŷ�ʹ���˴˱��
				// �й��ƶ�
				teleCompany = "y";
			} else if (np.startsWith(CU_ISP)) {
				// �й���ͨ
				teleCompany = "l";
			} else if (np.startsWith(CT_ISP)) {
				// �й�����
				teleCompany = "d";
			}
		}
		teleCompany = Util.trim(teleCompany);
		return teleCompany;
	}

	/**
	 * ��ȡ�ֻ���ʶ eg:ME860
	 * 
	 * @return
	 */
	public static String getPhoneModel() {
		String deviceModel = "";
		// ME860
		deviceModel = Build.MODEL;
		deviceModel = Util.trim(deviceModel);
		return deviceModel;
	}

	/**
	 * ��ȡ�ֻ��ͺ� eg:ME860_HKTW
	 * 
	 * @return
	 */
	public static String getPhoneType() {
		String phoneType = "";
		// eg:ME860_HKTW
		phoneType = Build.PRODUCT;
		phoneType = Util.trim(phoneType);
		return phoneType;
	}

	/**
	 * ��ȡ�ֻ����� eg:motorola
	 * 
	 * @return
	 */
	public static String getPhonePhoneManuFacturer() {
		String phoneManufactuer = "";
		// eg:motorola
		phoneManufactuer = Build.MANUFACTURER;
		phoneManufactuer = Util.trim(phoneManufactuer);
		return phoneManufactuer;
	}

	/**
	 * �����Ƿ����
	 * 
	 * @return true���� </br> false������
	 */
	public static boolean isNetworkAvailable() {
		ConnectivityManager connectivity = (ConnectivityManager) ApplicationExtension
				.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null && info.isAvailable()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ���ص�ǰ�ֻ��������������,
	 * 
	 * @return ����ֵ: 1.����mobile(2G3G), 2����wifi
	 */
	public static String getNetworkStat() {
		NetWorkType netType = NetWorkType.NONE;
		try {
			ConnectivityManager connMgr = (ConnectivityManager) ApplicationExtension
					.getInstance().getSystemService(
							Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
			if (activeInfo != null && activeInfo.isConnected()) {
				if (activeInfo.getType() == ConnectivityManager.TYPE_WIFI) {
					netType = NetWorkType.WIFI;
				} else if (activeInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
					netType = NetWorkType.MOBILE;
				}
			}
		} catch (Exception e) {
			new Debugger().log(e);
		}
		return netType.toString();
	}

	/**
	 * ���gps�Ƿ����
	 * 
	 * @return
	 */
	public static boolean isGpsAvaiable() {
		LocationManager locationManager = (LocationManager) ApplicationExtension
				.getInstance().getSystemService(Context.LOCATION_SERVICE);
		boolean isEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		return isEnabled;
	}

	/**
	 * ��gps�͹ر�gps
	 * 
	 * @param context
	 */
	public static void autoGps(Context context) {
		try {
			Intent GPSIntent = new Intent();// �����Զ���gps
			GPSIntent.setClassName("com.android.settings",
					"com.android.settings.widget.SettingsAppWidgetProvider");
			GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
			GPSIntent.setData(Uri.parse("custom:3"));
			PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
		} catch (CanceledException e) {
			new Debugger().log(e);
		}
	}

	/**
	 * ��ȡ�ֻ�ϵͳ�汾
	 * 
	 * @return
	 */
	public static String getPhoneOSVersion() {
		String osVersion = "";
		osVersion = String.valueOf(Build.VERSION.SDK_INT);
		osVersion = Util.trim(osVersion);
		return osVersion;
	}

    /**
     * ��ȡ�����ֻ�����
     * @return ���ر������룬����޷���ȡ�ֻ��ţ��򷵻�""�մ�
     */
    public static String getPhoneNumber(){
        String phoneNumber = "";
        phoneNumber = telephonyManager.getLine1Number();
        phoneNumber = phoneNumber == null ? "" : phoneNumber;
        return phoneNumber;
    }


    /**
     * ��ȡsim����
     * @return ����sim���ţ�����޷���ȡ������""�մ�
     */
    public static String getSimSerialNumber(){
        String simSerialNumber = "";
        simSerialNumber = telephonyManager.getSimSerialNumber();
        simSerialNumber = simSerialNumber == null ? "" : simSerialNumber;
        return simSerialNumber;
    }
    
    /**
     * ��ȡsim����
     * @return ����sim���ţ�����޷���ȡ������""�մ�
     */
    public static String getSimOperatorName(){
        String simOperatorName = "";
        simOperatorName = telephonyManager.getSimOperatorName();
        simOperatorName = simOperatorName == null ? "" : simOperatorName;
        return simOperatorName;
    }
}
