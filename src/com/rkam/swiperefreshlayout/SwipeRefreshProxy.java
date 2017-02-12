package com.rkam.swiperefreshlayout;

import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;
import org.appcelerator.titanium.TiApplication;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

@Kroll.proxy(creatableInModule=SwiperefreshlayoutModule.class)
public class SwipeRefreshProxy extends TiViewProxy implements Handler.Callback {

	private SwipeRefresh swipeRefresh;

	protected static final int MSG_SET_REFRESHING = KrollProxy.MSG_LAST_ID + 101;

	public SwipeRefreshProxy() {
		super();
	}

	@Override
	public TiUIView createView(Activity activity) {
		swipeRefresh = new SwipeRefresh(this);
		return this.swipeRefresh;
	}
    
	/* Public API */
	
	@Kroll.method
	public void setRefreshing(boolean refreshing) {
        if (TiApplication.isUIThread()) {
            doSetRefreshing(refreshing);
        } else {
            Message message = getMainHandler().obtainMessage(MSG_SET_REFRESHING, refreshing);
            message.sendToTarget();
        }

	}
    
	@Kroll.method @Kroll.getProperty
	public boolean isRefreshing() {
		return this.swipeRefresh.isRefreshing();
	}
    
	/* Utilities */
    
	public boolean handleMessage(Message message) {
		switch (message.what) {
			case MSG_SET_REFRESHING: {
				doSetRefreshing((Boolean) message.obj);
				return true;
			}
		}
		
		return super.handleMessage(message);
	}
	
	protected void doSetRefreshing(boolean refreshing) {
		this.swipeRefresh.setRefreshing(refreshing);
	}
}
