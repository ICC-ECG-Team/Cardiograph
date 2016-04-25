package com.cardiograph.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.cardiograph.constance.Parameters;

/**
 * @author ��ͬ��
 * 
 *         <pre>
 * REST WebService ���ʽӿ� 
 * Ӧ�����������˵Ľ�����Ҫͨ���˽ӿ�
 * </pre>
 */
public class RestWebService {

	public RestWebService() {
	}

	/**
	 * Get ��ʽ�ύ,����ͳ��
	 * 
	 * @param URL
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 */
	public static String getRequestWithStatistic(String URL)
			throws IOException, BaseException {
		String ret = null;
		HttpGet request = new HttpGet(URL);
		ret = ExecuteRequestForString(request);
		return ret;
	}

	/**
	 * Get ��ʽ�ύ
	 * 
	 * @param URL
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public static JSONObject getRequest(String URL) throws ParseException,
			IOException, BaseException {
		JSONObject ret = null;
		HttpGet request = new HttpGet(URL);
		ret = ExecuteRequest(request);
		return ret;
	}

	/**
	 * Get ��ʽ�ύ
	 * 
	 * @param URL
	 *            Ҫ����� URL
	 * @param nameValuePairs
	 *            �����б�
	 * @param charsetName
	 *            url �����ַ������ƣ��� "UTF-8"
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public static JSONObject getRequest(String URL,
			List<NameValuePair> nameValuePairs, String charsetName)
			throws ParseException, IOException, BaseException {
		JSONObject ret = null;
		String parameter = toUrlParameter(nameValuePairs, charsetName);
		HttpGet request = new HttpGet(URL.concat(parameter));
		ret = ExecuteRequest(request);

		return ret;
	}

	/**
	 * Get ��ʽ�ύ
	 * 
	 * @param URL
	 *            Ҫ����� URL
	 * @param nameValuePairs
	 *            �����б�
	 * @param charsetName
	 *            url �����ַ������ƣ��� "UTF-8"
	 * @return ����������
	 * @throws BaseException
	 * @throws org.apache.http.client.ClientProtocolException
	 * @throws java.io.IOException
	 */
	public static InputStream getRequestForStream(String URL,
			List<NameValuePair> nameValuePairs, String charsetName)
			throws BaseException, ClientProtocolException, IOException {
		InputStream ret = null;
		String parameter = toUrlParameter(nameValuePairs, charsetName);
		HttpGet request = new HttpGet(URL.concat(parameter));

		ret = ExecuteRequestForStream(request);

		return ret;
	}

	/**
	 * post��ʽ�ύ,����json ��ʽ�ַ���
	 * 
	 * @param URL
	 * @param entity
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public static JSONObject postRequest(String URL, HttpEntity entity)
			throws ParseException, IOException, BaseException {
		JSONObject ret = null;
		HttpPost request = new HttpPost(URL);
		if (entity != null) {
			request.setEntity(entity);
		}

		ret = ExecuteRequest(request);
		return ret;
	}

	/**
	 * Post ��ʽ�ύ
	 * 
	 * @param URL
	 *            Ҫ����� URL
	 * @param nameValuePairs
	 *            �����б�
	 * @param charsetName
	 *            url �����ַ������ƣ��� "UTF-8"
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public static JSONObject postRequest(String URL,
			List<NameValuePair> nameValuePairs, String charsetName)
			throws ParseException, IOException, BaseException {
		JSONObject ret = null;
		HttpPost request = new HttpPost(URL);
		HttpEntity entity;
		entity = new UrlEncodedFormEntity(nameValuePairs, charsetName);
		request.setEntity(entity);

		// ��������
		ret = ExecuteRequest(request);
		return ret;
	}

	/**
	 * put��ʽ�ύ
	 * 
	 * @param URL
	 * @param entity
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public static JSONObject putRequest(String URL, HttpEntity entity)
			throws ParseException, IOException, BaseException {
		JSONObject ret = null;
		HttpPut request = new HttpPut(URL);
		if (entity != null) {
			request.setEntity(entity);
		}
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		// ��������
		ret = ExecuteRequest(request);
		return ret;
	}

	/**
	 * Put ��ʽ�ύ
	 * 
	 * @param URL
	 *            Ҫ����� URL
	 * @param nameValuePairs
	 *            �����б�
	 * @param charsetName
	 *            url �����ַ������ƣ��� "UTF-8"
	 * @return
	 * @throws BaseException
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 */
	public static JSONObject putRequest(String URL,
			List<NameValuePair> nameValuePairs, String charsetName)
			throws ParseException, IOException, BaseException {
		JSONObject ret = null;
		HttpPut request = new HttpPut(URL);
		HttpEntity entity;

		entity = new UrlEncodedFormEntity(nameValuePairs, charsetName);
		request.setEntity(entity);

		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		// ��������
		ret = ExecuteRequest(request);
		return ret;
	}

	/**
	 * deleteRequest ��ʽ�ύ
	 * 
	 * @param URL
	 *            Ҫ����� URL
	 * @param nameValuePairs
	 *            �����б�
	 * @param charsetName
	 *            url �����ַ������ƣ��� "UTF-8"
	 * @return
	 * @throws BaseException
	 * @throws org.apache.http.client.ClientProtocolException
	 * @throws java.io.IOException
	 */
	public static JSONObject deleteRequest(String URL,
			List<NameValuePair> nameValuePairs, String charsetName)
			throws ParseException, IOException, BaseException {
		JSONObject ret = null;

		String parameter = toUrlParameter(nameValuePairs, charsetName);
		HttpDelete request = new HttpDelete(URL.concat(parameter));

		// ��������
		ret = ExecuteRequest(request);
		return ret;
	}

