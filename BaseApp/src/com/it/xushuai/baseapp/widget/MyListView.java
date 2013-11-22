package com.it.xushuai.baseapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 可完全显示的ListView（当与ScrollView 冲突的时候，数据显示不全�?
 * @author Administrator
 *
 */
public class MyListView extends ListView{

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		//根据模式计算每个child的高度和宽度
		super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST));
	}

}
