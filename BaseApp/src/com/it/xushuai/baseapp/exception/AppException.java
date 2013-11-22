package com.it.xushuai.baseapp.exception;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.http.HttpException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import com.it.xushuai.baseapp.AppContext;
import com.it.xushuai.baseapp.AppManager;
import com.it.xushuai.baseproject.R;

public class AppException implements UncaughtExceptionHandler{
	/**whether save log */
	private final static boolean Debug = false;
	
	/**Custom Exception Type*/
	public static final byte TYPE_NETWORK 	= 0x01;
	public static final byte TYPE_SOCKET	= 0x02;
	public static final byte TYPE_HTTP_CODE = 0x03;
	public static final byte TYPE_HTTP_ERROR= 0x04;
	public static final byte TYPE_XML 		= 0x05;
	public static final byte TYPE_IO 		= 0x06;
	public static final byte TYPE_RUN 		= 0x07;
	
	/**Exception Type*/
	private byte type;
	/**Exception Code*/
	private int code;
	
	/**系统默认的unCaughtException 处理类*/
	private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
	
	private AppException(){
		uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	private AppException(byte type,int code, Exception excp){
		this.type = type;
		this.code = code; 
		if(Debug){
			this.saveErrorLog(excp);
		}
	}
	
	public int getCode() {
		return this.code;
	}
	public int getType() {
		return this.type;
	}
	
	/**
	 * Http response code
	 * @param code
	 * @return
	 */
	public static AppException http(int code) {
		return new AppException(TYPE_HTTP_CODE,code,null);
	}
	
	/**
	 * Http Exception
	 * @param e
	 * @return
	 */
	public static AppException http(Exception e){
		return new AppException(TYPE_HTTP_ERROR,0,e);
	}

	public static AppException socket(Exception e){
		return new AppException(TYPE_SOCKET,0,e);
	}

	public static AppException xml(Exception e){
		return new AppException(TYPE_XML,0,e);
	}
	
	/**
	 * IO Exception ： includes Http、IO、Runtime Exception
	 * @param e
	 * @return
	 */
	public static AppException io(Exception e){
		if(e instanceof UnknownHostException || e instanceof ConnectException){
			return new AppException(TYPE_NETWORK,0,e);
		}
		else if(e instanceof IOException){
			return new AppException(TYPE_IO,0,e);
		}
		return run(e);
	}
	
	/**
	 * newwork Exception: includes Http、IO、Socket Exception
	 * @param e
	 * @return
	 */
	public static AppException network(Exception e) {
		if(e instanceof HttpException){
			return new AppException(TYPE_NETWORK,0,e);
		}
		else if(e instanceof IOException){
			return io(e);
		}
		else if(e instanceof SocketException){
			return socket(e);
		}
		return http(e);
	}
	
	public static AppException run(Exception e) {
		return new AppException(TYPE_RUN, 0, e);
	}
	
	/**
	 * 提示友好的错误信息
	 * @param ctx
	 */
	public void makeToast(Context ctx){
		switch(this.getType()){
		case TYPE_HTTP_CODE:
			String err = ctx.getString(R.string.http_status_code_error, this.getCode());
			Toast.makeText(ctx, err, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_HTTP_ERROR:
			Toast.makeText(ctx, R.string.http_exception_error, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_SOCKET:
			Toast.makeText(ctx, R.string.socket_exception_error, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_NETWORK:
			Toast.makeText(ctx, R.string.network_not_connected, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_XML:
			Toast.makeText(ctx, R.string.xml_parser_failed, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_IO:
			Toast.makeText(ctx, R.string.io_exception_error, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_RUN:
			Toast.makeText(ctx, R.string.app_run_code_error, Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	/**
	 * 获取APP异常崩溃处理对象
	 * @param context
	 * @return
	 */
	public static AppException getAppExceptionHandler(){
		return new AppException();
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		uncaughtExceptionHandler.uncaughtException(thread, ex);
	}
	
	private boolean handleException(Throwable ex){
		if(ex == null){
			return false;
		}
		final Context context = AppManager.getAppManager().getCurrentActivity();
		if(context == null)
			return false;
		String crashReport = getCrashReport(context,ex);
		//发送错误报告到服务器
		new Thread(){
			public void run() {
				Looper.prepare();
				//UIHelper.sendAppCrashReport(context, crashReport); //异步处理
				Looper.loop();
			};
		}.start();
		return true;
	}
	
	/**
	 * 获取APP崩溃异常报告
	 * @param ex
	 * @return
	 */
	private String getCrashReport(Context context,Throwable ex) {
		PackageInfo packageInfo = ((AppContext)context.getApplicationContext()).getPackageInfo();
		StringBuffer exceptionStr = new StringBuffer();
		exceptionStr.append("Version: "+packageInfo.versionName+"("+packageInfo.versionCode+")\n");
		exceptionStr.append("Android: "+android.os.Build.VERSION.RELEASE+"("+android.os.Build.MODEL+")\n");
		exceptionStr.append("Exception: "+ex.getMessage()+"\n");
		StackTraceElement[] elements = ex.getStackTrace();
		for (int i = 0; i < elements.length; i++) {
			exceptionStr.append(elements[i].toString()+"\n");
		}
		return exceptionStr.toString();
	}
	
	/**
	 * 保存错误日志
	 * @param excp
	 */
	private void saveErrorLog(Exception excp) {
		String errorLog = "errorlog.txt";
		String savePath = "";
		String logFilePath = "";
		FileWriter fw = null;
		PrintWriter pw = null;
		
		try {
				String externalStorageState = Environment.getExternalStorageState();
				if(externalStorageState.equals(Environment.MEDIA_MOUNTED)){
					savePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MyApp/Log/";
					File dir = new File(savePath);
					if(!dir.exists()){
						dir.mkdirs();
						logFilePath = savePath + errorLog;
					}
					if(logFilePath == ""){
						return;
					}
					File logFile = new File(logFilePath);
					if (!logFile.exists()) {
						logFile.createNewFile();
					}
					fw = new FileWriter(logFile,true);
					pw = new PrintWriter(fw);
					pw.println("--------------------"+(new Date().toLocaleString())+"---------------------");	
					excp.printStackTrace(pw);
					fw.close();
					pw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();	
			}finally{
				if(pw != null){ pw.close(); } 
				if(fw != null){ try { fw.close(); } catch (IOException e) { }}
			}
	}
}
