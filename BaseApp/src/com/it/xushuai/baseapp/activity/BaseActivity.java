package com.it.xushuai.baseapp.activity;

import com.it.xushuai.baseapp.AppManager;
import com.mobclick.android.MobclickAgent;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {
	private AppManager appManager = AppManager.getAppManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appManager.addActivity(this);
		MobclickAgent.onError(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		appManager.finishActivity(this);
	}
	
	protected abstract void initViews();
	protected abstract void initEvents();
		
}
