package com.cardiograph.view;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.cardiograph.constance.Constance;
import com.cardiograph.control.UserOnClickListener;
import com.cardiograph.service.UartService;
import com.cardiograph.util.Tools;
import com.example.cardiograph.R;
import com.qihoo.linker.logcollector.LogCollector;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.ParcelUuid;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/************************************************************
 *  ����ժҪ	���ĵ������棬ʵ���������ӹ���
 *
 *  ����	��Ҷ����
 *  ����ʱ��	��2014-12-21 ����4:50:01 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2014-12-21 ����4:50:01 	�޸��ˣ�
 *  	����	:
 ************************************************************/
/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��Ҷ����
 *  ����ʱ��	��2014-12-21 ����4:54:18 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2014-12-21 ����4:54:18 	�޸��ˣ�
 *  	����	:
 ************************************************************/
public class MainActivity extends Activity {
	private LinearLayout llStart,llProfile,llConnect,llUpload;
	private ImageView ibStart,ibProfile,ibHelp,ibInfo,ibHistory,
	ibConnect,ibManual,ibAutomatic,ibUpload,ibDisconnect;
	private Button btnBLE;
	private TextView tvStart,tvUserName,tvSex,tvAge,tvSymptom,
	tvDateTime,tvInfo,tvBPM,tvConnect;
	private ImageView ivBattery,ivPower,ivSound,ivInfo, ivSetting;
	private SurfaceView sfv;
	private SurfaceHolder sfh;
	private AudioManager mAudioManager;
	private Handler handler;
	private BroadcastReceiver batteryReceiver;
    /**
     *�Ƿ��һ�λ��ĵ�ͼ
     */
    public static boolean isFirst = true;
    /**
     *�Ƿ��ƶ�
     */
    public static boolean isMove = false;
    /**
     * �߳̿���
     */
    public static boolean isLoop = false;
    public static boolean isShow = true;
    public static boolean isLstRec = true;
    public static volatile boolean isDraw = false;
    public static int drawflag = Constance.DRAW_LST;
    public boolean isAdd = false;
    private ProgressDialog pd;
    private List<List<Integer>> lstBpm = new ArrayList<List<Integer>>();
    private List<Integer> lst = new ArrayList<Integer>();
    private List<Integer> lstData = new ArrayList<Integer>();
    private List<Integer> lstBuffer = new ArrayList<Integer>();
    private List<Integer> lstMax = new ArrayList<Integer>();
    private List<Integer> lstCalBpm = new ArrayList<Integer>();
    private int[] point = new int[360];
    private int width = 0, height = 0,length = 0;
    private int count = 0, min = 0, max = 0,avg = 0, bpm = 0, maxData = 0, index = 0; 
    private int frontMax = 0,frontMin = 0;
    private double t=0;
    //ÿ�������ݵĸ���
    public static int dataNum = 406;
    public static String dataDateTime;
/*    static{
    	System.loadLibrary("JNI_Interface");
    }*/
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        //��ʼ�����˶���
//		try {
//			filter = new MatlabFilter();
//		} catch (MWException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}  
        initData();
		initComponent();
		initView();
		service_init();
		registerListener();
        LogCollector.setDebugMode(true);
        LogCollector.init(this, "http://121.41.41.54:8801/", null);
	}

	private void initData() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");//"yyyy-MM-dd HH:mm:ss" 
		dataDateTime = format.format(new Date());
