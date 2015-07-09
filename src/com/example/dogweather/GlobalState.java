package com.example.dogweather;

import java.util.Locale;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

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
		BREED5;
		
		public int getDrawableResource() {
			switch (this) {
				case BREED1:
					return R.drawable.ic_launcher;
				case BREED2:
					return R.drawable.ic_launcher;
				case BREED3:
					return R.drawable.ic_launcher;
				case BREED4:
					return R.drawable.ic_launcher;
				case BREED5:
					return R.drawable.ic_launcher;
				case GENERIC:
				default:
					return R.drawable.ic_launcher;
			}
		}
	}
	
	public final static String SETTINGS_FILE = "app_settings";
	public final static Units DEFAULT_UNITS = Units.FAHRENHEIT;
	public final static Breed DEFAULT_BREED = Breed.GENERIC;
	
	private boolean mConfigured;
	private Units mUnits;
	private String mDogName;
	private Breed mDogBreed;
	private String mLocation;
	private Pair<String, Location> mCurrentLocation = null;
	
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
		
		if (getLocation() == null) { // auto
			final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
			
			LocationListener locationListener = new LocationListener() {
				public void onLocationChanged(Location location) {
					Log.e("doggydog", location.getLatitude() + "x" + location.getLongitude());
					
					double lat = location.getLatitude();
					double lng = location.getLongitude();
					
					Geocoder geoCoder = new Geocoder(getContext(), Locale.getDefault());
					try {
						Address address = geoCoder.getFromLocation(lat, lng, 1).get(0);
						mCurrentLocation = new Pair<String, Location>(address.getLocality() + ", " + address.getAdminArea(), location);
						
						Log.e("doggydog", address.toString());
						Log.e("doggydog", mCurrentLocation.first);
					} catch (Exception e) {
						Log.e("doggydog", "exception " + lat + "," + lng);
						mCurrentLocation = new Pair<String, Location>(lat + "," + lng, location);
					}
					
					locationManager.removeUpdates(this);
				}
				
				public void onStatusChanged(String provider, int status, Bundle extras) {}
				
				public void onProviderEnabled(String provider) {}
				
				public void onProviderDisabled(String provider) {}
			};
			
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		} else {
			mCurrentLocation = new Pair<String, Location>(mLocation, null);
		}
	}
	
	public boolean isConfigured() {
		return mConfigured;
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
	
	public Pair<String, Location> getCurrentLocation() {
		return mCurrentLocation;
	}
	
	public void setConfigured(boolean configured) {
		mConfigured = configured;
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
		
		mConfigured = settings.getBoolean("configured", false);
		
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
		
		editor.putBoolean("configured", mConfigured);
		editor.putInt("units", mUnits.ordinal());
		editor.putString("dog_name", mDogName);
		editor.putString("breed", mDogBreed.name());
		editor.putString("location", mLocation);
		
		editor.commit();
	}
}
