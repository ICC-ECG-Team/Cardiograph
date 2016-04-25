package com.cardiograph.commom;

import java.util.LinkedList;
import java.util.List;

import com.cardiograph.constance.Parameters;
import com.cardiograph.log.Debugger;
import com.cardiograph.model.UserInfo;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * �Զ���ȫ�ֳ���
 * @author bob
 * 
 *
 */
public class ApplicationExtension extends Application  {
	
	private static ApplicationExtension instance;

	private List<Activity> activities = new LinkedList<Activity>();
	public UserInfo user = null;

	public static ApplicationExtension getInstance() {
        return instance;
    }
    
    /**
     * ���activity
     * @param activity
     */
    public void addActivity(Activity activity) {
		activities.add(activity);
	}
    
    /**
     * �Ƴ�activity
     * @param activity
     */
    public void removeActivity(Activity activity) {
    	if (activities.contains(activity)) {
    		activities.remove(activity);
		}
	}

    /**
     * �˳�Ӧ�ã��رն�ջ��Activity
     */
    public void exit() {
		user.destory();
    	
		for (Activity activity : activities) {
			activity.finish();
		}
		
//		//������ڵĻ�������
//		FileCache fileCache = new FileCache(getApplicationContext());
//		fileCache.removeExpire();
//		DatabaseCache dbCache = new DatabaseCache(getApplicationContext());
//		dbCache.removeExpire();
		
		System.exit(0);  
	}
    	
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        user = UserInfo.getInstance();
        Parameters.initServerAddress();
//        //��ʼ��crash������
//        CrashHandler.init();   
//        // This configuration tuning is custom. You can tune every option, you may tune some of them, 
//     		// or you can create default configuration by
//     		//  ImageLoaderConfiguration.createDefault(this);
//     		// method.
// 		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
// 			.threadPriority(Thread.NORM_PRIORITY - 2)
// 			.memoryCacheSize(2 * 1024 * 1024) // 2 Mb
// 			.denyCacheImageMultipleSizesInMemory()
// 			.discCacheFileNameGenerator(new Md5FileNameGenerator())
// 			.imageDownloader(new ExtendedImageDownloader(getApplicationContext()))
// 			.tasksProcessingOrder(QueueProcessingType.LIFO)
////     			.enableLogging() // Not necessary in common
// 			.build();
// 		// Initialize ImageLoader with configuration.
// 		ImageLoader.getInstance().init(config);
    }
    
    /**
     * ��ϵͳ�ڴ����ʱ���¼�
     */
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	/**
	 * �����˳�ʱ���¼�
	 */
	@Override
	public void onTerminate() {
		super.onTerminate();
		user.destory();
	}
	
	/**
	 * �Ƿ��ǵ�һ�����е�ǰ�汾Ӧ��
	 * @return
	 */
	public boolean isFirstRun(){
		PackageManager packageManager = getPackageManager();
		boolean firstRun = false;
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			
//			firstRun = LklSharedPreferences.getInstance().getBoolean(
//					String.format("%s%04d",UniqueKey.firstRun,packageInfo.versionCode),
//					true);

		} catch (Exception e) {
			new Debugger().log(e);
		}
		
		return firstRun;
	}

    /**
     * �Ƿ��ǵ�һ���ڵ�ǰ�ֻ���¼
     */
	public boolean isFirstLogin(String loginName){
        PackageManager packageManager = getPackageManager();
        boolean firstRun = false;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

//            firstRun = LklSharedPreferences.getInstance().getBoolean(
//                    String.format("%s%04d",UniqueKey.firstRun,packageInfo.versionCode) + loginName,
//                    true);

        } catch (Exception e) {
            new Debugger().log(e);
        }

        return firstRun;
    }

    public void setFirtLogin(String loginName){

        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

//            LklSharedPreferences.getInstance().putBoolean(
//                    String.format("%s%04d",UniqueKey.firstRun,packageInfo.versionCode) + loginName,
//                    false);

        } catch (Exception e) {
            new Debugger().log(e);
        }
    }


	/**
	 * ����firstRun��־����ʾ�Ѿ����й���ǰ�汾Ӧ��
	 */
	public void setFirstRun(){
		PackageManager packageManager = getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			
//			LklSharedPreferences.getInstance().putBoolean(
//					String.format("%s%04d", UniqueKey.firstRun,packageInfo.versionCode),
//					false);

		} catch (Exception e) {
			new Debugger().log(e);
		}
	}

	public Activity getCurActivity(){
		return activities.get(activities.size()-1);
	}
}
