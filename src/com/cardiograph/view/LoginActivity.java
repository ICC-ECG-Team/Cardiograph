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
 * �û���¼
 * 
 */
public class LoginActivity extends Activity implements OnClickListener {

	/** ��¼�ֻ��� */
	private ImageView phoneText;
	private EditText phoneEdit;

	/** ��¼���� */
	private ImageView passwordText;
	private EditText passwordEdit;

	/** �Ƿ��¼��¼�ֻ��� */
	private CheckBox checkkeep;

	/** �һص�¼���� */
	private TextView findPWText;

	/** ע�� */
	private TextView registerBtn;
	/** ��¼ */
	private BtnWithTopLine loginBtn;

	/** ��ʾ����dialog */
	private final int DIALOG_SHOW = 0;
	/** ȥ������dialog */
	private final int DIALOG_CANCEL = 1;
	/** �˻�δע�� */
	private final int NOT_REGISTER = 2;
	/** ������� */
	private final int ERROR_PASSWORD = 3;
	/** ǩ���ɹ� */
	private final int CHECK_IN_OK = 4;
	/** ������ʾ */
	private final int ERROR_MSG = 5;
	/** ��¼ʧ�� */
	private final int LOGIN_FAIL = 6;

	/** ��ת���յ� */
	private final int TO_SHOUDAN = 7;

	/** �������� */
	private static final int MESSAGE_WHAT_SHOW_UPDATE_DIALOG = 8;

	private String userToken = null;
	private String info = null;
	private String phone = null;
	private String password = null;
	private SharedPreferences sp;
	private Editor editor;
	public static LoginActivity instance = null;
	protected NavigationBar navigationBar; // ������
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
		// ����
		navigationBar = (NavigationBar) findViewById(R.id.id_navigation_bar);
		navigationBar.setTitle(R.string.login);
//		navigationBar.setBackBtnVisibility(View.GONE);
		navigationBar.setOnNavBarClickListener(onNavBarClickListener);

