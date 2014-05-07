
package com.it.xushuai.baseapp.net;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.it.xushuai.baseapp.exception.AppException;

public class DownLoadPic {
	private static HashMap<String, SoftReference<Bitmap>> cache;  
	
	public static final String UTF_8 = "UTF-8";
	public static final String DESC = "descend";
	public static final String ASC = "ascend";
	
	private final static int TIMEOUT_CONNECTION = 20000;
	private final static int TIMEOUT_SOCKET = 20000;
	private final static int RETRY_TIME = 3;

	
	private static GetMethod getHttpGet(String url, String cookie, String userAgent) {
		GetMethod httpGet = new GetMethod(url);
		httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
//		httpGet.setRequestHeader("Host", URLs.HOST);
		httpGet.setRequestHeader("Connection","Keep-Alive");
		httpGet.setRequestHeader("Cookie", cookie);
		httpGet.setRequestHeader("User-Agent", userAgent);
		return httpGet;
	}
	
	private static HttpClient getHttpClient() {        
        HttpClient httpClient = new HttpClient();
		// ���� HttpClient ���� Cookie,���������һ��Ĳ���
		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        // ���� Ĭ�ϵĳ�ʱ���Դ������
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		// ���� l�ӳ�ʱʱ��
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT_CONNECTION);
		// ���� ����ݳ�ʱʱ�� 
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT_SOCKET);
		// ���� �ַ�
		httpClient.getParams().setContentCharset(UTF_8);
		return httpClient;
	}	
	
	/**
	 * @param url
	 * @return
	 */
	public static Bitmap getNetBitmap(String url) throws AppException {
		//System.out.println("image_url==> "+url);
		HttpClient httpClient = null;
		GetMethod httpGet = null;
		Bitmap bitmap = null;
		int time = 0;
		do{
			try 
			{
				httpClient = getHttpClient();
				httpGet = getHttpGet(url, null, null);
				int statusCode = httpClient.executeMethod(httpGet);
				if (statusCode != HttpStatus.SC_OK) {
					throw AppException.http(statusCode);
				}
		        InputStream inStream = httpGet.getResponseBodyAsStream();
		        bitmap = BitmapFactory.decodeStream(inStream);
		        inStream.close();
		        break;
			} catch (HttpException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				e.printStackTrace();
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				e.printStackTrace();
				throw AppException.network(e);
			} finally {
				httpGet.releaseConnection();
				httpClient = null;
			}
		}while(time < RETRY_TIME);
		return bitmap;
	}
	
}
