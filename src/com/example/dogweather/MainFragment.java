package com.example.dogweather;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {
	private static MainFragment mInstance;
	
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
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		
		return rootView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
	}
}
