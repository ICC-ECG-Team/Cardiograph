package com.cardiograph.thread;

import android.os.AsyncTask;

/**
 * AsyncTask�ļ򵥳����࣬�����ڼ򵥵��첽����
 * 
 * <p>�����Ҫ���ý��ȣ�����<code>doInBackground()</code>�е���<code>publishProgress(Progress... values)</code>������
 * Ȼ����<code>progressUpdate(int progress)</code>�и���UI</p>
 * 
 * <p>������ɺ��������������<code>.execute()</code>����ִ���첽����</p>
 * @ClassName AsyncTaskDoing
 * @author ZhengWx
 * @date 2014��8��24�� ����8:52:03
 */
public abstract class AsyncTaskDoing extends AsyncTask<Void, Integer, Void>{
	
	/**
	 * ��̨����
	 * @author ZhengWx
	 * @date 2014��8��24�� ����8:47:31
	 * @since 1.0
	 */
	public abstract void doInBackground();
	/**
	 * ׼������
	 * @author ZhengWx
	 * @date 2014��8��24�� ����8:47:26
	 * @since 1.0
	 */
	public abstract void preExecute();
	/**
	 * ���½���
	 * @author ZhengWx
	 * @date 2014��8��24�� ����8:46:27
	 * @param progress ��ǰ����ֵ
	 * @since 1.0
	 */
	public abstract void progressUpdate(int progress);
	/**
	 * ���н���
	 * @author ZhengWx
	 * @date 2014��8��24�� ����8:47:37
	 * @since 1.0
	 */
	public abstract void postExecute();
	
	@Override
	protected void onPreExecute() {
		preExecute();
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress) {
		progressUpdate(progress[0]);
	}
	
	@Override
	protected void onPostExecute(Void result) {
		postExecute();
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		doInBackground();
		return null;
	}
}
