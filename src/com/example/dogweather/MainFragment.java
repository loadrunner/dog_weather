package com.example.dogweather;

import com.example.dogweather.GlobalState.Units;
import com.example.dogweather.Weather.WeatherStatus;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment implements DrawerFragment.NavigationDrawerCallbacks {
	private static MainFragment mInstance;
	
	private GlobalState mGlobalState;
	
	private ViewGroup mViewContainer;
	
	public static MainFragment getInstance() {
		if (mInstance == null) {
			mInstance = new MainFragment();
			Bundle args = new Bundle();
		//	args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			mInstance.setArguments(args);
		}
		return mInstance;
	}
	
	public MainFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mViewContainer = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
		
		return mViewContainer;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		mGlobalState = (GlobalState) activity.getApplication();
	}
	
	@Override
	public void onUnitsChanged() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onLocationChanged() {
		Weather.getWeatherStatus(mGlobalState.getCurrentLocation().first, new Weather.WeatherCallback() {
			@Override
			public void onWeatherFinished(float temperature, WeatherStatus status) {
				((TextView) mViewContainer.findViewById(R.id.temp)).setText(String.format("%.1f \u00b0 %s", temperature, mGlobalState.getUnits() == Units.CELSIUS ? "C" : "F"));
			}
			
			@Override
			public void onWeatherFailed(String message) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
