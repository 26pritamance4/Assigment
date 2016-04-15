package com.encriptionapp.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class GetDeviceResolution {

	public int height, width;
	public double screenInches;
	private Activity act;

	public GetDeviceResolution(Activity act) {

		this.act = act;
		getDeviceResolution();

	}

	private void getDeviceResolution() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		width = displaymetrics.widthPixels;

		DisplayMetrics dm = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(dm);
		double x = Math.pow(width / dm.xdpi, 2);
		double y = Math.pow(height / dm.ydpi, 2);

		screenInches = Math.sqrt(x + y);

		if (width <= 480 && screenInches > 5.0) {
			screenInches = 4.5;
		}
	}

}