//		dataDateTime = new Date().getTime()/1000+"";
        File file = new File(Environment.getExternalStorageDirectory() + "/data");
        // �ж��ļ�Ŀ¼�Ƿ����
        if (!file.exists()) {
            file.mkdir();
        }
    }

	/**
	 * 
	 *  �������� : initComponent
	 *  �������� : ��ʼ���ؼ�
	 *  ����������ֵ˵����
	 *
	 *  �޸ļ�¼��
	 *  	���� ��2014-12-21 ����4:54:36	�޸��ˣ�yjx
	 *  	����	��
	 *
	 */
	private void initComponent(){
		llStart = (LinearLayout) findViewById(R.id.llStart);
		llProfile = (LinearLayout) findViewById(R.id.llProfile);
		llConnect = (LinearLayout) findViewById(R.id.llConnect);
		llUpload = (LinearLayout) findViewById(R.id.llUpload);
		tvStart = (TextView) findViewById(R.id.tvStart);
		ibStart = (ImageView) findViewById(R.id.ibStart);
		ibProfile = (ImageView) findViewById(R.id.ibProfile);
//		ibHelp = (ImageButton) findViewById(R.id.ibHelp);
//		ibInfo = (ImageButton) findViewById(R.id.ibInfo);
//		ibHistory = (ImageButton) findViewById(R.id.ibHistory);
		ibConnect = (ImageView) findViewById(R.id.ibConnect);
		ibUpload = (ImageView) findViewById(R.id.ibUpload);
//		ibDisconnect = (ImageButton) findViewById(R.id.ibDisconnect);
		tvConnect = (TextView) findViewById(R.id.tvConnect);
//		ibManual = (ImageButton) findViewById(R.id.ibManual);
//		ibAutomatic = (ImageButton) findViewById(R.id.ibAutomatic);
		btnBLE = (Button) findViewById(R.id.btnBLE);
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		tvSex = (TextView) findViewById(R.id.tvSex);
		tvAge = (TextView) findViewById(R.id.tvAge);
		tvSymptom = (TextView) findViewById(R.id.tvSymptom);
		tvDateTime = (TextView) findViewById(R.id.tvDateTime);
		tvInfo = (TextView) findViewById(R.id.tvInfo);
		tvBPM = (TextView) findViewById(R.id.tvBPM);
		ivBattery = (ImageView) findViewById(R.id.ivBattery);
		ivPower = (ImageView) findViewById(R.id.ivPower);
		ivSound = (ImageView) findViewById(R.id.ivSound);
		ivInfo = (ImageView) findViewById(R.id.ivInfo);
		ivSetting = (ImageView) findViewById(R.id.ivSetting);
		sfv = (SurfaceView) findViewById(R.id.sfv);
		//SurfaceHolder����ʾһ��surface�ĳ���ӿڣ�ʹ����Կ���surface�Ĵ�С�͸�ʽ�� �Լ���surface�ϱ༭���أ��ͼ���surace�ĸı䡣����ӿ�ͨ��ͨ��SurfaceView��ʵ�֡�
		sfh = sfv.getHolder();
		// SurfaceHolder �Ļص��ӿ�
		sfh.addCallback(new Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub

			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
//				drawBackground();
//				drawBackground();
				drawGrids();
			}
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				// TODO Auto-generated method stub

			}
		});
		
		tvUserName.setText("������ "+getIntent().getStringExtra("userName"));
		tvSex.setText("�Ա� ��");
		tvAge.setText("���䣺 22��");
		tvSymptom.setText("֢״�� ����");
    }

	private void initView() {
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case Constance.MESSAGE_DATETIME_AUDIO:
					tvDateTime.setText(msg.obj.toString());
//					int current = mAudioManager.getStreamVolume( AudioManager.STREAM_SYSTEM );
					if(msg.arg1>0){
						ivSound.setImageResource(R.drawable.led_on);
					}else{
						ivSound.setImageResource(R.drawable.led_off);
					}
					break;
				case Constance.MESSAGE_CALCULATE_BPM:
					if (bpm < 0) {
						bpm = 0;
					}
					tvBPM.setText(bpm + "bpm");
					break;
				}
			}
		};
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				while(true){
					SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//"yyyy-MM-dd  HH:mm:ss"
					String dateTime=format.format(new Date());
					int current = mAudioManager.getStreamVolume( AudioManager.STREAM_SYSTEM );
					Message msg =new Message();
					msg.obj = dateTime;
					msg.arg1 = current;
					msg.what = Constance.MESSAGE_DATETIME_AUDIO;
					handler.sendMessage(msg);
				}
			}
		}.start();
		
		batteryReceiver=new BroadcastReceiver(){  
		 
		        @Override 
		        public void onReceive(Context context, Intent intent) {  
		        	//  level��%���ǵ�ǰ������  
		            int level = intent.getIntExtra("level", 0); 
//		            Toast.makeText(MainActivity.this, level+"%", Toast.LENGTH_LONG);
		            if(level>=0&&level<25){
		            	ivBattery.setBackgroundResource(R.drawable.battery1);
		            }else if(level>=25&&level<50){
		            	ivBattery.setBackgroundResource(R.drawable.battery2);
		            }else if(level>=50&&level<75){
		            	ivBattery.setBackgroundResource(R.drawable.battery3);	
		            }else{
		            	ivBattery.setBackgroundResource(R.drawable.battery4);
		            }
		            System.out.println(level+"%");
		        }  
		};  
		registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}
	
	private void registerListener() {
		llStart.setOnClickListener(new UserOnClickListener(this,ibStart, tvStart, ivPower,tvBPM,sfv,sfh));
		llProfile.setOnClickListener(new UserOnClickListener(this));
//		ibHelp.setOnClickListener(new UserOnClickListener(this));
//		ibInfo.setOnClickListener(new UserOnClickListener(this,tvInfo,tvBPM,ivInfo));
//		ibHistory.setOnClickListener(new UserOnClickListener(this));
		llConnect.setOnClickListener(new UserOnClickListener(this, mBtAdapter, tvConnect));
//		ibManual.setOnClickListener(new UserOnClickListener(this,tvBPM,sfv,sfh));
//		ibAutomatic.setOnClickListener(new UserOnClickListener(this,tvBPM,sfv,sfh));
		btnBLE.setOnClickListener(new UserOnClickListener(this, mBtAdapter, btnBLE));
		llUpload.setOnClickListener(new UserOnClickListener(this));
//		ibDisconnect.setOnClickListener(new UserOnClickListener(this));
		ivSetting.setOnClickListener(new UserOnClickListener(this));
	}
	
	
	public List<Integer> getLst() {
		return lst;
	}

	public List<Integer> getLstData() {
		return lstData;
	}
	
	public void setLst(List<Integer> lst) {
		this.lst = lst;
	}

	private void drawBackground(){
		Canvas canvas = null;
		// ����һ�� Canvas ������� Canvas ���󣬾��������� Surface �ϻ�ͼʹ�õ�
		canvas = sfh.lockCanvas();
		// ��ȡ res Ŀ¼�µ� Bitmap ����
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.history_paper_right);
		//����һ�� Matrix ����
		Matrix matrix = new Matrix();
		//�������ű���
		matrix.setScale((float)sfv.getWidth() / bitmap.getWidth(), (float)sfv.getHeight() / bitmap.getHeight());
		//�õ�����ͼƬ(����һ)
		Bitmap bitmapScale = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		
		//�õ�����ͼƬ(������)
