/*
 * Copyright 2012 Kevin Sawicki <kevinsawicki@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.it.xushuai.baseapp.common;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

/**
 * Helpers for gestures
 */
public class Gestures {

	private Activity context;

	/**
	 * Register listener on double-tap gesture for view
	 * 
	 * @param view
	 * @param listener
	 * @return view
	 */
	public static View onDoubleTap(final View view,
			final OnDoubleTapListener listener) {
		final GestureDetector detector = new GestureDetector(view.getContext(),
				new SimpleOnGestureListener());
		detector.setOnDoubleTapListener(listener);
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return detector.onTouchEvent(event);
			}
		});

		return view;
	}

	private GestureDetector gd;

	/**
	 * ×¢²áË«»÷È«ÆÁÊÂ¼þ
	 */
	private boolean isFullScreen;

	private void regOnDoubleEvent() {
		gd = new GestureDetector(context,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onDoubleTap(MotionEvent e) {
						isFullScreen = !isFullScreen;
						if (!isFullScreen) {
							WindowManager.LayoutParams params = context
									.getWindow().getAttributes();
							params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
							context.getWindow().setAttributes(params);
							context.getWindow()
									.clearFlags(
											WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
							// mHeader.setVisibility(View.VISIBLE);
							// mFooter.setVisibility(View.VISIBLE);
						} else {
							WindowManager.LayoutParams params = context
									.getWindow().getAttributes();
							params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
							context.getWindow().setAttributes(params);
							context.getWindow()
									.addFlags(
											WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
							// mHeader.setVisibility(View.GONE);
							// mFooter.setVisibility(View.GONE);
						}
						return true;
					}
				});
	}
}
