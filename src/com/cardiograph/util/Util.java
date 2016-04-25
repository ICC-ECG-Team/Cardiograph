package com.cardiograph.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cardiograph.commom.ApplicationExtension;
import com.cardiograph.constance.Parameters;
import com.example.cardiograph.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

/**
 * ͨ�ù�����
 * 
 * <br>
 * 1.���ڸ�ʽ��</br> 2.���֣��������ʾ��������ʾ����</br> 3.���ַ���ʽ������ֵ���㣬�����С����</br>
 * 4.��λ��ת��-----���ֵ�λ����λ</br> 5.У���ַ��Ϸ���</br> 6.����������Ϣ</br> 7.�豸Ӳ����Ϣ��������Ϣ</br>
 * 
 * @author xyz
 */
public class Util {
	private static Toast toast = null;
//	public static String filename = "filegouwuche";
//
//	/** ��� ���ʱ�� */
//	private static long lastClickTime;
//	private static ArrayList<View> views = new ArrayList<View>();
//
//	private static SimpleDateFormat dateForamt = new SimpleDateFormat();
//
//	/**
//	 * �Ƿ��ظ���� ��������ǰ���Ƿ�ͬһ��view�������ʵ�����timeD���ʱ��
//	 * 
//	 * @return
//	 */
//	public static boolean isFastDoubleClick() {
//		long time = System.currentTimeMillis();
//		long timeD = time - lastClickTime;
//		if (0 < timeD && timeD < 800) {
//			return true;
//		}
//		lastClickTime = time;
//		return false;
//	}
//
//	/**
//	 * �Ƿ��ظ����
//	 * 
//	 * @view �����view�����ǰ����ͬһ��view�������˫��У��
//	 * @return
//	 */
//	public static boolean isFastDoubleClick(View view) {
//		long time = System.currentTimeMillis();
//		long timeD = time - lastClickTime;
//		if (views.size() == 0) {
//			views.add(view);
//		}
//		if (0 < timeD && timeD < 800 && views.get(0).getId() == view.getId()) {
//			return true;
//		}
//		lastClickTime = time;
//		views.clear();
//		views.add(view);
//		return false;
//	}
//
//	/******************************************* ���ڸ�ʽ��------��ʼ ********************************************/
//
//	/**
//	 * ���������ַ�������ʽΪ��yyyy-MM-dd HH:mm:ss
//	 * 
//	 * @deprecated ʹ��DateUtil���еķ���
//	 * @return ��ʽ����������ַ���
//	 */
//	public static String date() {
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String date = formatter.format(new Date());
//		return date;
//	}

	/**
	 * ���������ַ�������ʽΪ��yyyy_MM_dd_hh_mm_ss
	 * 
	 * @deprecated ʹ��DateUtil���еķ���
	 * @return ��ʽ����������ַ���
	 */
	public static String date2() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_MM_ss");
		String date = formatter.format(new Date());
		return date;
	}

