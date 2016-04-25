package com.cardiograph.view;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputFilter.LengthFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.cardiograph.component.BtnWithTopLine;
import com.cardiograph.component.NavigationBar;
import com.cardiograph.constance.Constance;
import com.cardiograph.model.User;
import com.cardiograph.thread.RequestTask;
import com.cardiograph.thread.RequestTask.ResponseCallBack;
import com.cardiograph.util.IMEUtil;
import com.cardiograph.util.Util;
import com.example.cardiograph.R;

/**
 * 用户登录
 * 
 */
public class LoginActivity extends Activity implements OnClickListener {

	/** 登录手机号 */
	private ImageView phoneText;
	private EditText phoneEdit;

	/** 登录密码 */
	private ImageView passwordText;
	private EditText passwordEdit;

	/** 是否记录登录手机号 */
	private CheckBox checkkeep;

	/** 找回登录密码 */
	private TextView findPWText;

	/** 注册 */
	private TextView registerBtn;
	/** 登录 */
	private BtnWithTopLine loginBtn;

	/** 显示加载dialog */
	private final int DIALOG_SHOW = 0;
	/** 去掉加载dialog */
	private final int DIALOG_CANCEL = 1;
	/** 账户未注册 */
	private final int NOT_REGISTER = 2;
	/** 密码错误 */
	private final int ERROR_PASSWORD = 3;
	/** 签到成功 */
	private final int CHECK_IN_OK = 4;
	/** 错误提示 */
	private final int ERROR_MSG = 5;
	/** 登录失败 */
	private final int LOGIN_FAIL = 6;

	/** 跳转到收单 */
	private final int TO_SHOUDAN = 7;

	/** 升级提醒 */
	private static final int MESSAGE_WHAT_SHOW_UPDATE_DIALOG = 8;

	private String userToken = null;
	private String info = null;
	private String phone = null;
	private String password = null;
	private SharedPreferences sp;
	private Editor editor;
	public static LoginActivity instance = null;
	protected NavigationBar navigationBar; // 导航条
	private NavigationBar.OnNavBarClickListener onNavBarClickListener = new NavigationBar.OnNavBarClickListener() {
		@Override
		public void onNavItemClick(NavigationBar.NavigationBarItem navBarItem) {
			if (navBarItem == NavigationBar.NavigationBarItem.back) {
				finish();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		instance = this;

		initUI();
	}

	protected void initUI(){
		// 标题
		navigationBar = (NavigationBar) findViewById(R.id.id_navigation_bar);
		navigationBar.setTitle(R.string.login);
//		navigationBar.setBackBtnVisibility(View.GONE);
		navigationBar.setOnNavBarClickListener(onNavBarClickListener);

		// 用户 名
		phoneText = (ImageView) findViewById(R.id.id_phone_text);
		phoneEdit = (EditText) findViewById(R.id.id_phone_edit);
		// 密码
		passwordText = (ImageView) findViewById(R.id.id_passwor_text);
		passwordEdit = (EditText) findViewById(R.id.id_passwor_edit);
		passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		LengthFilter[] filters2 = { new LengthFilter(20) };
		passwordEdit.setFilters(filters2);
		passwordEdit.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {

				if (actionId == EditorInfo.IME_ACTION_GO) {
					InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					if (imm.isActive()) {
						imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);	// 隐藏软键盘
					}

					login();

					return true;
				}

				return false;
			}
		});
		checkkeep = (CheckBox) findViewById(R.id.keep_phone);
		// 找回密码
		findPWText = (TextView) findViewById(R.id.find_login_PW);
		// 注册，登录
		registerBtn = (TextView) findViewById(R.id.tv_register);
		loginBtn = (BtnWithTopLine) findViewById(R.id.login);

		// 是否记住账号初始化
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		editor = sp.edit();
		String userName = sp.getString("userName", "");
		String password = sp.getString("password", "");
		if (!TextUtils.isEmpty(userName)) {// 如果记住账号，初始化时自动用户名
			phoneEdit.setText(userName);
			phoneEdit.setSelection(userName.length());
			if(!TextUtils.isEmpty(password)){
				passwordEdit.setText(new String(Base64.decode(password.getBytes(),0)));
				checkkeep.setChecked(true);
			}
		}		
		
