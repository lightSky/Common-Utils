package com.it.xushuai.baseapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.it.xushuai.baseproject.R;


public class LettersIndexSelectionBar extends View implements View.OnTouchListener {

	private int viewWith;
	private int viewHeight;
	private char ch = '\000';
	private int scale = 0;
	private boolean searchFlag = true;
	private LettersIndexSelectionBar.OnLetterSelectedListener onLetterSelectedListener;

	public LettersIndexSelectionBar(Context context) {
		super(context);
	}

	public LettersIndexSelectionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setOnTouchListener(this);
		setBackground();
		viewWith = getWidth();
		viewHeight = getHeight();
	}

	public LettersIndexSelectionBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private void setBackground() {
		if (!this.searchFlag)
			setBackgroundResource(R.drawable.letters_selection_bar_with_search_icon_default);
		else
			setBackgroundResource(R.drawable.letters_selection_bar_default);
	}

	private void setPressedBackground() {
		if (!this.searchFlag)
			setBackgroundResource(R.drawable.letters_selection_bar_with_search_icon_pressed);
		else
			setBackgroundResource(R.drawable.letters_selection_bar_pressed);
	}

	protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
		viewWith = paramInt1;
		viewHeight = paramInt2;
		scale = (this.viewHeight / 27);
	}

	public interface OnLetterSelectedListener {
		public abstract void onLetterSelected(char paramChar);
	}

	@Override
	public boolean onTouch(View view, MotionEvent paramMotionEvent) {
		int action;
		if (scale != 0) {
			int eventX = (int) paramMotionEvent.getX();
			int eventY = (int) paramMotionEvent.getY();
			action = paramMotionEvent.getAction();
			if ((eventX >= 0) && (eventX <= viewWith) && (eventY >= 0) && (eventY <= viewHeight)) {
				eventX = eventY / scale;
				if ((eventX >= 0) && (eventX <= 26)) {
					if (eventX != 0) {
						eventX = (char)(eventX + 65 - 1);
					} else {
						eventX = 35;
					}
					if ((ch != eventX) || (action == 0)) {
						ch = (char) eventX;
						setPressedBackground();
						if (onLetterSelectedListener != null)
							onLetterSelectedListener.onLetterSelected(ch);
					}
				}
			}
			if ((action == 1) || (action == 3)) {
				ch = '\000';
				setBackground();
			}
			action = 1;
		} else {
			action = 1;
		}
		return true;
	}

	public void setHasSearchIcon(boolean paramBoolean) {
		this.searchFlag = paramBoolean;
		setBackground();
	}

	public void setOnLetterSelectedListener(LettersIndexSelectionBar.OnLetterSelectedListener paramOnLetterSelectedListener) {
		this.onLetterSelectedListener = paramOnLetterSelectedListener;
	}

}