//	/**
//	 * ���������ַ�������ʽΪ��yyyy-MM-dd HH:mm:ss
//	 * 
//	 * @deprecated ʹ��DateUtil���еķ���
//	 * @return ��ʽ����������ַ���
//	 */
//	public static String date(Date d) {
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String date = formatter.format(d);
//		return date;
//	}
//
//	/**
//	 * ���������ַ�������ʽΪ��yyyy-MM-dd HH:mm:ss
//	 * 
//	 * @deprecated ʹ��DateUtil���еķ���
//	 * @return ��ʽ����������ַ���
//	 */
//	public static String date2(Date d) {
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy��MM��dd��");
//		String date = formatter.format(d);
//		return date;
//	}
//
//	/**
//	 * ���������ַ�������ʽΪ��yyyy��MM��dd HH:mm
//	 * 
//	 * @deprecated ʹ��DateUtil���еķ���
//	 * @param dateString
//	 *            �����ַ������������ַ�����
//	 * @return ��ʽ����������ַ���
//	 */
//	public static String date(String dateString) {
//		long timeString = Long.parseLong(dateString);
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
//				"yyyy��MM��dd�� HH:mm:ss");
//		Calendar c = Calendar.getInstance();
//		c.setTimeInMillis(timeString);
//		String date = simpleDateFormat.format(c.getTime());
//		return date;
//	}
//
//	/**
//	 * ���������ַ�������ʽΪ��MMdd
//	 * 
//	 * @deprecated ʹ��DateUtil���еķ���
//	 * @param dateString
//	 *            �����ַ������������ַ�����
//	 * @return ��ʽ����������ַ���
//	 */
//	public static String formatSignDate(String dateString) {
//		long timeString = Long.parseLong(dateString);
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd");
//		Calendar c = Calendar.getInstance();
//		c.setTimeInMillis(timeString);
//		String date = simpleDateFormat.format(c.getTime());
//		return date;
//	}
//
//	/**
//	 * ���������ַ�������ʽΪ��yyyy��MM��dd HH:mm
//	 * 
//	 * @deprecated ʹ��DateUtil���еķ���
//	 * @param dateString
//	 *            �����ַ������������ַ�����
//	 * @param pattern
//	 *            ��ʽ����ʽ �磺yyyy-MM-dd HH:mm ��yyyy MM dd HH:MM ,yyyy-MM-dd
//	 * @return ��ʽ����������ַ���
//	 */
//	public static String date(String dateString, String pattern) {
//		long timeString = Long.parseLong(dateString);
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//		Calendar c = Calendar.getInstance();
//		c.setTimeInMillis(timeString);
//		String date = simpleDateFormat.format(c.getTime());
//		return date;
//	}
//
//	/**
//	 * ��cst��ʽ������ת��Ϊ�����ʽʱ��
//	 * 
//	 * @param CSTDateString
//	 *            cst��ʽ��ʱ�� ���磺Tue May 21 14:32:31 CST 2013
//	 * @return
//	 */
//	public static String dateString(String CSTDateString) {
//		if (!CSTDateString.contains("CST")) {
//			return CSTDateString;
//		}
//		CSTDateString = CSTDateString.replace("Jan", "01");
//		CSTDateString = CSTDateString.replace("Feb", "02");
//		CSTDateString = CSTDateString.replace("Mar", "03");
//		CSTDateString = CSTDateString.replace("Apr", "04");
//		CSTDateString = CSTDateString.replace("May", "05");
//		CSTDateString = CSTDateString.replace("Jun", "06");
//		CSTDateString = CSTDateString.replace("Jul", "07");
//		CSTDateString = CSTDateString.replace("Aug", "08");
//		CSTDateString = CSTDateString.replace("Sep", "09");
//		CSTDateString = CSTDateString.replace("Oct", "10");
//		CSTDateString = CSTDateString.replace("Nov", "11");
//		CSTDateString = CSTDateString.replace("Dec", "12");
//		CSTDateString = CSTDateString.replace("Mon", "");
//		CSTDateString = CSTDateString.replace("Tues", "");
//		CSTDateString = CSTDateString.replace("Wed", "");
//		CSTDateString = CSTDateString.replace("Thur", "");
//		CSTDateString = CSTDateString.replace("Fri", "");
//		CSTDateString = CSTDateString.replace("Sat", "");
//		CSTDateString = CSTDateString.replace("Sun", "");
//
//		SimpleDateFormat sdf = new SimpleDateFormat(
//				"EE MM dd HH:mm:ss 'CST' yyyy", Locale.US);
//		Date date = null;
//		String dateString2 = "";
//		try {
//			date = sdf.parse(CSTDateString);
//			dateString2 = date.getTime() + "";
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return dateString2;
//	}
//
//	/**
//	 * ���������ַ�������ʽΪ��yyyy-MM-dd HH:mm
//	 * 
//	 * @deprecated ʹ��DateUtil���еķ���
//	 * @param dateString
//	 *            �����ַ������������ַ�����
//	 * @return ��ʽ����������ַ���
//	 */
//	public static String date4(String dateString) {
//		long timeString = Long.parseLong(dateString);
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
//				"yyyy-MM-dd  HH:mm");
//		Calendar c = Calendar.getInstance();
//		c.setTimeInMillis(timeString);
//		String date = simpleDateFormat.format(c.getTime());
//		return date;
//	}
//
//	/**
//	 * ���������ַ�������ʽΪ��yyyy��MM��dd��
//	 * 
//	 * @deprecated ʹ��DateUtil���еķ���
//	 * @param dateString
//	 *            �����ַ������������ַ�����
//	 * @return ��ʽ����������ַ���
//	 */
//	public static String date3(String dateString) {
//		long timeString = Long.parseLong(dateString);
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy��MM��dd��");
//		Calendar c = Calendar.getInstance();
//		c.setTimeInMillis(timeString);
//		String date = simpleDateFormat.format(c.getTime());
//		return date;
//	}
//
//	/**
//	 * ���ط����ַ�������ʽΪ��06,00
//	 * 
//	 * @param time
//	 *            �����ַ������������ַ�����
//	 * @return ��ʽ�����ʱ���ַ���
//	 */
//	public static String timeFormat(int time) {
//		String pattern = "00";
//		DecimalFormat df = new DecimalFormat(pattern);
//		return df.format(time);
//	}
//
//	/**
//	 * ���������ַ�������ʽΪ20120130
//	 * 
//	 * @param date
//	 *            ����
//	 * @return ��ʽ����������ַ���
//	 */
//	public static String dateFormatNum(String date) {
//		String dateString = "";
//		String[] temp = date.split("-");
//		dateString = temp[0] + dateNum(temp[1]) + dateNum(temp[2]);
//		return dateString;
//	}
//
//	/**
//	 * �ж����ڻ��·��Ƿ�Ϊ��λ������Ǹ�λ���㡣
//	 * 
//	 * @param num
//	 *            ���ڻ��·�ֵ
//	 * @return ��ʽ����������ַ���
//	 * */
//	private static String dateNum(String num) {
//		String date = "";
//		if (num.length() == 1) {
//			date = "0" + num;
//		} else {
//			date = num;
//		}
//		return date;
//	}
//
//	/**
//	 * ���������ַ�������ʽΪ01/30
//	 * 
//	 * @param date
//	 *            ����
//	 * @return ��ʽ����������ַ���
//	 */
//	public static String dateFormatMonthDay(String time) {
//		String dateString = "";
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			Date date = sdf.parse(time);
//			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
//
//			String strDate = sdf.format(sqlDate);
//			String[] dateshuzu = strDate.split("-");
//			dateString = dateshuzu[1] + "/" + dateshuzu[2];
//		} catch (Exception e) {
//			new Debugger().log(e);
//		}
//		return dateString;
//	}
//
//	/**
//	 * ����ʱ���ʽ hostDate:20130515 hostTime:11:33:00
//	 * 
//	 * @param hostDate
//	 * @param hostTime
//	 * @return ��ʽ��ʱ���ʽ yyyy-MM-dd HH:mm:ss
//	 */
//	public static String formatData(String hostDate, String hostTime) {
//		if (hostDate == null || hostTime == null) {
//			return "";
//		}
//		try {
//			int year = Integer.parseInt(hostDate.substring(0, 4));
//			int month = Integer.parseInt(hostDate.substring(4, 6));
//			int day = Integer.parseInt(hostDate.substring(6));
//			int hourOfDay = Integer.parseInt(hostTime.substring(0, 2));
//			int minute = Integer.parseInt(hostTime.substring(2, 4));
//			int second = Integer.parseInt(hostTime.substring(4));
//			Calendar calendar = Calendar.getInstance();
//			calendar.set(year, month - 1, day, hourOfDay, minute, second);
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
//					"yyyy-MM-dd  HH:mm:ss");
//			return simpleDateFormat.format(calendar.getTime());
//		} catch (NumberFormatException e) {
//			new Debugger().log(e);
//		}
//		return "";
//	}
//
//	/**
//	 * ����ָ����ʽ����ʽϵͳʱ��
//	 * 
//	 * @deprecated ʹ��DateUtil���еķ���
//	 * @param pattern
//	 *            ��ʽ(yyMMdd HH:mm:ss ....)
//	 * @return
//	 */
//	public static String formateDate(String pattern) {
//		dateForamt.applyPattern(pattern);
//		String date = dateForamt.format(new Date());
//		return date;
//	}
//
//	/**
//	 * Ǯ������ʹ�õ�ʱ���ʽ
//	 * 
//	 * @return
//	 */
//	public static String dateForWallet() {
//		String date = formateDate("yyMMddHHmmss");
//		return date;
//	}
//
//	/**
//	 * ʱ���ַ�����һ�ָ�ʽת������һ�ָ�ʽ
//	 * 
//	 * @deprecated ʹ��DateUtil���еķ���
//	 * @param date
//	 *            20130516
//	 * @param pattern
//	 *            yyyyMMdd
//	 * @param targetPattern
//	 *            yyyy-MM-dd (2013-05-16)
//	 * @return
//	 */
//	public static String formatDateStrToPattern(String date, String pattern,
//			String targetPattern) {
//		dateForamt.applyPattern(pattern);
//		Date d;
//		try {
//			d = dateForamt.parse(date);
//			dateForamt.applyPattern(targetPattern);
//			return dateForamt.format(d);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return date;
//	}
//
//	/****************************************** ���ڸ�ʽ��---���� ***************************************/
//
//	/********************** ���֣��������ʾ��������ʾ���� ------��ʼ ******************************/
//
	/**
	 * Toast��Ϣ��ʾ
	 * 
	 * @param context
	 *            ������
	 * @param strId
	 *            �ַ�Id
	 */
	public static void toast(Context context, int strId) {
//		Context context = ApplicationExtension.getInstance()
//				.getApplicationContext();
		getToast(context, context.getString(strId), Toast.LENGTH_LONG).show();

	}

	/**
	 * Toast��Ϣ��ʾ ����
	 * 
	 * @param context
	 *            ������
	 * @param strId
	 *            �ַ�Id
	 */
	public static void toastCenter(Context context, int strId) {
//		Context context = ApplicationExtension.getInstance()
//				.getApplicationContext();
		Toast toast = getToast(context, context.getString(strId), Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static Toast getToast(Context context, String text, int showTime) {
//		Context context = ApplicationExtension.getInstance()
//				.getApplicationContext();
		// toast = Toast.makeText(context, text, showTime);
		if (toast == null) {
			toast = Toast.makeText(context, text, showTime);
		} else {
			toast.setDuration(showTime);
			toast.setText(text);
		}

		return toast;
	}

	/**
	 * Toast��Ϣ��ʾ
	 * 
	 * @param context
	 *            ������
	 * @param strId
	 *            �ַ�Id
	 */
	public static void toast(Context context, int strId, boolean isShowShortTime) {
//		Context context = ApplicationExtension.getInstance()
//				.getApplicationContext();
		int showTime = 0;
		if (isShowShortTime) {
			showTime = Toast.LENGTH_SHORT;
		} else {
			showTime = Toast.LENGTH_LONG;
		}
		getToast(context, context.getString(strId), showTime).show();
	}

	/**
	 * Toast��Ϣ��ʾ
	 * 
	 * @param str
	 *            ��ʾ��Ϣ
	 */
	public static void toast(Context context, String str) {
		if (str != null && !"��Ч��Token".equals(str)) {
			getToast(context, str, Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Toast��Ϣ��ʾ ����
	 * 
	 * @param str
	 *            ��ʾ��Ϣ
	 */
	public static void toastCenter(Context context, String str) {
		if (str != null && !"��Ч��Token".equals(str)) {
			Toast toast = getToast(context, str, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

	/**
	 * Toast��Ϣ��ʾ
	 * 
	 * @param str
	 *            ��ʾ��Ϣ
	 * @param duration
	 *            ��Ϣ�ӳ�ʱ��
	 */
	public static void toast(Context context, String str, int duration) {
		if (str != null) {
			getToast(context, str, duration).show();
		}
	}

	/**
	 * log��־��ӡ
	 * 
	 * @param tag
	 * @param msg
	 *            ��ӡ��Ϣ
	 */
	public static void log(String tag, String msg) {
		if (Parameters.debug)
			Log.d(tag, msg);
	}
//
//	/**
//	 * log��־��ӡ,ͳһʹ����ͬ��tag
//	 * 
//	 * @param msg
//	 *            ��ӡ��Ϣ
//	 */
//	public static void log(String msg) {
//		if (Parameters.debug)
//			Log.d(Parameters.TAG, msg);
//	}
//
//	/**
//	 * ���ؼ��� ����������½ǰ�ťʱ���ؼ���
//	 * 
//	 * */
//	public static void hideKeyboard(View view) {
//		InputMethodManager imm = (InputMethodManager) ApplicationExtension
//				.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
//		imm.hideSoftInputFromWindow(view.getWindowToken(),
//				InputMethodManager.HIDE_NOT_ALWAYS);
//	}
//
//	/**
//	 * ��ʾ���� ����������½ǰ�ťʱ���ؼ���
//	 * 
//	 * */
//	public static void showKeyboard(View view) {
//		InputMethodManager imm = (InputMethodManager) ApplicationExtension
//				.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
//		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
//	}
//
//	/******************************** ���֣��������ʾ����������ʾ����-----���� ******************************************/
//
//	// /**
//	// * ʹicon���ʱ��������˸Ч��
//	// *
//	// * @param view
//	// * �������view
//	// * @param event
//	// * �¼�
//	// */
//	// public static void makeIconBlink(View view, MotionEvent event) {
//	// if (view == null || event == null) {
//	// return;
//	// }
//	// if (event.getAction() == MotionEvent.ACTION_DOWN) {
//	// view.getBackground().setAlpha(100);
//	// view.invalidate();
//	// } else if (event.getAction() == MotionEvent.ACTION_UP
//	// || event.getAction() == MotionEvent.ACTION_MOVE
//	// || event.getAction() == MotionEvent.ACTION_CANCEL) {
//	// view.getBackground().setAlpha(255);
//	// view.invalidate();
//	// }
//	// }
//
//	/****************************************** ���ַ���ʽ������ֵ���㣬�����С����----��ʼ ***************************************/
//
//	/**
//	 * ����ʽ��
//	 * 
//	 * @param s
//	 *            ���
//	 * @return ��ʽ��Ľ��(###,###.##)
//	 */
//	public static String formatAmount(String s) {
//		return formatAmount(s, false);
//	}
//
//	public static String formatBigDecimalAmount(String s) {
//		String amount = "";
//		try {
//			BigDecimal bgAmount = new BigDecimal(s).setScale(2,
//					BigDecimal.ROUND_HALF_UP);
//			amount = bgAmount.toString();
//
//		} catch (Exception e) {
//			e.fillInStackTrace();
//		}
//		return amount;
//
//	}
//
//	/**
//	 * ����ʽ��
//	 * 
//	 * @param s
//	 *            ���
//	 * @param isInitNull
//	 *            �Ƿ��ʼ��Ϊ���ַ�
//	 * @return ��ʽ��Ľ��(###,###.##)
//	 */
//	public static String formatAmount(String s, boolean isInitNull) {
//		String result = "";
//		if (Util.isEmpty(s))
//			return "";
//		try {
//			String temp = s;
//			s = formatString(s);// ȥ��string���ܵķָ���
//			double num = 0.0;
//			try {
//				num = Double.parseDouble(s);
//			} catch (NumberFormatException e) {
//				new Debugger().log(e);
//			}
//			if (num == 0) {
//				if (isInitNull) {
//					return "";
//				} else {
//					return "0.00";
//				}
//			}
//			if (num < 1) {// С��1������⴦��
//				if (s.length() == 4) {// 0.05
//					return temp;
//				} else if (s.length() == 3) {// 0.5
//					return temp + "0";
//				}
//			}
//			NumberFormat formater = new DecimalFormat("#,###.00");
//			result = formater.format(num);
//		} catch (Exception e) {
//			new Debugger().log(e);
//		}
//		if (result.startsWith(".")) {
//			result = "0" + result;
//		}
//		return result;
//	}
//
//	/**
//	 * ���ֱ��ת��Ϊdouble����
//	 * 
//	 * @param amount
//	 *            ����Ǹ�ʽ�����,����"123,456.00"
//	 * @return
//	 */
//	public static double parseStringToDouble(String amount) {
//		if (isEmpty(amount)) {
//			return Double.parseDouble("0.00");
//		}
//		StringBuilder stringBuilder = new StringBuilder();
//		char[] charArray = amount.toCharArray();
//
//		for (int i = 0; i < charArray.length; i++) {
//			if (',' != charArray[i]) {
//				stringBuilder.append(charArray[i]);
//			}
//		}
//		double result = Double.parseDouble(stringBuilder.toString());
//
//		return result;
//	}
//
	/**
	 * ȥ���ַ��м�� "�ո�/-/," �ȼ����
	 * 
	 * @param string
	 *            Ҫ��ʽ�����ַ�
	 * @return ��ʽ������ַ�
	 */
	public static String formatString(String string) {
		if (string == null)
			return "";
		String newString = string.replaceAll(" ", "").replaceAll("-", "")
				.replaceAll(",", "");
		return newString;
	}
//
//	/**
//	 * �ַ�����˼�ȫ�ǿո񣬶����5������
//	 * 
//	 * @param string
//	 * @return
//	 */
//	public static String suffixSpaceToString(String string) {
//		return suffixSpaceToString(string, 5);
//	}
//
//	/**
//	 * �ַ�����˼�ȫ�ǿո񣬶����ָ������������
//	 * 
//	 * @param string
//	 * @param len
//	 *            ָ������λ��
//	 * @return
//	 */
//	public static String suffixSpaceToString(String string, int len) {
//		StringBuilder stringBuilder = new StringBuilder();
//		int length = string.length();
//		int appendCount = length < len ? len - length : 0;
//
//		for (int i = 0; i < appendCount; i++) {
//			stringBuilder.append("��");
//		}
//		return string + stringBuilder.toString();
//	}
//
	/**
	 * �ַ���ǰ�˼�ȫ�ǿո񣬶����5������
	 * 
	 * @param string
	 * @return
	 */
	public static String addSpaceToStringFront(String string) {
		return addSpaceToStringFront(string, 5);
	}

	/**
	 * �ַ���ǰ�˼�ȫ�ǿո񣬶����ָ������������
	 * 
	 * @param string
	 * @param len
	 *            ָ������λ��
	 * @return
	 */
	public static String addSpaceToStringFront(String string, int len) {
		StringBuilder stringBuilder = new StringBuilder();
		int length = string.length();
		int appendCount = length < len ? len - length : 0;

		for (int i = 0; i < appendCount; i++) {
			stringBuilder.append("��");
		}
		return stringBuilder.toString() + string;
	}

//	/**
//	 * �ַ�����˼�ȫ�ǿո񣬶����ָ������������
//	 * 
//	 * @param string
//	 * @param len
//	 *            ָ������λ��
//	 * @return
//	 */
//	public static String addSpaceToStringBack(String string, int len) {
//		StringBuilder stringBuilder = new StringBuilder();
//		int length = string.length();
//		int appendCount = length < len ? len - length : 0;
//
//		for (int i = 0; i < appendCount; i++) {
//			stringBuilder.append("��");
//		}
//		return string + stringBuilder.toString();
//	}
//	/**
//	 * ��ʽ�����ţ��ĸ�һ�飬�м��Կո����
//	 * 
//	 * @param cardNumber
//	 *            ���ţ�
//	 * @return ��ʽ��֮��Ŀ���
//	 */
//	public static String formatCardNumberWithSpace(String cardNumber) {
//
//		// if (!checkCardNumber(cardNumber)) {
//		// return cardNumber;
//		// }
//		cardNumber = cardNumber.replaceAll(" ", "");
//		char[] chars = cardNumber.toCharArray();
//		StringBuilder builder = new StringBuilder();
//		for (int i = 0; i < chars.length; i++) {
//			if (i != 0 && i % 4 == 0) {
//				builder.append(" ");
//			}
//			builder.append(chars[i]);
//		}
//
//		return builder.toString();
//	}
//
//	/**
//	 * ��ʽ�����ţ�ǰ6��4���м����λ�����Ǻ���ʾ
//	 */
//	public static String formatCardNumberWithStar(String cardNumber) {
//
//		return formatCardNumberWithStar(cardNumber, "*");
//	}
//
//	public static String formatCompanyAccount(String cardNumber) {
//
//		int length = cardNumber.length();
//		StringBuilder builder = new StringBuilder();
//		builder.append(cardNumber.substring(0, 1));
//		for (int i = 0; i < (length - 1); i++) {
//			builder.append("*");
//		}
//		builder.append(cardNumber.substring(length - 1, length));
//
//		return builder.toString();
//	}
//
//	/**
//	 * ��ʽ�����еĸ�ʽ ���ӣ���������(023534) ������������
//	 * 
//	 * @param bankWithBankCode
//	 * @return
//	 */
//	public static String formatBankWithBankCode(String bankWithBankCode) {
//		if (bankWithBankCode.indexOf("(") == -1) {
//			return bankWithBankCode;
//		}
//
//		return bankWithBankCode.substring(0, bankWithBankCode.indexOf("("));
//	}
//
//	/**
//	 * ��ʽ�����ţ�ǰ6��4���м����λ�����Ǻ���ʾ
//	 */
//	@Deprecated
//	public static String formatPersonAccount(String cardNumber) {
//		if (!checkCardNumber(cardNumber)) {
//			return cardNumber;
//		}
//		int length = cardNumber.length();
//		StringBuilder builder = new StringBuilder();
//		builder.append(cardNumber.substring(0, 1));
//		for (int i = 0; i < (length - 1); i++) {
//			builder.append("*");
//		}
//		builder.append(cardNumber.substring(length - 1, length));
//
//		return builder.toString();
//	}
//
//	/**
//	 * ǰ3�����ֺ�4������
//	 * 
//	 * @param phoneNo
//	 * @return
//	 */
//	public static String formatPhoneStart3End4(String phoneNo) {
//		if (null == phoneNo)
//			return "";
//
//		int length = phoneNo.length();
//		StringBuilder builder = new StringBuilder();
//		builder.append(phoneNo.substring(0, 3));
//		for (int i = 0; i < (length - 7); i++) {
//			builder.append("*");
//		}
//		builder.append(phoneNo.substring(length - 4, length));
//		return builder.toString();
//	}
//
//	/**
//	 * 
//	 * @param phoneNo
//	 * @return
//	 */
//	public static String formatPhoneNo(String phoneNo) {
//
//		if (null == phoneNo) {
//			return "";
//		}
//		int length = phoneNo.length();
//		StringBuilder builder = new StringBuilder();
//		builder.append(phoneNo.substring(0, 4));
//		for (int i = 0; i < (length - 3); i++) {
//			builder.append("*");
//		}
//		builder.append(phoneNo.substring(length - 3, length));
//		return builder.toString();
//	}
//
//	/**
//	 * ��ʽ�����ţ�ǰ6��4���м����λ�����Ǻ���ʾ
//	 * 
//	 * @param cardNumber
//	 *            ����
//	 * @param interval
//	 *            �ָ���
//	 */
//	public static String formatCardNumberWithStar(String cardNumber,
//			String interval) {
//		if (!checkCardNumber(cardNumber)) {
//			return cardNumber;
//		}
//		int length = cardNumber.length();
//		StringBuilder builder = new StringBuilder();
//		builder.append(cardNumber.substring(0, 6));
//		for (int i = 0; i < (length - 10); i++) {
//			builder.append(interval);
//		}
//		builder.append(cardNumber.substring(length - 4, length));
//
//		return builder.toString();
//	}
//
//	/**
//	 * ��ʽ�����ţ�ǰ6��4���м����4λ���ָ�����ʾ
//	 * 
//	 * @param cardNumber
//	 *            ����
//	 * @param interval
//	 *            �ָ���
//	 */
//	public static String formatCardNumberWith4Star(String cardNumber,
//			String interval) {
//		if (!checkCardNumber(cardNumber)) {
//			return cardNumber;
//		}
//		int length = cardNumber.length();
//		StringBuilder builder = new StringBuilder();
//		builder.append(cardNumber.substring(0, 6));
//		for (int i = 0; i < 4; i++) {
//			builder.append(interval);
//		}
//		builder.append(cardNumber.substring(length - 4, length));
//
//		return builder.toString();
//	}
//
//	/**
//	 * ���ת��Ϊȫ��
//	 * 
//	 * @param input
//	 * @return
//	 */
//	public static String ToDBC(String input) {
//		char[] c = input.toCharArray();
//		for (int i = 0; i < c.length; i++) {
//			if (c[i] == 12288) {
//				c[i] = (char) 32;
//				continue;
//			}
//			if (c[i] > 65280 && c[i] < 65375)
//				c[i] = (char) (c[i] - 65248);
//		}
//		return new String(c);
//	}
//
//	/**
//	 * ����������ַ���ת��Ϊ��ȫ�������ַ���
//	 * 
//	 * @param input
//	 * @return
//	 */
//	public static String ToQuanJiaoString(String input) {
//		input = input.replaceAll("0", "��").replaceAll("1", "��")
//				.replaceAll("2", "��").replaceAll("3", "��").replaceAll("4", "��")
//				.replaceAll("5", "��").replaceAll("6", "��").replaceAll("7", "��")
//				.replaceAll("8", "��").replaceAll("9", "��");
//		return input;
//	}

	/**
	 * ȥ���ַ�����β�Ŀո�����ַ�����null���򷵻�""
	 * 
	 * @param s
	 * @return
	 */
	public static String trim(String s) {
		if (s == null || s.equals("null"))
			return "";
		else
			return s.trim();
	}

//	/**
//	 * ����ת��ΪԪ,������С����
//	 * 
//	 * @param fenStr
//	 * @return
//	 */
//	public static String fen2Yuan(String fenStr) {
//
//		if (fenStr == null)
//			return "0.00";
//
//		// ���˷����ֵ����������˿��ܷ��ء�null���ַ�������ʱ���ء�0��
//		boolean isAllDigit = Pattern.matches("[\\d.]+", fenStr);
//		if (!isAllDigit) {
//			return "0.00";
//		}
//		String yuanString = new BigDecimal(fenStr)
//				.divide(new BigDecimal("100"))
//				.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
//		return yuanString;
//	}
//
//	/**
//	 * �������Ԫת��Ϊ��
//	 * 
//	 * @param yuanStr
//	 * @return
//	 */
//	public static String yuan2Fen(String yuanStr) {
//		if (isEmpty(yuanStr)) {
//			return "0";
//		}
//		yuanStr = formatString(yuanStr);
//		String fenString = new BigDecimal(yuanStr)
//				.multiply(new BigDecimal("100")).setScale(0).toString();
//		return fenString;
//	}
//
//	/**
//	 * �Ƚ�һ�����Ƿ��������һ����
//	 * 
//	 * @param oneNumber
//	 *            ����
//	 * @param anotherNumber
//	 *            ��һ������
//	 * @return
//	 */
//	public static boolean max(String oneNumber, String anotherNumber) {
//		if (null == oneNumber || "".equals(oneNumber)) {
//			oneNumber = "0";
//		}
//		if (null == anotherNumber || "".equals(anotherNumber)) {
//			anotherNumber = "0";
//		}
//		int count01 = new BigDecimal(oneNumber).compareTo(new BigDecimal(
//				anotherNumber));
//		if (count01 == 1) {// ����
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	/**
//	 * ʡ���û��������֤7-14λ����*�Ŵ���
//	 */
//	public static String formatCretifiNum(String cretifiNum) {
//		if (null == cretifiNum) {
//			return null;
//		}
//		StringBuilder sbBuilder = new StringBuilder(cretifiNum);
//		// sbBuilder.replace(6, 14, "********");//�滻λ��������ҿ�
//		for (int i = 1; i <= sbBuilder.length(); i++) {
//			if (i >= 7 && i <= 14) {
//				sbBuilder.replace(i - 1, i, "*");
//			}
//		}
//		return sbBuilder.toString();
//	}
//
//	/**
//	 * ʡ���û��������֤�м��ð˸�*�Ŵ���
//	 */
//	public static String formatIDCardNum(String cretifiNum) {
//		if (null == cretifiNum) {
//			return null;
//		}
//		StringBuilder sbBuilder = new StringBuilder(cretifiNum);
//		if (sbBuilder.length() >= 3) {
//			sbBuilder.replace(1, sbBuilder.length() - 1, "********");
//		} else {
//			sbBuilder.replace(1, sbBuilder.length(), "********");
//		}
//		return sbBuilder.toString();
//	}
//
//	/**
//	 * ʡ���û���������*�Ŵ���
//	 */
//	public static String formatUserName(String username) {
//		if (null == username || username.equals("")) {
//			return null;
//		}
//		StringBuilder sbBuilder = new StringBuilder(username);
//		sbBuilder.replace(1, sbBuilder.length(), "*");
//		return sbBuilder.toString();
//	}
//
//	/**
//	 * ����������ȥ@֮ǰ�Ĳ������ݣ�������ĸ���ǺŴ���
//	 * 
//	 * @param email
//	 * @return
//	 */
//	public static String formatEmailN1(String email) {
//		if (null == email) {
//			return null;
//		}
//		int index = email.indexOf("@");
//		StringBuilder sb = new StringBuilder(email);
//		if (index > 1) {
//			int len = email.length();
//
//			String str = "";
//			for (int i = 0; i < index - 1; i++) {
//				str += "*";
//			}
//			sb.replace(1, index, str);
//
//			// int count = len-4-(len-1-index);
//			// String str = "";
//			// for (int i = 0; i < count; i++) {
//			// str += "*";
//			// }
//			// sb.replace(1, index, str);
//		}
//		return sb.toString();
//	}
//
//	/**
//	 * ����������ȥ@֮ǰ�Ĳ������ݣ����ǺŴ���
//	 * 
//	 * @ǰ����3λʱ����������ȫ����ʾ
//	 * @ǰ��4λ������ʱ���ӵ�4λ��ȫ�����ǺŴ���
//	 */
//	public static String formatEmail(String email) {
//		if (null == email) {
//			return null;
//		}
//		int index = email.indexOf("@");
//		StringBuilder sb = new StringBuilder(email);
//		if (index > 2) {
//			int len = email.length();
//			int count = len - 4 - (len - 1 - index);
//			String str = "";
//			for (int i = 0; i < count; i++) {
//				str += "*";
//			}
//			sb.replace(3, index, str);
//		}
//		return sb.toString();
//	}
//
//	/**
//	 * �������ֵĳ����������ֵĴ�С
//	 * 
//	 * @param strLen
//	 *            ���ֵĳ���
//	 * @param textView
//	 */
//	public static void positionSizeAdapter(int strLen, TextView textView) {
//		if (strLen == 1) {
//			textView.setTextSize(16);
//		} else if (strLen == 2) {
//			textView.setTextSize(13);
//		} else if (strLen == 3) {
//			textView.setTextSize(10);
//		} else if (strLen == 4) {
//			textView.setTextSize(8);
//		}
//	}
//
//	/**
//	 * �����ַ����λ�Ե����
//	 * 
//	 * @param str
//	 * @param len
//	 *            �ַ�����޶�
//	 * @return ����Ϊlen + ... ���ַ�
//	 */
//	public static String appendDian(String str, int len) {
//		if (null != str && str.length() > len) {
//			str = str.substring(0, len);
//			str = str + "...";
//		}
//		return str;
//	}
//
//	/**
//	 * �����ַ����λ�Ե����
//	 * 
//	 * @param str
//	 * @param len
//	 *            �ַ�����޶�
//	 * @return �ܳ���Ϊlen���ַ�
//	 */
//	public static String appendDianLimit(String str, int len) {
//		if (null != str && str.length() > len) {
//			str = str.substring(0, len - 1);
//			str = str + "...";
//		}
//		return str;
//	}
//
//	/**
//	 * ������ҳ��&nbsp;��<br/>
//	 */
//	public static String filterSpaceString(String str) {
//		if (str == null)
//			return str;
//		str = str.replaceAll("&nbsp;", "\t").replaceAll("<br/>", "\n");
//		return str;
//	}
//
//	/**
//	 * ���˵绰�����������ʽ
//	 * 
//	 * @param str
//	 * @return ��һ���绰
//	 */
//	public static String getTelPhone(String str) {
//		if (str == null)
//			return str;
//		str = filterUnNumber(str);
//		int index = -1;
//		index = str.indexOf("-");
//		if (index == -1) {
//			index = str.indexOf("��");// ȫ��
//		}
//		if (index != -1 && index < 5 && str.length() >= 13) {
//			String startStr = str.substring(0, index);
//			String endStr = str.substring(index + 1, index + 9);
//			str = startStr + endStr;
//		} else if (index == -1 || index >= 5) {
//			str = str.substring(0, 9);
//		}
//		return str;
//	}
//
//	/**
//	 * ���˳�����-��������ַ�
//	 * 
//	 * @param str
//	 * @return
//	 */
//	public static String filterUnNumber(String str) {
//		// ֻ������
//		String regEx = "[^0-9-��]";
//		Pattern p = Pattern.compile(regEx);
//		Matcher m = p.matcher(str);
//		// �滻��ģʽƥ��������ַ����������ֵ��ַ�����""�滻��
//		return m.replaceAll("").trim();
//	}
//
//	/**
//	 * ��ȡ��ǰ����
//	 */
//	public static String getCurrentDate() {
//		Date date = new Date();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String currentDate = dateFormat.format(date);
//		return currentDate;
//	}
//
//	/******************************* ���ַ���ʽ������ֵ���㣬�����С����-----���� *****************************/
//
//	/*************************************** ��λ��ת��-----���ֵ�λ����λ----��ʼ ***********************************************/
//
//	/**
//	 * ��pxֵת��Ϊdip��dpֵ����֤�ߴ��С����
//	 * 
//	 * @param pxValue
//	 *            ����ֵ
//	 * @param scale
//	 *            ��DisplayMetrics��������density��
//	 * @return dpֵ
//	 */
//	public static int px2dip(float pxValue, Context context) {
//		float scale = getDensity(context);
//		return (int) (pxValue / scale + 0.5f);
//	}
//
//	/**
//	 * ��dip��dpֵת��Ϊpxֵ����֤�ߴ��С����
//	 * 
//	 * @param dipValue
//	 *            dip��ֵ
//	 * @param scale
//	 *            ��DisplayMetrics��������density��
//	 * @return ����ֵ
//	 */
//	public static int dip2px(float dipValue, Context context) {
//		float scale = getDensity(context);
//		return (int) (dipValue * scale + 0.5f);
//	}
//
//	/**
//	 * ��pxֵת��Ϊspֵ����֤���ִ�С����
//	 * 
//	 * @param pxValue
//	 *            ����ֵ
//	 * @param fontScale
//	 *            ��DisplayMetrics��������scaledDensity��
//	 * @return ����sp��ֵ
//	 */
//	public static int px2sp(float pxValue, Context context) {
//		float scale = getDensity(context);
//
//		return (int) (pxValue / scale + 0.5f);
//	}
//
//	/**
//	 * ��spֵת��Ϊpxֵ����֤���ִ�С����
//	 * 
//	 * @param spValue
//	 *            sp��ֵ
//	 * @param fontScale
//	 *            ��DisplayMetrics��������scaledDensity��
//	 * @return ��������ֵ
//	 */
//	public static int sp2px(float spValue, Context context) {
//		float scale = getDensity(context);
//		return (int) (spValue * scale + 0.5f);
//	}
//
//	/**
//	 * ȡ���ֻ���Ļ���ܶ�
//	 * 
//	 * @param context
//	 *            ������
//	 * @return �ֻ���Ļ���ܶ�
//	 */
//	public static float getDensity(Context context) {
//		float scale = context.getResources().getDisplayMetrics().density;
//		return scale;
//	}
//
//	/************************************* ��λ��ת��-----���ֵ�λ����λ----���� *******************************************/
//
//	/*************************************** У���ַ��Ϸ���-----��ʼ **********************************/
//
//	/**
//	 * ����û��Ƿ��¼
//	 * 
//	 * @return
//	 */
//	public static boolean isUserLogin() {
//		if (!Util.isEmpty(Parameters.user.userName)
//				&& !Util.isEmpty(Parameters.user.token)) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
	/**
	 * ����ַ����Ƿ�Ϊ��
	 * 
	 * @param string
	 * @return ��:true
	 */
	public static boolean isEmpty(String string) {
		if (string != null && string.length() > 0) {
			return false;
		}
		return true;
	}
//
//	/**
//	 * ������Ƿ���Ч
//	 * 
//	 * @param amountString
//	 *            ���
//	 * @return
//	 */
//	public static boolean isAmountVaild(String amountString) {
//		if (amountString != null && amountString.length() > 0) {
//			double amount = 0.0;
//			try {
//				amount = Double.parseDouble(amountString);
//			} catch (Exception e) {
//				new Debugger().log(e);
//				Util.toast(R.string.toast_money_format_error);
//			}
//			// ���������0
//			if (amount > 0.001) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public static boolean isAmountCorrect(String amount) {
//
//		return true;
//	}
//
//	/**
//	 * �жϽ���Ƿ���Ч
//	 * 
//	 * @param amountString
//	 *            ����Ľ��
//	 * @param max
//	 *            ��ҵ�����Ƶ������
//	 * @return true��false
//	 */
//	public static boolean isAmountVaild(String amountString, double max) {
//		if (isAmountVaild(amountString)) {
//			try {
//				double amount = Double.parseDouble(amountString);
//				if (amount > max) {
//					return false;
//				} else {
//					return true;
//				}
//			} catch (Exception e) {
//				new Debugger().log(e);
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * ��Ͽ����Ƿ�Ϸ�
//	 * 
//	 * @param cardNumber
//	 * @return
//	 */
//	private static boolean checkCardNumber(String cardNumber) {
//		if (cardNumber != null && cardNumber.length() >= 15
//				&& cardNumber.length() <= 19) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	/**
//	 * У�����п�����(��ǿ�)
//	 * 
//	 * @param cardId
//	 * @return false �Ƿ����� true �ʺϺϷ�����
//	 */
//	public static boolean checkDebitCard(String cardId) {
//		char bit = getDebitCardCheckCode(cardId.substring(0,
//				cardId.length() - 1));
//		if (bit == 'f') {
//			return false;
//		}
//		return cardId.charAt(cardId.length() - 1) == bit;
//	}
//
//	/**
//	 * �Ӳ���У��λ�����п����Ų��� Luhm У���㷨���У��λ
//	 * 
//	 * @param nonCheckCodeCardId
//	 *            ����У��λ�����п�����
//	 * @return char У��λ
//	 */
//	private static char getDebitCardCheckCode(String nonCheckCodeCardId) {
//		if (nonCheckCodeCardId == null
//				|| nonCheckCodeCardId.trim().length() == 0
//				|| !nonCheckCodeCardId.matches("^[0-9]+$")) {
//
//			return 'f';
//		}
//		char[] chs = nonCheckCodeCardId.trim().toCharArray();
//		int luhmSum = 0;
//		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
//			int k = chs[i] - '0';
//			if (j % 2 == 0) {
//				k *= 2;
//				k = k / 10 + k % 10;
//			}
//			luhmSum += k;
//		}
//		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
//	}
//
//	/**
//	 * У�����ÿ������Ƿ�Ϊ��ȷ���ţ���ֹ������󿨺ţ� Luhn �㷨У��
//	 * 
//	 * @param cardNumber
//	 *            ���ÿ�����
//	 * @return false �Ƿ����� true �ʺϺϷ�����
//	 */
//	public static boolean checkCreditCard(String cardNumber) {
//		String digitsOnly = getDigitsOnly(cardNumber);
//		int sum = 0;
//		int digit = 0;
//		int addend = 0;
//		boolean timesTwo = false;
//
//		for (int i = digitsOnly.length() - 1; i >= 0; i--) {
//			digit = Integer.parseInt(digitsOnly.substring(i, i + 1));
//			if (timesTwo) {
//				addend = digit * 2;
//				if (addend > 9) {
//					addend -= 9;
//				}
//			} else {
//				addend = digit;
//			}
//			sum += addend;
//			timesTwo = !timesTwo;
//		}
//
//		int modulus = sum % 10;
//		return modulus == 0;
//	}
//
//	/**
//	 * ���˵��������ַ�
//	 * 
//	 * @param s
//	 *            �ַ���
//	 * @return ���˺󿨺��ַ�
//	 */
//	private static String getDigitsOnly(String s) {
//		StringBuffer digitsOnly = new StringBuffer();
//		char c;
//		for (int i = 0; i < s.length(); i++) {
//			c = s.charAt(i);
//			if (Character.isDigit(c)) {
//				digitsOnly.append(c);
//			}
//		}
//		return digitsOnly.toString();
//	}
//
//	/**
//	 * ����Ƿ�������
//	 * 
//	 * @param phoneNumber
//	 * @return
//	 */
//	public static boolean isPhoneNumberValid(String phoneNumber) {
//		boolean isValid = false;
//		String expression = "^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$";
//		CharSequence inputStr = phoneNumber;
//		Pattern pattern = Pattern.compile(expression);
//		Matcher matcher = pattern.matcher(inputStr);
//		if (matcher.matches()) {
//			isValid = true;
//		}
//		return isValid;
//	}
//
//	/**
//	 * ����ֻ����Ƿ�Ϸ� </br>���п�ֵ�ͳ�������11λ
//	 * 
//	 * @param number�ֻ�����
//	 * @return true �Ϸ� false ���Ϸ�
//	 */
//	public static boolean checkPhoneNumber(String number) {
//		if (number.equals("") || number.length() != 11) {
//			return false;
//		}
//		Pattern pattern = Pattern.compile("1[0-9]{10}");
//		Matcher matcher = pattern.matcher(number);
//		if (matcher.matches()) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	/**
//	 * У��email��ַ�ĺϷ���
//	 * 
//	 * @param emailString
//	 *            ���������ַ
//	 * @return true �Ϸ� false ���Ϸ�
//	 */
//	public static boolean checkEmailAddress(String emailString) {
//		// String regEx = "^[\\w\\d]+@[\\w\\d]+(\\.[\\w\\d]+)+$";
//		String regEx = "\\w+([-+._]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
//
//		Matcher matcherObj = Pattern.compile(regEx).matcher(emailString);
//
//		if (matcherObj.matches()) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	/**
//	 * У�����֤�ŵĺϷ��� <br>
//	 * �����ڽ���鳤�ȣ��Ѿ��������ݺϷ��ԣ���������ʵ�ԣ�
//	 * 
//	 * @param idCard
//	 *            ���֤��
//	 * 
//	 * @return true �Ϸ� false ���Ϸ�
//	 */
//	public static boolean checkIdCard(String idCard) {
//		String regEx = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";
//		Matcher matcherObj = Pattern.compile(regEx).matcher(idCard);
//
//		if (matcherObj.matches()) {
//			if (idCard.length() == 15) {
//				// �����15λ���֤�ţ�����Ϊ��֤�ɹ���
//				return true;
//			}
//
//			// �����18λ���֤�ţ����ڼ���У�����Ƿ���ȷ��
//			int sigma = 0;
//			// ϵͳ����
//			Integer[] coeTable = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
//					8, 4, 2 };
//			// У�����
//			String[] codeTable = { "1", "0", "X", "9", "8", "7", "6", "5", "4",
//					"3", "2" };
//
//			// �����֤ÿһλ����ϵ�����е�ϵ���������ӡ�
//			for (int i = 0; i < 17; i++) {
//				int ai = Integer.parseInt(idCard.substring(i, i + 1));
//				int wi = coeTable[i];
//				sigma += ai * wi;
//			}
//			// ���ȡ 11 ������
//			int number = sigma % 11;
//			// ʹ��������������ȡУ���롣
//			String check_number = codeTable[number];
//			if (idCard.substring(17).equalsIgnoreCase(check_number)) {
//				return true;
//			} else {
//				return false;
//			}
//		} else {
//			return false;
//		}
//	}
//
//	/**
//	 * У���û���������Ϸ��� <br>
//	 * �����ڽ�����û�������Ӣ�ġ����ĺ͵㣩
//	 * 
//	 * @param username
//	 *            �û�����
//	 * 
//	 * @return true �Ϸ� false ���Ϸ�
//	 */
//	public static boolean checkUserName(String username) {
//		String regEx = "[\\w\\u4e00-\\u9fa5\\. ]+";
//		Matcher matcherObj = Pattern.compile(regEx).matcher(username);
//
//		if (matcherObj.matches()) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	/**
//	 * У���û���������Ϸ��� <br>
//	 * �����ڽ�����û����������ĺ͵㣩
//	 * 
//	 * @param username
//	 *            �û�����
//	 * 
//	 * @return true �Ϸ� false ���Ϸ�
//	 */
//	public static boolean checkChineseUserName(String username) {
//		String regEx = "[\\u4e00-\\u9fa5\\. ]+";
//		Matcher matcherObj = Pattern.compile(regEx).matcher(username);
//
//		if (matcherObj.matches()) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	public static boolean checkzipCode(String zipCode) {
//		String regEx = "([0-9]{6})+";
//		Matcher matcherObj = Pattern.compile(regEx).matcher(zipCode);
//
//		if (matcherObj.matches()) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
	/**
	 * �������ǿ��
	 * 
	 * @param password
	 * @return �ã�GOOD һ�㣺GENERAL ����BAD
	 */
	public static String checkPWLevel(String password) {
		String pwLevel = null;
		int count = 0;
		if (Pattern.compile("(?i)[a-zA-Z]").matcher(password).find()) {
			count += 10;
		}
		if (Pattern.compile("(?i)[0-9]").matcher(password).find()) {
			count += 10;
		}
		if (Pattern
				.compile(
						"(?i)[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~��@#��%����&*��������+|{}������������������������]")
				.matcher(password).find()) {
			count += 10;
		}
		if (count == 10) {
			pwLevel = "BAD";
		} else if (count == 20) {
			pwLevel = "GENERAL";
		} else if (count == 30) {
			pwLevel = "GOOD";
		}
		return pwLevel;
	}

//	/************************************ У���ַ��Ϸ���-----���� *********************************/
//
//	/******************************************************* ����������Ϣ----��ʼ ***********************************************************/
//
//	/**
//	 * ����ָ�����ȵ������ĸ��������ϵ��ַ��������ڿͻ����Զ����token
//	 * 
//	 * @param length
//	 *            ָ������
//	 * @return
//	 */
//	public static String getCharAndNumrToken(int length) {
//		String val = "";
//
//		Random random = new Random();
//		for (int i = 0; i < length; i++) {
//			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // �����ĸ��������
//
//			if ("char".equalsIgnoreCase(charOrNum)) // �ַ���
//			{
//				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // ȡ�ô�д��ĸ����Сд��ĸ
//				val += (char) (choice + random.nextInt(26));
//			} else if ("num".equalsIgnoreCase(charOrNum)) // ����
//			{
//				val += String.valueOf(random.nextInt(10));
//			}
//		}
//
//		return val;
//	}
//
//	/**
//	 * ��ȡĿǰ����汾 ��������
//	 * 
//	 * @return
//	 */
//	public static String getAppVersionCode() {
//		String versionCode = "";
//		try {
//			PackageManager pm = ApplicationExtension.getInstance()
//					.getPackageManager();
//
//			PackageInfo pi = pm.getPackageInfo(ApplicationExtension
//					.getInstance().getPackageName(), 0);
//			int ver = (pi.versionCode - 32);
//
//			versionCode = "skb_" + (ver < 10 ? ("0" + ver) : ("" + ver));// ��33�汾��ʼ�����
//																			// ��p_v
//																			// ��
//																			// ��ǰ�汾��ȥ32
//		} catch (Exception e) {
//		}
//		return versionCode;
//	}
//
	/**
	 * ��ȡ���װ汾
	 * 
	 * @return
	 */
	public static String getVersionCode() {
		String versionCode = "";
		try {
			PackageManager pm = ApplicationExtension.getInstance()
					.getPackageManager();

			PackageInfo pi = pm.getPackageInfo(ApplicationExtension
					.getInstance().getPackageName(), 0);

			versionCode = "" + pi.versionCode;
		} catch (Exception e) {
		}
		return versionCode;
	}
//
//	/**
//	 * ��ȡĿǰ����汾
//	 * 
//	 * @return
//	 */
//	public static String getAppVersionName() {
//		String versionName = "";
//		try {
//			PackageManager pm = ApplicationExtension.getInstance()
//					.getPackageManager();
//
//			PackageInfo pi = pm.getPackageInfo(ApplicationExtension
//					.getInstance().getPackageName(), 0);
//			versionName = pi.versionName + "";
//		} catch (Exception e) {
//		}
//		return versionName;
//	}
//
//	// ��ȡmanifest������������Ϣ
//	public static String getChanel() {
//		String CHANNELID = "0";
//		try {
//			ApplicationInfo ai = ApplicationExtension
//					.getInstance()
//					.getPackageManager()
//					.getApplicationInfo(
//							ApplicationExtension.getInstance().getPackageName(),
//							PackageManager.GET_META_DATA);
//			Object value = ai.metaData.get("CHANNEL");
//			if (value != null) {
//				CHANNELID = value.toString();
//			}
//		} catch (Exception e) {
//			new Debugger().log(e);
//		}
//		CHANNELID = trim(CHANNELID);
//
//		return CHANNELID;
//	}
//
//	/**
//	 * MD5����
//	 * 
//	 * @param s
//	 * @return
//	 */
//	public final static String MD5(String s) {
//		final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
//				'9', 'a', 'b', 'c', 'd', 'e', 'f' };
//		try {
//			byte[] strTemp = s.getBytes();
//			// ʹ��MD5����MessageDigest����
//			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
//			mdTemp.update(strTemp);
//			byte[] md = mdTemp.digest();
//			int j = md.length;
//			char str[] = new char[j * 2];
//			int k = 0;
//			for (int i = 0; i < j; i++) {
//				byte b = md[i];
//				str[k++] = hexDigits[b >> 4 & 0xf];
//				str[k++] = hexDigits[b & 0xf];
//			}
//			return new String(str);
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	/*********************************************** ����������Ϣ----���� ********************************************************/
//
//	/*********************************************** �豸Ӳ����Ϣ��������Ϣ-----��ʼ ********************************************************/
	/**
	 * ��ȡ�ֻ�imei����
	 * 
	 * @return
	 */
	public static String getIMEI() {
		return PhoneUtils.getIMEI();
	}

	/**
	 * ��ȡIMSI��
	 * 
	 * @return
	 */
	public static String getIMSI() {
		return PhoneUtils.getIMSI();
	}

//	/**
//	 * ��ȡ�ֻ�������Ӫ������
//	 * 
//	 * @return
//	 */
//	public static String getPhoneISP() {
//		return PhoneUtils.getPhoneISP();
//	}
//
//	/**
//	 * ��ȡ�ֻ�ϵͳ�汾
//	 * 
//	 * @return
//	 */
//	public static String getPhoneOSVersion() {
//		return PhoneUtils.getPhoneOSVersion();
//	}
//
//	/**
//	 * ��ȡ�ֻ���ʶ eg:ME860
//	 * 
//	 * @return
//	 */
//	public static String getPhoneModel() {
//		return PhoneUtils.getPhoneModel();
//	}
//
//	/**
//	 * ��ȡ�ֻ��ͺ� eg:ME860_HKTW
//	 * 
//	 * @return
//	 */
//	public static String getPhoneType() {
//		return PhoneUtils.getPhoneType();
//	}
//
//	/**
//	 * ��ȡ�ֻ����� eg:motorola
//	 * 
//	 * @return
//	 */
//	public static String getPhonePhoneManuFacturer() {
//		return PhoneUtils.getPhonePhoneManuFacturer();
//	}
//
//	/**
//	 * �����Ƿ����
//	 * 
//	 * @return true���� </br> false������
//	 */
//	public static boolean isNetworkAvailable() {
//		return PhoneUtils.isNetworkAvailable();
//	}
//
//	/**
//	 * ���ص�ǰ�ֻ��������������,
//	 * 
//	 * @return ����ֵ: 1.����mobile(2G3G), 2����wifi
//	 */
//	public static String getNetworkStat() {
//		return PhoneUtils.getNetworkStat();
//	}
//
//	/**
//	 * ���gps�Ƿ����
//	 * 
//	 * @return
//	 */
//	public static boolean isGpsAvaiable() {
//		return PhoneUtils.isGpsAvaiable();
//	}
//
//	/**
//	 * ��gps�͹ر�gps
//	 * 
//	 * @param context
//	 */
//	public static void autoGps(Context context) {
//		PhoneUtils.autoGps(context);
//	}
//
//	/**
//	 * TextView ��ɫ����
//	 * 
//	 * @param str
//	 *            ��Ҫ������ʾ���ַ���
//	 * @param from
//	 *            ��ʼλ��
//	 * @param to
//	 *            ����Ϊֹ
//	 * @return
//	 */
//	public static SpannableStringBuilder buildRed(String str, int from, int to) {
//		SpannableStringBuilder style = new SpannableStringBuilder(str);
//		style.setSpan(new ForegroundColorSpan(Color.RED), from, to,
//				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		return style;
//	}
//
//	/**
//	 * TextView ���ָ�����ʾ
//	 * 
//	 * @param color
//	 *            ��Ҫ��ʾ��������ɫ
//	 * @param str
//	 *            ��Ҫ������ʾ���ַ���
//	 * @param from
//	 *            ��ʼλ��
//	 * @param to
//	 *            ����Ϊֹ
//	 * @param isUnderline
//	 *            �Ƿ�Ҫ�»���
//	 * @return
//	 */
//	public static SpannableStringBuilder buildColor(int color, String str,
//			int from, int to, boolean isUnderline) {
//		SpannableStringBuilder style = new SpannableStringBuilder(str);
//		style.setSpan(new ForegroundColorSpan(color), from, to,
//				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		if (isUnderline) {
//			style.setSpan(new UnderlineSpan(), from, to,
//					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		}
//		return style;
//	}
//
//	/**
//	 * WebView֧�ֶ������,ȥ��WebView ���Ű�ť������3.0һ���û�ʹ�÷���
//	 * 
//	 * @param view
//	 */
//	// @TargetApi(11)
//	public static void setWebViewZoomControlGone(View view) {
//		boolean ISHONEYCOMB = Build.VERSION.SDK_INT >= 11;
//		if (ISHONEYCOMB) {
//			// �˷�����Ҫ3.0+ sdk������ʱ ��Ҫ��sdk�л�һ��
//			// ((WebView)view).getSettings().setDisplayZoomControls(false);
//		} else {
//			Class<?> classType;
//			Field field;
//			try {
//				classType = WebView.class;
//				field = classType.getDeclaredField("mZoomButtonsController");
//				field.setAccessible(true);
//				ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(
//						view);
//				mZoomButtonsController.getZoomControls().setVisibility(
//						View.GONE);
//				try {
//					field.set(view, mZoomButtonsController);
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				}
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			} catch (NoSuchFieldException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * �жϴ���ʱ����Ƿ����
//	 * 
//	 * @param startTime
//	 * @param endTime
//	 * @return
//	 */
//	public static boolean timeNotEnd(String startTime, String endTime) {
//		if (startTime == null || endTime == null)
//			return false;
//		if (startTime.equals("") || endTime.equals(""))
//			return false;
//		try {
//			long start = Long.parseLong(startTime);
//			long end = Long.parseLong(endTime);
//			if ((end - start) > 0) {
//				return true;
//			}
//		} catch (NumberFormatException e) {
//			new Debugger().log(e);
//		}
//		return false;
//	}
//
//	/**
//	 * ���ط����¼�
//	 * 
//	 * @param activity
//	 * @param channelCode
//	 *            ������������
//	 * @param callbackUrl
//	 *            �������ص���ַ
//	 * @see #splitCallBack(String, String)
//	 */
//	public static void interceptBackEvent(Activity activity,
//			String channelCode, String callbackUrl) {
//
//		if (null == channelCode) {
//			channelCode = "";
//		}
//
//		if ("".equals(channelCode)) {
//			activity.finish();
//		} else {
//			if (!"".equals(callbackUrl)) {
//
//				String[] strings = splitCallBack(channelCode, callbackUrl);
//				String pkg = strings[0].trim();
//				String clazz = strings[1].trim();
//				Intent intent = new Intent();
//				intent.setComponent(new ComponentName(pkg, clazz));
//				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);// ���ݵ��������ֻ��ͻ����޸ķ��ص���ʱ����Activity������
//				activity.startActivity(intent);
//			}
//			ApplicationExtension.getInstance().exit();
//		}
//	}
//
//	/**
//	 * ���������Ų��callback
//	 * 
//	 * @param channelCode
//	 * @param callback
//	 * @return String[]
//	 */
//	public static String[] splitCallBack(String channelCode, String callback) {
//
//		if (null == callback || "".equals(callback)) {
//			throw new IllegalArgumentException("callback is null ");
//		}
//
//		String[] results = new String[] {};
//
//		// TODO ��ʱ��δʵ������������,��Map<code,prefix>���� ??
//		results = callback.replaceAll("cmb://", "").split("\\|");
//		final int length = results.length;
//		if (2 != length) {
//			throw new RuntimeException("callback format is error");
//		}
//		return results;
//	}
//
//	/**
//	 * CanUseWithOutInput �жϽ���Ƿ񳬹�100
//	 * 
//	 * @param mount
//	 *            ��� ��λԪ
//	 * 
//	 * */
//	private boolean CanUseWithOutInput(int mount) {
//		boolean beyond = false;
//		if (mount >= 100) {
//			beyond = true;
//		}
//		return beyond;
//	}
//
//	/**
//	 * �������ͷ����ٺ�(6Ϊ����)Series,����
//	 * 
//	 * @return
//	 */
//	public static String createSeries() {
//
//		LklSharedPreferences pref = LklSharedPreferences.getInstance();
//		int value = pref.getInt(UniqueKey.PREFERENCE_SERIES_KEY);
//		if (value < UniqueKey.PREFERENCE_SERIES_MAX_VALUE) {
//			value = value + 1;
//		} else {
//			value = 0;
//		}
//		String series = addMarkToStringFront(String.valueOf(value), "0", 6);
//		pref.putInt(UniqueKey.PREFERENCE_SERIES_KEY, value);
//		return series;
//	}
//
//	/**
//	 * ���ַ���ǰ���ָ�����ַ�
//	 * 
//	 * @param string
//	 *            ��Ҫ��ȫ���ַ�
//	 * @param mark
//	 *            �����ַ�
//	 * @param len
//	 *            ��ȫ����ַ�����
//	 * @return
//	 */
//	public static String addMarkToStringFront(String string, String mark,
//			int len) {
//		StringBuilder stringBuilder = new StringBuilder();
//		int length = string.length();
//		int appendCount = length < len ? len - length : 0;
//
//		for (int i = 0; i < appendCount; i++) {
//			stringBuilder.append(mark);
//		}
//		return stringBuilder.append(string).toString();
//	}
//
//	/**
//	 * ���ַ���ǰ���ָ�����ַ�
//	 * 
//	 * @param string
//	 *            ��Ҫ��ȫ���ַ�
//	 * @param mark
//	 *            �����ַ�
//	 * @param len
//	 *            ��ȫ����ַ�����
//	 * @return
//	 */
//	public static String addMarkToStringAfter(String string, String mark,
//			int len) {
//		StringBuilder stringBuilder = new StringBuilder();
//		int length = string.length();
//		int appendCount = length < len ? len - length : 0;
//		stringBuilder.append(string);
//		for (int i = 0; i < appendCount; i++) {
//			stringBuilder.append(mark);
//		}
//		return stringBuilder.toString();
//	}
//
//	/**
//	 * �����ֻ�model�ж��Ƿ���С��2
//	 * 
//	 * @return
//	 */
//	public static boolean isMITwo() {
//		String model = getPhoneModel();
//		model = formatString(model);
//		if (UniqueKey.PHONE_MODEL.equals(model)) {
//			return true;
//		}
//		return false;
//	}
//
//	/**
//	 * ����rom������Ͱ汾�Ƚϣ�����ֻ�rom�汾����ָ���汾����ͨ���㲥�رնű�
//	 * 
//	 * @return
//	 */
//	public static boolean isNewVersion() {
//		String osVersion = Build.VERSION.INCREMENTAL;
//		if (osVersion.startsWith(UniqueKey.STABLETAG)) { // �ȶ���
//			// osVersion.compareTo(string) ����ʹ�������⣬�ǰ����ַ��ȶԣ�JLB4.0�����JLB16.0��
//			String replaceVerson = osVersion.replace(UniqueKey.STABLETAG, "");
//			String replaceTarget = UniqueKey.STABLEVERSION.replace(
//					UniqueKey.STABLETAG, "");
//			return strCompare(replaceVerson, replaceTarget);
//		} else {
//			return strCompare(osVersion, UniqueKey.ENGVERSION);
//		}
//	}
//
//	/**
//	 * ���δ��ָ���ַ����ȶ�
//	 * 
//	 * @param dist
//	 *            "3.5.8"
//	 * @param targetVersion
//	 *            "3.5.7"
//	 * @return "3.5.8">"3.5.7" "3.5.10">"3.5.7" ...
//	 */
//	private static boolean strCompare(String dist, String targetVersion) {
//
//		String[] osVersions = dist.split("\\.");
//		String[] targetVersions = targetVersion.split("\\.");
//		int osLength = osVersions.length;
//		int tarLength = targetVersions.length;
//		int length = Math.min(osLength, tarLength);
//		int isNew = 0;
//
//		for (int i = 0; i < length; i++) {
//			int version = Integer.parseInt(osVersions[i]);
//			int target = Integer.parseInt(targetVersions[i]);
//			if (version > target) {
//				isNew = version - target;
//				break;
//			} else if (version < target) {
//				isNew = version - target;
//				break;
//			} else {
//				isNew = 0;
//			}
//		}
//
//		if ((isNew == 0) && (osLength > tarLength)) {
//			isNew = osLength - tarLength;
//		}
//
//		return isNew >= 0;
//	}
//
//	/**
//	 * �㲥���ű�
//	 * 
//	 * @param context
//	 */
//	public static void checkDolbyIntent(Context context) {
//		Intent intent = new Intent();
//		intent.setAction(UniqueKey.CHECK_STATUS_ACTION);
//		context.sendBroadcast(intent);
//	}
//
//	/**
//	 * �㲥���¶ű�
//	 * 
//	 * @param context
//	 * @param bEnable
//	 *            1 ����; 0�ر�
//	 */
//	public static void updateDolbyIntent(Context context, int bEnable) {
//		Intent intent = new Intent();
//		intent.setAction(UniqueKey.ACTION_DOLBY_UPDATE);
//		intent.putExtra("enable", bEnable);
//		context.sendBroadcast(intent);
//	}
//
//	/**
//	 * ��ת����ҳ��,��Ҫ����flag����
//	 * 
//	 * @param context
//	 * @param flags
//	 */
//	public static void startHomeActivity(Context context, int flags) {
//		// Intent intent = new Intent(context, MainActivity.class);
//		Intent intent = new Intent(context, ShouDanMainActivity.class);
//
//		if (flags != 0) {
//			intent.setFlags(flags);
//		}
//		context.startActivity(intent);
//	}
//
//	/**
//	 * ��ת����¼,��Ҫ����flag����
//	 * 
//	 * @param context
//	 * @param flags
//	 */
//	public static void startLoginActivity(Context context, int flags) {
//		Intent intent = new Intent(context, LoginActivity.class);
//
//		if (flags != 0) {
//			intent.setFlags(flags);
//		}
//		context.startActivity(intent);
//	}
//
//	/**
//	 * ��ת����ҳ��,����Ҫ����flag����
//	 * 
//	 * @param context
//	 */
//	public static void startHomeActivity(Context context) {
//		startHomeActivity(context, 0);
//	}
//
//	/**
//	 * �����ת����ҳ��intent
//	 * 
//	 * @param context
//	 * @return
//	 */
//	public static Intent getHomeIntent(Context context) {
//		// return new Intent(context,MainActivity.class);
//		return new Intent(context, ShouDanMainActivity.class);
//
//	}
//
//	/**
//	 * ���ݷ���������code,�ж��Ƿ�ɹ�.
//	 * 
//	 * @param retCode
//	 *            ���������ص�Code
//	 * @return
//	 */
//	public static boolean isProcessSuccess(final String retCode) {
//		return Parameters.successRetCode.equals(retCode);
//	}
//
//	public static String formatDisplayAmount(String amount) {
//		return formatAmount(amount) + "Ԫ";
//	}
//
//	public static String getNowTime() {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		return sdf.format(new Date());
//	}
//
//	public static String addAmount(String amt1, String amt2) {
//
//		return formatAmount(String.valueOf(Double.parseDouble(amt1)
//				+ Double.parseDouble(amt2)));
//
//	}
//
//	/**
//	 * �������(value)
//	 * 
//	 * @param value
//	 * @return
//	 */
//	public static String addBrackets(String value) {
//		if (null == value)
//			return "";
//		return "(" + value + ")";
//	}
//
//	/**
//	 * ʣ��������λת��
//	 * @author ZhengWx
//	 * @date 2014��9��11�� ����11:45:19
//	 * @param size ��С����λbit
//	 * @return ת����ĵ�λ��С���磺10.3MB
//	 * @since 1.0
//	 */
//	public static String convertSpaceSize(long size) {
//		String strUnit = "Bytes";
//		double intDivisor = 1;
//		StringBuilder sb = new StringBuilder();
//
//		try {
//			size /= 8;
//			if (size >= 1024 * 1024) {
//				strUnit = "MB";
//				intDivisor = 1024 * 1024;
//			} else if (size >= 1024) {
//				strUnit = "KB";
//				intDivisor = 1024;
//			}
//
//			if (intDivisor == 1) {
//				sb.append(size);
//				sb.append(" ");
//				sb.append(strUnit);
//			} else {
//				DecimalFormat df = new DecimalFormat("######0.00");
//
//				sb.append(df.format(size / intDivisor));
//				sb.append(" ");
//				sb.append(strUnit);
//			}
//		} catch (Exception e) {
//			sb.setLength(0);
//			sb.append("0.00");
//			sb.append(strUnit);
//		}
//
//		return sb.toString();
//	}
//	
//	/**
//	 * �ж�������ֻ����Ƿ�Ϊ�й��ƶ�����
//	 * @author Yejx
//	 * @date 2014��11��27�� ����3:39:20
//	 * @param phoneNo
//	 * @return boolean
//	 * @since 1.0
//	 */
//	public static boolean isChinaMobileNo(String phoneNo){
//		Pattern pattern1 = Pattern.compile("^134[0-8].*");
//		Matcher matcher1 = pattern1.matcher(phoneNo);
//		
//		Pattern pattern2 = Pattern.compile("^13[5-9].*");
//		Matcher matcher2 = pattern2.matcher(phoneNo);
//		
//		Pattern pattern3 = Pattern.compile("^15[0-2].*");
//		Matcher matcher3 = pattern3.matcher(phoneNo);
//		
//		Pattern pattern4 = Pattern.compile("^15[7-9].*");
//		Matcher matcher4 = pattern4.matcher(phoneNo);
//		
//		Pattern pattern5 = Pattern.compile("^18[2-3].*");
//		Matcher matcher5 = pattern5.matcher(phoneNo);
//		
//		Pattern pattern6 = Pattern.compile("^18[7-8].*");
//		Matcher matcher6 = pattern6.matcher(phoneNo);
//		
//		if(matcher1.matches() || matcher2.matches() || matcher3.matches() 
//				|| matcher4.matches() || matcher5.matches()|| matcher6.matches()
//				|| phoneNo.startsWith("147")){
//			return true;
//		}
//		return false;
//	}
//	
//	/**
//	 * �ж������豸�Ƿ�ΪME18�豸
//	 * @author Yejx
//	 * @date 2014��11��28�� ����11:05:55
//	 * @param name
//	 * @return boolean
//	 * @since 1.0
//	 */
//	public static boolean bluetoothDeviceFilter(String name){
//		if(name != null && name.toLowerCase().contains("me18")){
//			return true;
//		}
//		return false;
//	}
//	
//	/**
//	 * ��JSONObject����ΪMap����
//	 * @author Yejx
//	 * @date 2014��12��9�� ����14:52:50
//	 * @param jsonObject
//	 * @return Map<String,Object>
//	 * @since 1.0
//	 */
//	public static Map<String,Object> getMap(JSONObject jsonObject){
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			Iterator<String> keyIter = jsonObject.keys();
//			while (keyIter.hasNext()) {
//				String key = (String) keyIter.next();
//				Object value = jsonObject.get(key);
//				map.put(key, value);
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return map;
//	}
//	
//	/**
//	 * ��jsonArray����ΪList����
//	 * @author Yejx
//	 * @date 2014��12��9�� ����14:52:50
//	 * @param jsonArray
//	 * @return List<byte[]>
//	 * @since 1.0
//	 */
//	public static List<byte[]> getList(JSONArray jsonArray){
//		List<byte[]>  list = new ArrayList<byte[]>();
//		try {
//			for (int i = 0; i < jsonArray.length(); i++) {
//				byte[] b = CodecUtils.hex2byte(jsonArray.getString(i));
//				list.add(b);
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
//	}
//	
//	/**
//	 * ������ЧAid�б�
//	 * @author Yejx
//	 * @date 2014��12��11�� ����11:13:18
//	 * @param aidList
//	 * @return List<byte[]>
//	 * @since 1.0
//	 */
//	public static List<byte[]> filterAidList(List<byte[]> aidList){
//		List<byte[]> aids = new ArrayList<byte[]>();
//		for (byte[] aid : aidList) {
//			String aidStr = CodecUtils.hexString(aid);
//			if("A0000003330101060048080000010000".equals(aidStr) 
//					|| "A0000003330101060048080000030000".equals(aidStr) 
//					|| "D156000015CCECB8AECDA8BFA800".equals(aidStr)
//					|| "D1560001018000000000000100000000".equals(aidStr)){
//				aids.add(aid);
//			}
//		}
//		return aids;
//	}
//	
//	/**
//	 * �ж�����Ľ���Ƿ�����
//	 * @author Yejx
//	 * @date 2015��1��13�� ����11:53:30
//	 * @param amount
//	 * @return boolean
//	 * @since 1.0
//	 */
//	public static boolean isAmountError(String amount){
//		Pattern pattern = Pattern.compile("^([1-9]\\d*|0)(\\.\\d{1,2})?$");
//		Matcher matcher = pattern.matcher(amount);
//	    if(matcher.matches()){
//	    	return false;
//		}
//		return true;
//	}
	
	//ж��Ӧ��
	public void uninstall(Context context){
		
		Intent intent = new Intent();
	    intent.setAction(Intent.ACTION_DELETE);
	    intent.setData(Uri.parse("package:com.example.cardiograph"));
	    context.startActivity(intent);
	}
}