		// �û� ��
		phoneText = (ImageView) findViewById(R.id.id_phone_text);
		phoneEdit = (EditText) findViewById(R.id.id_phone_edit);
		// ����
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
						imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);	// ���������
					}

					login();

					return true;
				}

				return false;
			}
		});
		checkkeep = (CheckBox) findViewById(R.id.keep_phone);
		// �һ�����
		findPWText = (TextView) findViewById(R.id.find_login_PW);
		// ע�ᣬ��¼
		registerBtn = (TextView) findViewById(R.id.tv_register);
		loginBtn = (BtnWithTopLine) findViewById(R.id.login);

		// �Ƿ��ס�˺ų�ʼ��
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		editor = sp.edit();
		String userName = sp.getString("userName", "");
		String password = sp.getString("password", "");
		if (!TextUtils.isEmpty(userName)) {// �����ס�˺ţ���ʼ��ʱ�Զ��û���
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
		if(System.currentTimeMillis() - key_time < 72*3600*1000){//72Сʱ
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
		case R.id.find_login_PW:// �һ�����
			findPWText.requestFocus();
			findPWText.setFocusable(true);
//			startActivity(new Intent(this, ResetPassword1_PhoneNumberActivity.class));
			break;
			
		case R.id.tv_register:// ��ת��ע��
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivityForResult(intent, 1);
			break;
			
		case R.id.login:// ��¼
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
	 * ��ס�˺�
	 */
	private void recordUserName(){
		String userName = phoneEdit.getText().toString();
		editor.putString("userName", userName);
		if (checkkeep.isChecked()) {// checkbox��ѡ��ס�˺�
			String password = passwordEdit.getText().toString();
			editor.putString("password", new String(Base64.encode(password.getBytes(), 0)));
		} else {
			editor.putString("password", "");
		}
		editor.commit();
	}

	/**
	 * ��¼����
	 * 
	 */
	private void startLogin() {
//		User user = new User(this, phone, password);
//		Map<String, Object> map = user.login();
//		if(Integer.parseInt(map.get("num").toString())>0){
//			Toast.makeText(this, "��½�ɹ���", Toast.LENGTH_LONG).show();
//			Intent intent = new Intent(this, MainActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			intent.putExtra("code", phone);
//			intent.putExtra("userName", map.get("userName").toString());
//			startActivity(intent);
//			recordUserName();
//			finish();
//		}else{
//			Toast.makeText(this, "�˺Ż�����������������롣", Toast.LENGTH_LONG).show();
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
						Toast.makeText(LoginActivity.this, "��¼�ɹ���", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(LoginActivity.this, MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra("userName", phone);
						startActivity(intent);
						recordUserName();
						finish();
					}else{
						Toast.makeText(LoginActivity.this, "��¼ʧ�ܣ�", Toast.LENGTH_LONG).show();
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
//						// ��¼�ɹ�,��ȡ�û���Ϣ��
//						JSONObject userInfo = (JSONObject) retData;
//
//						Parameters.user.token = userInfo.getString("userToken");
//						Parameters.user.userName = phone;// ��ֵ
//						Parameters.user.walletTerminalNO = userInfo.getString("psamNo");// Ǯ�������ն�Id
//						if (userInfo.has("userId")) {
//							Parameters.user.userId = userInfo.getString("userId");
//						}
//
//						Parameters.user.giftUserNo = userInfo.getString("giftno");// ����˻�Id
//						Parameters.user.giftNum = userInfo.optInt("giftnum", 0);// �������(����������)
//						// ����Ǯ�����
//						double balance = MAC.FenToYuan(userInfo.getString("amount"));
//						// WalletMoneyManager.getInstance().update(balance);
//
//						Parameters.user.tamout = MAC.FenToYuan(userInfo.getString("tamount")); // ���������
//						Parameters.user.walletFreePwdAmount = MAC.FenToYuan(userInfo.getString("limitAmount"));// ����֧�������޶�(��)
//						Parameters.user.isExistPinkey = userInfo.optInt("hasPwd", 0); // �Ƿ����֧������
//						Parameters.user.pinkeyState = userInfo.optInt("isAsk", 0); // �Ƿ���֧������
//						Parameters.user.isState = userInfo.optInt("status", 0); // ����״̬
//						Parameters.user.walletMainPan = userInfo.getString("pan"); // Ǯ�����˻�Pan
//						Parameters.user.custlev = userInfo.optString("custlev", "0"); // �ͻ�����
//						Parameters.user.couponUrl = userInfo.optString("couponUrl"); // �Ż�ȯurl
//
//						ShouDanMainActivity.ifReloadType2 = true;
//
//						userToken = Parameters.user.token;
//
//						// ���ñ�ǣ����������������Ϣ����
//
//						ShoudanService superServce = ShoudanService.getInstance();
//						Parameters.merchantInfo = superServce.queryShoudanRegisterInfo();
//						defaultHandler.sendEmptyMessage(TO_SHOUDAN);
//
//						defaultHandler.sendEmptyMessage(DIALOG_CANCEL);
//						return;
//					} else if (retCode.equals(Parameters.userNoExists)) {// û��ע��
//						defaultHandler.sendEmptyMessage(NOT_REGISTER);
////						setResult(0);
////						finish();
//					} else if (retCode.equals(Parameters.userOrPWError)) {// ��¼ʧ��--�������
//						defaultHandler.sendEmptyMessage(ERROR_PASSWORD);
////						setResult(-1);
////						finish();
//					} else {
//						defaultHandler.sendEmptyMessage(ERROR_MSG);
////						setResult(-2);
////						finish();
//					}
//				} catch (Exception e) {
//					// ��¼�쳣��,ʹ���쳣�����������쳣
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

	protected boolean isInputValid() {// ������
		phone = phoneEdit.getText().toString().trim();
		password = passwordEdit.getText().toString().trim();
		if (phone.length() == 0) {// �ֻ������Ƿ�Ϊ11Ϊ
			// AlertDialog dialog =
			// DialogCreator.createInputPromptDialog(this,R.string.phone_illegal_title,R.string.phone_illegal_content);
			// dialog.show();
			Util.toastCenter(this,"�������û���");
			return false;
		} else if (password.length() < 6 || password.length() > 20) {// ���볤��6��20λ
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
			if (requestCode == 1) {// ע��󷵻�ע���ֻ��������û��������
				phoneEdit.setText(data.getStringExtra("code"));
			}
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}