	/**
	 * ����ֵ��ת���� Url �����ַ���
	 * 
	 * @param parameters
	 *            �����б�
	 * @param charsetName
	 * @return ����������ʽ���ַ��� :?var1=1&var2=2
	 */
	public static String toUrlParameter(List<NameValuePair> parameters,
			String charsetName) {
		if (parameters == null)
			return null;

		StringBuffer urlEncode = new StringBuffer();
		int size = parameters.size();

		for (int index = 0; index < size; index++) {
			NameValuePair vp = parameters.get(index);

			if (index == 0)
				urlEncode.append('?');
			else
				urlEncode.append('&');

			urlEncode.append(vp.getName());
			urlEncode.append('=');

			try {
				urlEncode.append(URLEncoder.encode(vp.getValue(), charsetName));
			} catch (UnsupportedEncodingException e) {
				urlEncode.append(vp.getValue());
			}
		}

		return urlEncode.toString();
	}

	/**
	 * ִ�� Http ����
	 * 
	 * @param request
	 * @return ���� json ����
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 * @throws BaseException
	 */
	private static JSONObject ExecuteRequest(HttpUriRequest request)
			throws ParseException, IOException, BaseException {
		JSONObject ret = null;

		// ǿ���� http ͷ��ָ��charset ���������������롣
		AddCharsetToHeader(request);

		// ִ��http���󣬷���Ӧ�����ݡ�
		HttpResponse response = HttpUtil.getHttpClient().execute(request);

		// �жϷ�����Ӧ��״̬�����״̬������ʼ�������ص�json ��
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

			String retString = EntityUtils.toString(response.getEntity());

			// �������ص�����
			try {
				ret = new JSONObject(retString);
				if (Parameters.debug) {
					System.out.println("����200��json:" + ret);
				}
			} catch (JSONException e) {
				// json �����쳣���񷵻��˴�������ݣ����ⷵ���Զ�����쳣�ࡣ
				throw new BaseException("",
						ExceptionCode.ServerResultDataError, 0);
			}
		} else {
			// ��200״̬ʱ���ر�����
			try {
				request.abort();
			} catch (UnsupportedOperationException e) {
			}

			// �����������˴����׳��쳣
			throw new BaseException(response.getStatusLine().getReasonPhrase(),
					ExceptionCode.ServerHttpError, response.getStatusLine()
							.getStatusCode());
		}

		return ret;
	}

	/**
	 * ִ�� Http ����,�����ַ�����
	 * 
	 * @param request
	 * @return �����ַ���
	 * @throws java.io.IOException
	 * @throws org.apache.http.ParseException
	 * @throws BaseException
	 */
	private static String ExecuteRequestForString(HttpUriRequest request)
			throws IOException, BaseException {
		String ret = null;

		// ǿ���� http ͷ��ָ��charset ���������������롣
		AddCharsetToHeader(request);

		// ִ��http���󣬷���Ӧ�����ݡ�
		HttpResponse response = HttpUtil.getHttpClient().execute(request);

		// �жϷ�����Ӧ��״̬�����״̬������ʼ�������ص�json ��
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			ret = EntityUtils.toString(response.getEntity());
		} else {
			// ��200״̬ʱ���ر�����
			try {
				request.abort();
			} catch (UnsupportedOperationException e) {
			}

			// �����������˴����׳��쳣
			throw new BaseException(response.getStatusLine().getReasonPhrase(),
					ExceptionCode.ServerHttpError, response.getStatusLine()
							.getStatusCode());
		}

		return ret;
	}

	/**
	 * ִ�� Http ����
	 * 
	 * @param request
	 * @return ���� json ������
	 * @throws java.io.IOException
	 * @throws BaseException
	 * @throws org.apache.http.client.ClientProtocolException
	 */
	private static InputStream ExecuteRequestForStream(HttpUriRequest request)
			throws BaseException, ClientProtocolException, IOException {
		InputStream ret = null;

		// ǿ���� http ͷ��ָ��charset ���������������롣
		AddCharsetToHeader(request);

		HttpClient httpclient = HttpUtil.getHttpClient();
		
		// ����ʱ
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		// ��ȡ��ʱ
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				10000);

		// ִ��http���󣬷���Ӧ�����ݡ�
		HttpResponse response = httpclient.execute(request);
		
		int statusCode = response.getStatusLine().getStatusCode();

		// �жϷ�����Ӧ��״̬�����״̬�����򷵻�������;
		if (statusCode == HttpStatus.SC_OK) {

			if (response.getEntity().isStreaming()) {
				return response.getEntity().getContent();
			} else {
				return ret;
			}
		} else {
			// ��200״̬ʱ���ر�����
			try {
				request.abort();
			} catch (UnsupportedOperationException e) {
			}

			// �����������˴����׳��쳣
			throw new BaseException(response.getStatusLine().getReasonPhrase(),
					ExceptionCode.ServerHttpError, response.getStatusLine()
							.getStatusCode());
		}
	}

	/**
	 * ǿ���� http ͷ��ָ��charset ���������������롣
	 * 
	 * @param request
	 */
	private static void AddCharsetToHeader(HttpUriRequest request) {
		Header ht = null;
		if (request instanceof HttpPost || request instanceof HttpPut) {
			HttpEntityEnclosingRequest hp = (HttpEntityEnclosingRequest) request;
			ht = hp.getEntity().getContentType();
		}

		String contentType = "";
		if (ht != null) {
			contentType = ht.getValue();
		}
		contentType += ";charset=" + HttpUtil.EncodingCharset;
		request.setHeader("Content-Type", contentType);
	}

}
