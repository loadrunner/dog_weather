package com.example.dogweather;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends FragmentActivity implements
		DrawerFragment.NavigationDrawerCallbacks {
	
	private DrawerFragment mNavigationDrawerFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (!((GlobalState) getApplication()).isConfigured()) {
			Intent intent = new Intent(this, IntroActivity.class);
			startActivity(intent);
			finish();
		}
		
		setContentView(R.layout.activity_main);
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container, MainFragment.getInstance())
				.commit();
		
		mNavigationDrawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
	}
	
	@Override
	public void unitsChanged() {
		// TODO Auto-generated method stub
		
	}
}
