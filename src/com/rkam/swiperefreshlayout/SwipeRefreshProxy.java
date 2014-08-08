package com.rkam.swiperefreshlayout;

import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;

public class SwipeRefreshProxy extends TiViewProxy {

	private SwipeRefresh swipeRefresh;
	
	public SwipeRefreshProxy() {
		super();
	}

	@Override
	public TiUIView createView(Activity activity) {
		swipeRefresh = new SwipeRefresh(this);
		return swipeRefresh;
	}
	
	@Kroll.method
	public void setRefreshing(boolean refreshing) {
		if (TiApplication.isUIThread()) {
			swipeRefresh.setRefreshing(refreshing);
			return;
		}
	}
	
	@Kroll.method @Kroll.getProperty
	public boolean isRefreshing() {
		return swipeRefresh.isRefreshing();
	}
	
	@Kroll.method @Kroll.setProperty
	public void setColorScheme(Object arg) {
		setPropertyAndFire(SwipeRefresh.PROPERTY_COLOR_SCHEME, arg);
	}
}
