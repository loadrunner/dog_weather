package com.example.dogweather;

import com.example.dogweather.GlobalState.Breed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IntroActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.intro);
		
		ViewGroup breedContainer = (ViewGroup) findViewById(R.id.breed_container);
		Breed[] breeds = Breed.values();
		for (int i = 0; i < breeds.length; i++) {
			ImageView breed = new ImageView(this);
			breed.setImageResource(breeds[i].getDrawableResource());
			breed.setTag(Integer.valueOf(i));
			breed.setOnClickListener(this);
			breed.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			breedContainer.addView(breed);
		}
	}
	
	@Override
	public void onClick(View v) {
		GlobalState app = (GlobalState) getApplication();
		app.setDogName(((TextView) findViewById(R.id.dog_name_input)).getEditableText().toString());
		app.setDogBreed(Breed.values()[((Integer) v.getTag())]);
		app.setConfigured(true);
		app.savePreferences();
		
		Intent intent = new Intent(this,  MainActivity.class);
		startActivity(intent);
		finish();
	}
}
