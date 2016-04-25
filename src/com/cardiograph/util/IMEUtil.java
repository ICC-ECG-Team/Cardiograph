package com.cardiograph.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 系统输入法键�?�?��工具
 * 
 * @author xyz
 * 
 */
public class IMEUtil {
	/**
	 * 隐藏键盘
	 * @param context
	 */
	public static void hideIme(Activity context) {
		if (context == null)
			return;
		final View v = context.getWindow().peekDecorView();
		if (v != null && v.getWindowToken() != null) {
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
	}
	
	/**
	 * �?��系统键盘是否显示
	 * @param context
	 * @return
	 */
	public static boolean  isSysKeyboardVisiable(Activity context) {
		final View v = context.getWindow().peekDecorView();
		if (v != null && v.getWindowToken() != null) {
			return true;
		}
		return false;
	}
}