//		Bitmap bitmapScale = Bitmap.createScaledBitmap(bitmap, sfv.getWidth(), sfv.getHeight(), true);
		
		//��ͼƬ
		canvas.drawBitmap(bitmapScale, 0, 0, new Paint());
		// ���� surface �ĵ�ǰ�༭�����������ʾ����Ļ��
		sfh.unlockCanvasAndPost(canvas);
	}
	
	private void drawGrids(){
//		int width = 0, height = 0,length = 0;
//		height = Tools.dip2px(120, this);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		System.out.println(dm.widthPixels+"--"+dm.heightPixels+"--"+dm.density);
		System.out.println(Environment.getExternalStorageDirectory());
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			width = (int) (dm.heightPixels*540.0/720);
			height = (int) (dm.widthPixels*240.0/1280);
		} else {
			width = (int) (dm.widthPixels*1080.0/1280);
			height = (int) (dm.heightPixels*240.0/720);
		}
		length = (int) (height/20.0);
		Canvas canvas = null;
		// ����һ�� Canvas ������� Canvas ���󣬾��������� Surface �ϻ�ͼʹ�õ�
		canvas = sfh.lockCanvas();
		Paint p = new Paint();
		p.setColor(getResources().getColor(R.color.sf_green_grid));
		p.setAntiAlias(true);
		p.setStrokeWidth(2);
		
		Paint px = new Paint();
		px.setColor(getResources().getColor(R.color.sf_green_grid));
		px.setAntiAlias(true);
		px.setStrokeWidth(1);
		px.setStyle(Paint.Style.STROKE);
		px.setPathEffect(new DashPathEffect(new float[]{5,5,5,5},1));
		
		Paint bp = new Paint();
		bp.setColor(getResources().getColor(R.color.sf_black_bg));
		canvas.drawRect(0, 0, width, height, bp);

		// ������
		for (int i = 0; i < 20+1; i++) {
			if(i%5==0){
				p.setStrokeWidth(2);
				canvas.drawLine(0, i*length, width, i*length, p);
			}else{
				p.setStrokeWidth(2);
				canvas.drawLine(0, i*length, width, i*length, px);
			}
//			canvas.drawLine(0, i*length, width, i*length, p);
		}
		// ������
		for (int i = 0; i < width / length+1; i++) {
			if(i%5==0){
				p.setStrokeWidth(2);
				canvas.drawLine(i*length, 0, i*length, height, p);
			}else{
				p.setStrokeWidth(2);
				canvas.drawLine(i*length, 0, i*length, height, px);
			}
//			canvas.drawLine(i*length, 0, i*length, height, p);
		}
		// ���� surface �ĵ�ǰ�༭�����������ʾ����Ļ��
		sfh.unlockCanvasAndPost(canvas);
	}
	
	public static final String TAG = "nRFUART";
    public static UartService mService = null;
    public static BluetoothDevice mDevice = null;
    private BluetoothAdapter mBtAdapter = null;
    private int mState = UART_PROFILE_DISCONNECTED;
    private static final int UART_PROFILE_CONNECTED = 20;
    private static final int UART_PROFILE_DISCONNECTED = 21;
    //UART service connected/disconnected
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
        	    isLoop = true;
        		mService = ((UartService.LocalBinder) rawBinder).getService();
        		Log.d(TAG, "onServiceConnected mService= " + mService);
        		if (!mService.initialize()) {
                    Log.e(TAG, "Unable to initialize Bluetooth");
                    finish();
                }

        }

        public void onServiceDisconnected(ComponentName classname) {
       ////     mService.disconnect(mDevice);
        		mService = null;
        }
    };
    
    //�����ʼ��
    private void service_init(){
        Intent bindIntent = new Intent(this, UartService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
  
        LocalBroadcastManager.getInstance(this).registerReceiver(UARTStatusChangeReceiver, makeGattUpdateIntentFilter());
    }
    
    //�㲥������
    private final BroadcastReceiver UARTStatusChangeReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            final Intent mIntent = intent;
           //*********************//
            if (action.equals(UartService.ACTION_GATT_CONNECTED)) {
            	 runOnUiThread(new Runnable() {
                     public void run() {
                         	String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                             Log.d(TAG, "UART_CONNECT_MSG");
                             tvConnect.setText(R.string.ble_disconnect);
                             tvStart.setText(R.string.pause);
                             mState = UART_PROFILE_CONNECTED;
                     		 SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");//"yyyy-MM-dd HH:mm:ss" 
                    		 dataDateTime = format.format(new Date());
//                    		 getUUID();
                     }
            	 });
            }
           
          //*********************//
            if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
            	 runOnUiThread(new Runnable() {
                     public void run() {
                    	 	 String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                             Log.d(TAG, "UART_DISCONNECT_MSG");
                             tvConnect.setText(R.string.ble_connect);
                             tvStart.setText(R.string.start);
                             mState = UART_PROFILE_DISCONNECTED;
                             mService.close();
                            //setUiState();
                         
                     }
                 });
            }
            
          
          //*********************//
            if (action.equals(UartService.ACTION_GATT_SERVICES_DISCOVERED)) {
             	 mService.enableTXNotification();
            }
          //*********************//
            if (action.equals(UartService.ACTION_DATA_AVAILABLE)) {
                 final byte[] txValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);
                 runOnUiThread(new Runnable() {
                     public void run() {
                         try {
                 			if (isShow) {
                 				ibStart.setImageResource(R.drawable.ic_media_pause);
                				pd = new ProgressDialog(MainActivity.this);
                				pd.setIcon(android.R.drawable.ic_dialog_email);
                				pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                				pd.setMax(100);
                				pd.setMessage("��ȡ�����У����Ե�......");
                				pd.setCancelable(true);
                				pd.show();
                				isShow = false;
                			}
							if (isLoop) {
								synchronized (this) {
									BLEDataReceiveHandle1(txValue);
									if (lstBpm.size() > 0) {
										if (!isDraw) {
											isDraw = true;
											System.out.println("+++++++++++++++");
											//										Tools.getInstance().writer(Environment.getExternalStorageDirectory() + "/data/"+dataDateTime+"lstBpm.txt", lstBpm.get(index));
											Tools.getInstance().drawWaveform1(MainActivity.this, sfh, lstBpm.get(0));
											lstBpm.remove(0);
//											index++;
//											Tools.getInstance().drawWaveform2(MainActivity.this, sfh, lst);
//											lst.clear();
										}
										pd.cancel();
									}
								}
								
							}
//                 			if(isLstRec){
//                 				BLEDataReceiveHandle1(txValue);
//                 				if(lst.size() == 216){
//                 					isLstRec = false;
//                 				}
//                 			}else{
//                 				BLEDataReceiveHandleBuffer(txValue);
//                 				if(lstBuffer.size() == 216){
//                 					isLstRec = true;
//                 				}
//                 			}
//                        	 if(isFirst){
//              					if (lst.size() == 216 && lstBuffer.size() == 216 ) {
//            						if(isLoop){
//            							System.out.println("+++++++++++++++");
//            							Tools.getInstance().drawWaveform1(MainActivity.this,sfh, lst);
//            						}
//            						pd.cancel();
//            						lst = new ArrayList<Integer>();
//            						isFirst = false;
//            					}
//                        	 }
//                    		 if(!isDraw){
//                    			isDraw = true;
//                    			switch (drawflag) {
// 								case Constance.DRAW_LST:
// 									Tools.getInstance().drawWaveform1(MainActivity.this, sfh, lst);
// 									lst = new ArrayList<Integer>();
// 									break;
// 								case Constance.DRAW_LSTBUFFER:
// 									Tools.getInstance().drawWaveform1(MainActivity.this, sfh, lstBuffer);
// 									lstBuffer = new ArrayList<Integer>();
// 									break;
// 								}
//                    		 }
                         } catch (Exception e) {
                             Log.e(TAG, e.toString());
                         }
                     }

					
                 });

             }
           //*********************//
            if (action.equals(UartService.DEVICE_DOES_NOT_SUPPORT_UART)){
            	showMessage("Device doesn't support UART. Disconnecting");
            	mService.disconnect();
            }
            
            
        }
    };
    
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UartService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(UartService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(UartService.DEVICE_DOES_NOT_SUPPORT_UART);
        return intentFilter;
    }
    
    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

        case Constance.REQUEST_SELECT_DEVICE:
        	//When the DeviceListActivity return, with the selected device address
            if (resultCode == Activity.RESULT_OK && data != null) {
                String deviceAddress = data.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
                mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress);
                Log.d(TAG, "... onActivityResultdevice.address==" + mDevice + "mserviceValue" + mService);
                ((TextView) findViewById(R.id.deviceName)).setText(mDevice.getName()+ " - connecting");
                mService.connect(deviceAddress);

            }
            break;
        case Constance.REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Bluetooth has turned on ", Toast.LENGTH_SHORT).show();

            } else {
                // User did not enable Bluetooth or an error occurred
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, "Problem in BT Turning ON ", Toast.LENGTH_SHORT).show();
                finish();
            }
            break;
        default:
            Log.e(TAG, "wrong request code");
            break;
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
//        isMove = true;
        index = 0;
        txValueIndex = 0;
        lstBpm.clear();
        if (!mBtAdapter.isEnabled()) {
            Log.i(TAG, "onResume - BT not enabled yet");
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, Constance.REQUEST_ENABLE_BT);
        }
