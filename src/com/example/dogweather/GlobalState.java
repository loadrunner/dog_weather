package com.example.dogweather;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class GlobalState extends Application {
	public enum Units {
		CELSIUS, FAHRENHEIT
	}
	
	public enum Breed {
		GENERIC,
		BREED1,
		BREED2,
		BREED3,
		BREED4,
		BREED5
	}
	
	public final static String SETTINGS_FILE = "app_settings";
	public final static Units DEFAULT_UNITS = Units.FAHRENHEIT;
	public final static Breed DEFAULT_BREED = Breed.GENERIC;
	
	private Units mUnits;
	private String mDogName;
	private Breed mDogBreed;
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
	public Breed getDogBreed() {
		return mDogBreed;
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
	public void setDogBreed(Breed breed) {
		mDogBreed = breed;
	}
	public void setLocation(String location) {
		if (location == null || location.length() > 0) {
			mLocation = location;
		}
	}
	
	public void reloadPreferences() {
		SharedPreferences settings = getSharedPreferences();
		
		int units = settings.getInt("units", -1);
		if (units >= 0 && units < Units.values().length)
			mUnits = Units.values()[units];
		else
			mUnits = DEFAULT_UNITS;
		
		mDogName = settings.getString("dog_name", null);
		
		try {
			String breed = settings.getString("breed", DEFAULT_BREED.toString());
			mDogBreed = Breed.valueOf(breed);
		} catch (Exception e) {
			mDogBreed = DEFAULT_BREED;
		}
		
		mLocation = settings.getString("location", null);
	}
	
	public void savePreferences() {
		SharedPreferences.Editor editor = getSharedPreferences().edit();
		
		editor.putInt("units", mUnits.ordinal());
		editor.putString("dog_name", mDogName);
		editor.putString("breed", mDogBreed.name());
		editor.putString("location", mLocation);
		
		editor.commit();
	}
}
