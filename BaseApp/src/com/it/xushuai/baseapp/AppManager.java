package com.it.xushuai.baseapp;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * 应用程序管理类，处理所有的Activity、应用程序的退出
 *
 */
public class AppManager {
	
	private Stack<Activity> activityStack;
	
	/**
	 * 单例模式
	 * @return
	 */
	private static AppManager appManagerInstance;
	private AppManager(){}
	public static AppManager getAppManager(){
		if(appManagerInstance == null){
			return  new AppManager();
		}
		return appManagerInstance;
	}
	
	/**
	 * 添加当前Activity 到堆栈
	 * @param activity
	 */
	public void addActivity(Activity activity){
		if(activityStack == null){
				activityStack = new Stack<Activity>();
		}
		activityStack.push(activity);
	}

	/**
	 * 获取当前的Activity （堆栈中的最后一个压入的）
	 * @return
	 */
	public Activity getCurrentActivity(){
		return activityStack.lastElement();
	}
	
	/**
	 * 销毁当前的Activity
	 * @param activity
	 */
	public void finishActivity(){
		finishActivity(activityStack.lastElement());
	}
	
	/**
	 * 销毁指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if(activity !=null ){
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}
	
	/**
	 * 销毁指定的Activity
	 * @param clazz
	 */
	public void finishActivity(Class<?> clazz){
		for (Activity activity : activityStack){
			if(activity.getClass().equals(clazz)){
				finishActivity(activity);
			}
		}
	}
	
	public void finishAllActivity(){
		for(Activity activity : activityStack){
			finishActivity(activity);
		}
		activityStack.clear();
	}

	/**
	 * 退出应用程序
	 */
	@SuppressWarnings("deprecation")
	public void ExitApp(Context context){
		finishAllActivity();
		ActivityManager activityMgr = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		activityMgr.restartPackage(context.getPackageName());
		System.exit(0);
	}
}