//        service_init();
 
    }
    
    @Override
    public void onBackPressed() {
        if (mState == UART_PROFILE_CONNECTED) {
			MainActivity.isLoop = false;
			MainActivity.isDraw = false;
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            showMessage("nRFUART's running in background.\n             Disconnect to exit");
            System.exit(0);
        }
        else {
            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(R.string.popup_title)
            .setMessage(R.string.popup_message)
            .setPositiveButton(R.string.popup_yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                    ActivityManager am = (ActivityManager)getSystemService (Context.ACTIVITY_SERVICE);
//                    am.restartPackage(getPackageName());
   	                finish();
                }
            })
            .setNegativeButton(R.string.popup_no, null)
            .show();
        }
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	isMove = false;
    }
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
		tvConnect.setText(R.string.ble_connect);
//        try {
//        	LocalBroadcastManager.getInstance(this).unregisterReceiver(UARTStatusChangeReceiver);
//        } catch (Exception ignore) {
//            Log.e(TAG, ignore.toString());
//        } 
//        unbindService(mServiceConnection);
//        mService.stopSelf();
//        mService= null;
    }
    @Override
    public void onDestroy() {
    	 super.onDestroy();
        Log.d(TAG, "onDestroy()");
        
        try {
        	LocalBroadcastManager.getInstance(this).unregisterReceiver(UARTStatusChangeReceiver);
        } catch (Exception ignore) {
            Log.e(TAG, ignore.toString());
        } 
        unbindService(mServiceConnection);
        mService.stopSelf();
        mService= null;
        unregisterReceiver(batteryReceiver);
        System.exit(0);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if(event.getAction()==KeyEvent.ACTION_DOWN){
    		switch (keyCode) {
			case KeyEvent.KEYCODE_HOME:
				finish();
				break;

			default:
				break;
			}
    	}
    	return super.onKeyDown(keyCode, event);
    }
    
    //����תΪԭ��
	private int dataParser(int x) 
	{
	    int y = x >> 8 ;
	    if(x>=0){
	    	return (((x^y)-y)|(y<<8));
	    }else{
	    	return Math.abs((((x^y)-y)|(y<<8)));
	    }
	} 
	
