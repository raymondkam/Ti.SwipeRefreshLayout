package com.rkam.swiperefreshlayout;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;
import org.appcelerator.titanium.util.TiRHelper;
import org.appcelerator.titanium.util.TiRHelper.ResourceNotFoundException;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.graphics.Color;
import android.view.LayoutInflater;

public class SwipeRefresh extends TiUIView {
	
	private SwipeRefreshLayout layout;
	private TiViewProxy view;
	
	public static final String PROPERTY_VIEW = "view";
	public static final String PROPERTY_COLOR_SCHEME = "colorScheme";
	
	int layout_swipe_refresh = 0;
	
	public SwipeRefresh(final SwipeRefreshProxy proxy) {
		super(proxy);
		
//		ActionBarActivity activity = (ActionBarActivity) proxy.getActivity();
		
		try {
			layout_swipe_refresh = TiRHelper.getResource("layout.swipe_refresh");
		}
		catch (ResourceNotFoundException e) {
			
		}
		
		LayoutInflater inflater = LayoutInflater.from(TiApplication.getInstance());
		layout = (SwipeRefreshLayout) inflater.inflate(layout_swipe_refresh, null, false);
		
		layout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (proxy.hasListeners("refreshing")) {
					proxy.fireEvent("refreshing", null);
				}
			}
		});
		
		setNativeView(layout);
	}

	@Override
	public void processProperties(KrollDict d) {
		if (d.containsKey(PROPERTY_VIEW)) {
			Object view = d.get(PROPERTY_VIEW);
			if (view != null && view instanceof TiViewProxy) {
				this.view = (TiViewProxy) view;
				this.layout.addView(this.view.getOrCreateView().getOuterView());
			}
		}
		if (d.containsKey(PROPERTY_COLOR_SCHEME)) {
			String[] colorStrings = (String[]) d.get(PROPERTY_COLOR_SCHEME);
			if (colorStrings != null && colorStrings.length == 4) {
				this.layout.setColorScheme(
					Color.parseColor(colorStrings[0]), 
					Color.parseColor(colorStrings[1]), 
					Color.parseColor(colorStrings[2]), 
					Color.parseColor(colorStrings[3])
				);
			}
		}
		super.processProperties(d);
	}
	
	public boolean isRefreshing() {
		return layout.isRefreshing();
	}
	
	public void setRefreshing(boolean refreshing) {
		this.layout.setRefreshing(refreshing);
	}
	
	public void setColorScheme(String[] colorStrings) {
		this.layout.setColorScheme(
			Color.parseColor(colorStrings[0]), 
			Color.parseColor(colorStrings[1]), 
			Color.parseColor(colorStrings[2]), 
			Color.parseColor(colorStrings[3]
		));
	}
}
