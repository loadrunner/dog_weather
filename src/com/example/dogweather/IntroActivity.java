package com.example.dogweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class IntroActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.intro);
		
		ViewGroup breedContainer = (ViewGroup) findViewById(R.id.breed_container);
		for (int i = 0; i < breedContainer.getChildCount(); i++) {
			breedContainer.getChildAt(i).setOnClickListener(this);
		}
	}
	
	@Override
	public void onClick(View v) {
		Bundle bundle = new Bundle();
		bundle.putString("breed", (String) v.getTag());
		bundle.putString("name", ((TextView) findViewById(R.id.dog_name_input)).getEditableText().toString());
		
		Intent intent = new Intent(this,  MainActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
	}
}
