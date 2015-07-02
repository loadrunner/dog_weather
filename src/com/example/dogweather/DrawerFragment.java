package com.example.dogweather;

import com.example.dogweather.GlobalState.Units;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DrawerFragment extends Fragment {
	
	private GlobalState mGlobalState;
	
	private NavigationDrawerCallbacks mCallbacks;
	
	private DrawerLayout mDrawerLayout;
	private View mFragmentContainerView;
	private ViewGroup mViewContainer;
	
	public DrawerFragment() {
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mViewContainer = (ViewGroup) inflater.inflate(R.layout.fragment_drawer, container, false);
		
		((TextView) mViewContainer.findViewById(R.id.dog_name)).setText(mGlobalState.getDogName());
		
		final Button unitsCButton = (Button) mViewContainer.findViewById(R.id.units_c);
		final Button unitsFButton = (Button) mViewContainer.findViewById(R.id.units_f);
		
		unitsCButton.setPressed(mGlobalState.getUnits() == Units.CELSIUS);
		unitsFButton.setPressed(mGlobalState.getUnits() == Units.FAHRENHEIT);
		
		unitsCButton.setOnTouchListener(new View.OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					mGlobalState.setUnits(Units.CELSIUS);
					mGlobalState.savePreferences();
					
					unitsCButton.setPressed(true);
					unitsFButton.setPressed(false);
					
					mCallbacks.unitsChanged();
				}
				
				return true;
			}
		});
		
		unitsFButton.setOnTouchListener(new View.OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					mGlobalState.setUnits(Units.FAHRENHEIT);
					mGlobalState.savePreferences();
					
					unitsCButton.setPressed(false);
					unitsFButton.setPressed(true);
					
					mCallbacks.unitsChanged();
				}
				
				return true;
			}
		});
		
		return mViewContainer;
	}
	
	public boolean isDrawerOpen() {
		return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
	}
	
	public void setUp(int fragmentId, DrawerLayout drawerLayout) {
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			mCallbacks = (NavigationDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
		}
		
		mGlobalState = (GlobalState) activity.getApplication();
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		
		mCallbacks = null;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
	}
	
	public static interface NavigationDrawerCallbacks {
		void unitsChanged();
	}
}