		phoneEdit.setText("icc");
		passwordEdit.setText("jcdlsjzx");
		
		findPWText.setOnClickListener(this);
		registerBtn.setOnClickListener(this);
		loginBtn.setOnClickListener(this);

		//
		long key_time = 0;
		if(!TextUtils.isEmpty(sp.getString("key_time", ""))){
			key_time = Long.parseLong(sp.getString("key_time", ""));
		}
		if(System.currentTimeMillis() - key_time < 72*3600*1000){//72小时
			Intent intent = new Intent(this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("userName", phone);
			startActivity(intent);
			recordUserName();
			finish();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.find_login_PW:// 找回密码
			findPWText.requestFocus();
			findPWText.setFocusable(true);
//			startActivity(new Intent(this, ResetPassword1_PhoneNumberActivity.class));
			break;
			
		case R.id.tv_register:// 跳转到注册
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivityForResult(intent, 1);
			break;
			
		case R.id.login:// 登录
			login();
			break;
		}
	}
	
	private void login(){
		if (isInputValid()) {
			IMEUtil.hideIme(this);
			startLogin();
		}
	}

	/**
	 * 记住账号
	 */
	private void recordUserName(){
		String userName = phoneEdit.getText().toString();
		editor.putString("userName", userName);
		if (checkkeep.isChecked()) {// checkbox勾选记住账号
			String password = passwordEdit.getText().toString();
			editor.putString("password", new String(Base64.encode(password.getBytes(), 0)));
		} else {
			editor.putString("password", "");
		}
		editor.commit();
	}