//	private int dataScale(int data) 
//	{
//		String str = String.valueOf(data);
//		int high = Integer.parseInt(str.substring(0, 1))+1;
//		switch (str.length()) {
//		case 1:
//		case 2:
//		case 3:
//		case 4:
//			data = (int)(240-(1500-data/(1000.0*high)*1500));
//			break;
//		case 5:
//			data = (int)(240-(1500-data/(10000.0*high)*1500));
//			break;
//		case 6:
//			data = (int) (3500-data/(100000.0*high)*3500);
//			if(data > 240){
//				String str6 = String.valueOf(data);
//				int high6 = Integer.parseInt(str6.substring(0, 1));
//				data = 240-(data-high6*100);
//			}
//			break;
//		case 7:
//			data = (int)(240-(1500-data/(1000000.0*high)*1500));
//			break;
//		case 8:
//			data = (int)(240-(1500-data/(10000000.0*high)*1500));
//			break;
//		default:
//			break;
//		}
//		return data;
//	}
	
//	private void BLEDataReceiveHandle(byte[] txValue) {
//		synchronized (this) {
////            	String text = new String(txValue, "UTF-8").trim();
////            	System.out.println("--------"+text);
////            	lst.add(Float.parseFloat(text.substring(1)));
//		 if(isShow){
//			 pd = new ProgressDialog(MainActivity.this);
//			 pd.setIcon(android.R.drawable.ic_dialog_email);
//			 pd.setMax(100);
//			 pd.setMessage("��ȡ�����У����Ե�......");
//			 pd.setCancelable(true);
//			 pd.show();
//			 isShow = false;
//		 }
//		 int data = 0;
//		 for (int i = 2; i < txValue.length-2; i++) {
//			if(i%3==2){
//				data = dataParser(txValue[i]);
//			}else{
//				data = (data<<8)+dataParser(txValue[i]);
//			}
//			if(i%3==1){
////        			String str = String.valueOf(data);
////        			data = Integer.parseInt(str.substring(str.length()-2));
////        			if(data>80){
////        				data = 120+(80-data)*6;
////        			}else{
////        				data = 120+(80-data)*6;
////        			}
////        			data = (int)(240-data/100.0*240);
////        			data = (int)(240-data/(16*1024*1024.0)*100);
////        			data = (int)(240-data/(250000.0)*180);
//				System.out.println("yjx&&"+data);
////        			data = dataScale(data);
////        			System.out.println("yjx**"+data);
//				lstData.add(data);
////        			if(count<360){
//					if(lstData.size()==360){
////        			   point[count++] = data;
////        				System.out.println("yjx**"+point[count-1]);
////        				if(count==360){
//						long sum = 0;
//						min = lstData.get(0); 
//						for (int j = 0; j < lstData.size(); j++) {
//							sum += lstData.get(j);
////        						sum += point[j];
//							min = Math.min(min, lstData.get(j));
//							max = Math.max(max, lstData.get(j));
//						}
//						avg = (int) (sum/360.0);
//						for (int j = 0; j < lstData.size(); j++) {
//		    				System.out.println("yjx--"+avg);
//		    				if(max-avg>avg-min){
//		    					data = (int)(240-180*1.0/(max-min)*(lstData.get(j)-min)-30);
//		    				}else if(max==min && max==avg){
//		    					data=180;
//		    				}else{
//		    					data = (int)(180*1.0/(max-min)*(lstData.get(j)-min)+60-30);
//		    				}
//		    				System.out.println("yjx$$"+data);
//		    				lst.add(data);
//						}
//						max = 0;
//						sum = 0;
//						new Thread(new Runnable() {
//							public void run() {
//								try {
//									Tools.getInstance().writer("/mnt/sdcard/data.txt", lstData);
//								} catch (IOException e) {
//									e.printStackTrace();
//								}
//							}
//						}).start();
//						lstData = new ArrayList<Integer>();
//						for (int j = 1; j < lst.size()-1; j++) {
//		    				if(lst.get(j)<lst.get(j-1) && lst.get(j)<lst.get(j+1)){
//		    					lstMax.add(j);
//		    				}
//						}
//						for (int j = 0; j < lstMax.size()-1; j++) {
//							sum += lstMax.get(j+1)-lstMax.get(j);
//						}
//						count = (int) (sum*1.0/(lstMax.size()-1));
//						t = sum*1.0/(lstMax.size()-1)/125.0;
//						bpm = (int) (60.0/t);
//						tvBPM.setText(bpm+"bpm");
//						lstMax = new ArrayList<Integer>();
////        					isAdd = true;
////        				}
//				}
//				if(isAdd){
//					System.out.println("yjx--"+avg);
////        				System.out.println("yjx--"+data*1.0/avg);
////        				data = (int)(240-data*1.0/avg*100);
//					data = (int)(180*1.0/(max-min)*(data-min));
////        				data = data-min;
//					System.out.println("yjx$$"+data);
//					lst.add(data);
////        				lstSubData.add(data);
////        				if (lstSubData.size() == 360){
////              				long sum = 0;
////            				for (int j = 0; j < lstSubData.size(); j++) {
////            					sum += lstSubData.get(j);
////							}
////            				int avg = (int) (sum/360.0);
////            				for (int j = 0; j < lstSubData.size(); j++) {
////            					int data2 = (int)(240-lstSubData.get(j)*1.0/avg*180);
////            					System.out.println("yjx****"+data);
////            					lst.add(data2);
////							}
////            				lstSubData = new ArrayList<Integer>();
////        				}
//				}
//				if (lst.size() == 360 && lst != null) {
//					lstBpm.add(lst);
////						Tools.getInstance().writer("/mnt/sdcard/data.txt", lst);
//					if(isFirst){
//						System.out.println("--------111111111111");
//						if(isLoop){
//							Tools.getInstance().drawWaveform(MainActivity.this, sfh, lstBpm, count);
//						}
//						pd.cancel();
//					}else{
//						if (lstBpm.size() == 2) {
//							System.out.println("--------111111111111");
//							if(isLoop){
//								Tools.getInstance().drawWaveform(MainActivity.this, sfh, lstBpm,count);
//							}
//							lstBpm.remove(0);
//						}
//					}
//					lst = new ArrayList<Integer>();
//				}
//			}
//		} 
//		}
//
////        	lst.add((Float) filter.trapper(1, Float.parseFloat(text), 250.0)[0]);
////        	System.out.println("----------"+(Float) filter.trapper(1, Float.parseFloat(text), 250.0)[0]);
////         	String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
////         	tvBPM.setText(text+"bpm");
////			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//"yyyy-MM-dd  HH:mm:ss"
////			String dateTime=format.format(new Date());
////          	HeartRate hr = new HeartRate(MainActivity.this, "������", dateTime, Integer.parseInt(text));
////          	boolean addResult = hr.add();
//// 			if(addResult){
//// 				Toast.makeText(MainActivity.this, "��ӳɹ���", Toast.LENGTH_SHORT).show();
//// 			}else{
//// 				Toast.makeText(MainActivity.this, "���ʧ�ܣ�", Toast.LENGTH_SHORT).show();
//// 			}
//	}
	
	private int frontNum = 0, num = 0;
	public static int txValueIndex = 0;
	private List<String> lstLoseData = new ArrayList<String>();
	//�������ݽ��մ���
	private void BLEDataReceiveHandle1(byte[] txValue) {
//		synchronized (this) {
//			String data17 = Integer.toHexString(dataParser(txValue[17])).toUpperCase();
//			Log.d("yjx", "----------"+data17);
			byte[] value = {txValue[0]};
			mService.writeRXCharacteristic(value);//˫����
			try {
				Tools.getInstance().writer(Environment.getExternalStorageDirectory() + "/data/"+dataDateTime+"package.txt", txValue);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
//			num = (dataParser(txValue[0])<< 8)+dataParser(txValue[1]);
//			Log.d("yjx", "��ǰ�յ����������ݰ��ţ� "+num);
//			lstLoseData.add("��ǰ�յ����������ݰ��ţ� "+num);
//			if(num - frontNum > 1 && frontNum > 0){
//				for (int i = frontNum+1; i < num; i++) {
//					Log.d("yjx", "�������������ݰ��ţ� "+i);
//					lstLoseData.add("�������������ݰ��ţ� "+i);
//					String message = Integer.toHexString(i).toUpperCase();
//					byte data0 = Byte.parseByte(message.substring(0, 2));
//					byte data1 = Byte.parseByte(message.substring(2));
//	            	byte[] value = {data0,data1};
//					try {
//					//send data to service
//	//				value = message.getBytes("UTF-8");
//					mService.writeRXCharacteristic(value);
//					return;
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//			frontNum = num;
//			if(!data17.equals("FA")){
////				return;
//			}
			int data = 0;
//			dataNum = txValue.length;
			for (int i = 2; i < txValue.length; i++) {
				//2���ֽڣ���20���ֽ�
				if(i % 2 == 0){
					data = dataParser(txValue[i]);
//					if(data==0)data=255;
				}else{
					data = (data << 8) + dataParser(txValue[i]);
////				}
					
//				if(txValueIndex % 2 == 0){
//					data = dataParser(txValue[i]);
//				}else{
//					data = (data << 8) + dataParser(txValue[i]);
//				}
//				txValueIndex++;
				
				//3���ֽ�,��17���ֽ�
//				if (i % 3 == 2) {
//					data = dataParser(txValue[i]);
//				} else {
//					data = (data << 8) + dataParser(txValue[i]);
//				}
//				if (i % 3 == 1) {
					
//					System.out.println("yjx&&" + data);
//					if(lstData.size() < dataNum){
//						if(data>frontMax+5000){
//							data = frontMin;
//						}
						lstData.add(data);
//					}
					
					if (lstData.size() == dataNum && lst.size() == 0) {
						long sum = 0;
						min = lstData.get(0);
						for (int j = 0; j < lstData.size(); j++) {
							sum += lstData.get(j);
							min = Math.min(min, lstData.get(j));
							max = Math.max(max, lstData.get(j));
							frontMax = max;
							frontMin = min;
						}
						avg = (int) (sum / (dataNum*1.0));
						sum = 0;
						System.out.println("yjx--" + avg);
						for (int j = 0; j < lstData.size(); j++) {
							if (max - avg > avg - min) {
								data = (int) (height - height * 0.75 / (max - min)*(lstData.get(j) - min) - height/8);
							} else if (max == min && max == avg) {
								data = 180;
							} else {
								data = (int) (height * 0.75 / (max - min)*(lstData.get(j) - min) + height/4 - height/8);
							}
//							System.out.println("yjx$$" + data);
							lst.add(data);
							sum += data;
							maxData = Math.max(maxData, data);
						}
						avg = (int) (sum / (dataNum*1.0));
						max = 0;
//						new Thread(new Runnable() {
//							public void run() {
								try {
//									Tools.getInstance().writer("/mnt/sdcard/data.txt", lstData);
									Tools.getInstance().writerInt(Environment.getExternalStorageDirectory() + "/data/"+dataDateTime+".dat", lstData,true);
									Tools.getInstance().writer16(Environment.getExternalStorageDirectory() + "/data/"+dataDateTime+"data16.txt", lstData,true);
//									Tools.getInstance().writer(Environment.getExternalStorageDirectory() + "/data/"+dataDateTime+"lst.txt", lst,true);
//									Tools.getInstance().writerLoseData(Environment.getExternalStorageDirectory() + "/data/"+dataDateTime+"losedata.txt", lstLoseData);
								} catch (IOException e) {
									e.printStackTrace();
								}
//							}
//						}).start();
								
						lstBpm.add(lst);
						System.out.println("lstBpm.size()==" + lstBpm.size());
						lstCalBpm = lst;
						lst = new ArrayList<Integer>();
						lstData.clear();
//						lstLoseData.clear();
//						lstData = new ArrayList<Integer>();

//						new Thread(new Runnable() {
//							public void run() {
								for (int j = 1; j < lstCalBpm.size() - 1; j++) {
									if (avg-lstCalBpm.get(j) > height*0.5 && lstCalBpm.get(j) < lstCalBpm.get(j-1)
											&& lstCalBpm.get(j) < lstCalBpm.get(j + 1)) {
										lstMax.add(j);//��Ӳ�����X������
									}
								}
								//����һ�����ε�һ����������һ�������X�����
								int sum1 = lstMax.get(lstMax.size()-1) - lstMax.get(0);
//								for (int j = 0; j < lstMax.size() - 1; j++) {
//									sum1 += lstMax.get(j + 1) - lstMax.get(j);
//								}
								//��һ����������һ�������������ݵ�����������εĸ�����2������֮�����һ���������Σ����Լ�1��,����һ�����������ƽ�����ݵ�      
								count = (int) (sum1 * 1.0 / (lstMax.size() - 1));
								//һ��������������ݸ������ڲ���Ƶ�ʵ���һ�����β��������ʱ������
								t = count * 1.0/125;//125����Ƶ�ʣ�ÿ��ɼ������ݸ�����
								//���ʵ���1min�������Ĵ�����Ҳ�����������εĸ���
								bpm = (int) (60.0 / t);
								lstMax = new ArrayList<Integer>();
								handler.sendEmptyMessage(Constance.MESSAGE_CALCULATE_BPM);
//							}
//						}).start();
						
					}
				}
			}
//		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_settings:
			break;
		case R.id.action_file_manager:
//			fileManager();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void getUUID(){
		try {
//			Method getUuidsMethod = BluetoothAdapter.class.getDeclaredMethod("getUuids", null);
//			ParcelUuid[] uuids = (ParcelUuid[]) getUuidsMethod.invoke(mBtAdapter, null);
			ParcelUuid[] uuids = mDevice.getUuids();
			for (ParcelUuid uuid: uuids) {
			    Log.d(TAG, "UUID: " + uuid.getUuid().toString());
			    Toast.makeText(this, "UUID: " + uuid.getUuid().toString(), Toast.LENGTH_LONG).show();
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
