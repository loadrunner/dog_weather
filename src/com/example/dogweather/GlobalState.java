package com.example.dogweather;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class GlobalState extends Application {
	public static enum Units {
		CELSIUS, FAHRENHEIT
	}
	
	public final static String SETTINGS_FILE = "app_settings";
	public final static Units DEFAULT_UNITS = Units.FAHRENHEIT;
	
	private Units mUnits;
	private String mDogName;
	private String mLocation;
	
	private static GlobalState mInstance = null;
	
	public static GlobalState getInstance() {
		return mInstance;
	}
	
	private SharedPreferences getSharedPreferences() {
		return getSharedPreferences(SETTINGS_FILE, MODE_PRIVATE);
	}
	
	public Context getContext() {
		return super.getApplicationContext();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		
		reloadPreferences();
	}
	
	public Units getUnits() {
		return mUnits;
	}
	public String getDogName() {
		return mDogName;
	}
	public String getLocation() {
		return mLocation;
	}
	
	public void setUnits(Units type) {
		mUnits = type;
	}
	public void setDogName(String name) {
		if (name != null && name.length() > 0) {
			mDogName = name;
		}
	}
	public void setLocation(String location) {
		if (location == null || location.length() > 0) {
			mLocation = location;
		}
	}
	
	public void reloadPreferences() {
		SharedPreferences settings = getSharedPreferences();
		
		int units = settings.getInt("units", DEFAULT_UNITS.ordinal());
		mUnits = Units.values()[units];
		
		mDogName = settings.getString("dog_name", null);
		mLocation = settings.getString("location", null);
	}
	
	public void savePreferences() {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		
		editor.putInt("units", mUnits.ordinal());
		editor.putString("dog_name", mDogName);
		editor.putString("location", mLocation);
		
		editor.commit();
	}
}