	/**
	 * 登录操作
	 * 
	 */
	private void startLogin() {
//		User user = new User(this, phone, password);
//		Map<String, Object> map = user.login();
//		if(Integer.parseInt(map.get("num").toString())>0){
//			Toast.makeText(this, "登陆成功！", Toast.LENGTH_LONG).show();
//			Intent intent = new Intent(this, MainActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			intent.putExtra("code", phone);
//			intent.putExtra("userName", map.get("userName").toString());
//			startActivity(intent);
//			recordUserName();
//			finish();
//		}else{
//			Toast.makeText(this, "账号或密码错误，请重新输入。", Toast.LENGTH_LONG).show();
//		}
		List<NameValuePair> nvpList = new ArrayList<NameValuePair>();
		nvpList.add(new BasicNameValuePair("action", "auth"));
		nvpList.add(new BasicNameValuePair("username", phone));
		nvpList.add(new BasicNameValuePair("password", password));
		new RequestTask(Constance.URL_LOGIN, Constance.POST, nvpList, new ResponseCallBack() {
			@Override
			public void onExecuteResult(String response) {
				try {
					Log.d("yjx",response);
					JSONObject jsonObject = new JSONObject(response);
					String result = jsonObject.getString("result");
					if(result.equals(Constance.RESULT_OK)){
						String key = jsonObject.getString("key");
						editor.putString("key", key);
						editor.putString("key_time", System.currentTimeMillis()+"");
						Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(LoginActivity.this, MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra("userName", phone);
						startActivity(intent);
						recordUserName();
						finish();
					}else{
						Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).execute();
		
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				ResultForService result = null;
//				String retCode = null;
//				Object retData = null;
//				try {
//					manager = CommonServiceManager.getInstance();
//					defaultHandler.sendEmptyMessage(DIALOG_SHOW);
//					result = manager.getUserLoginState(phone,
//							RSAEncrypt.getInstance(RSAEncrypt.PK_LOGIN).encryptData(password), getSignatureMd5());
//					retCode = result.retCode;
//					retData = result.retData;
//					String errMsg = result.errMsg;
//					info = errMsg;
//					if (retCode.equals(Parameters.successRetCode)) {
//						StatisticsManager.getInstance(LoginActivity.this).onEvent(StatisticsManager.LoginUserCount, "",StatisticsManager.login, 
//								StatisticsManager.DESC_login, StatisticsManager.ORIGIN_LOGIN);
//						// 登录成功,获取用户信息。
//						JSONObject userInfo = (JSONObject) retData;
//
//						Parameters.user.token = userInfo.getString("userToken");
//						Parameters.user.userName = phone;// 赋值
//						Parameters.user.walletTerminalNO = userInfo.getString("psamNo");// 钱包虚拟终端Id
//						if (userInfo.has("userId")) {
//							Parameters.user.userId = userInfo.getString("userId");
//						}
//
//						Parameters.user.giftUserNo = userInfo.getString("giftno");// 红包账户Id
//						Parameters.user.giftNum = userInfo.optInt("giftnum", 0);// 红包总数(不是种类数)
//						// 处理钱包余额
//						double balance = MAC.FenToYuan(userInfo.getString("amount"));
//						// WalletMoneyManager.getInstance().update(balance);
//
//						Parameters.user.tamout = MAC.FenToYuan(userInfo.getString("tamount")); // 可提现余额
//						Parameters.user.walletFreePwdAmount = MAC.FenToYuan(userInfo.getString("limitAmount"));// 免输支付密码限额(分)
//						Parameters.user.isExistPinkey = userInfo.optInt("hasPwd", 0); // 是否存在支付密码
//						Parameters.user.pinkeyState = userInfo.optInt("isAsk", 0); // 是否免支付密码
//						Parameters.user.isState = userInfo.optInt("status", 0); // 激活状态
//						Parameters.user.walletMainPan = userInfo.getString("pan"); // 钱包主账户Pan
//						Parameters.user.custlev = userInfo.optString("custlev", "0"); // 客户级别
//						Parameters.user.couponUrl = userInfo.optString("couponUrl"); // 优惠券url
//
//						ShouDanMainActivity.ifReloadType2 = true;
//
//						userToken = Parameters.user.token;
//
//						// 设置标记，告诉主界面更新消息总数
//
//						ShoudanService superServce = ShoudanService.getInstance();
//						Parameters.merchantInfo = superServce.queryShoudanRegisterInfo();
//						defaultHandler.sendEmptyMessage(TO_SHOUDAN);
//
//						defaultHandler.sendEmptyMessage(DIALOG_CANCEL);
//						return;
//					} else if (retCode.equals(Parameters.userNoExists)) {// 没有注册
//						defaultHandler.sendEmptyMessage(NOT_REGISTER);
////						setResult(0);
////						finish();
//					} else if (retCode.equals(Parameters.userOrPWError)) {// 登录失败--密码错误
//						defaultHandler.sendEmptyMessage(ERROR_PASSWORD);
////						setResult(-1);
////						finish();
//					} else {
//						defaultHandler.sendEmptyMessage(ERROR_MSG);
////						setResult(-2);
////						finish();
//					}
//				} catch (Exception e) {
//					// 登录异常了,使用异常过滤器处理异常
//					if (Parameters.debug)
//						Log.e("", "Login Error", e);
//					if (ExceptionHandler.filter(context, defaultHandler, e)) {
//						defaultHandler.sendEmptyMessage(DIALOG_CANCEL);
//					} else {
//						defaultHandler.sendEmptyMessage(LOGIN_FAIL);
//					}
//				}
//			}
//		}).start();
	}

	protected boolean isInputValid() {// 输入检查
		phone = phoneEdit.getText().toString().trim();
		password = passwordEdit.getText().toString().trim();
		if (phone.length() == 0) {// 手机号码是否为11为
			// AlertDialog dialog =
			// DialogCreator.createInputPromptDialog(this,R.string.phone_illegal_title,R.string.phone_illegal_content);
			// dialog.show();
			Util.toastCenter(this,"请输入用户名");
			return false;
		} else if (password.length() < 6 || password.length() > 20) {// 密码长度6到20位
			// AlertDialog dialog =
			// DialogCreator.createInputPromptDialog(this,R.string.PW_illegal_title,
			// R.string.PW_illegal_content1);
			// dialog.show();
			Util.toastCenter(this,R.string.PW_illegal_content1);
			return false;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			if (requestCode == 1) {// 注册后返回注册手机号填入用户名输入框
				phoneEdit.setText(data.getStringExtra("code"));
			}
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}
