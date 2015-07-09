package com.example.dogweather;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.dogweather.GlobalState.Units;

@SuppressLint("DefaultLocale")
public class Weather {
	public static enum WeatherStatus {
		SUNNY, CLOUDY, CLOUDS, RAINY, RAIN, THUNDERSTORM, SNOW
	}
	
	public static String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather?APPID=3ceef9d4ba9bddb275a84a23495bd8ff";
	
	public static void getWeatherStatus(float lat, float lng, WeatherCallback c) {
		try {
			new Task().execute(new Pair<URL, WeatherCallback>(new URL(WEATHER_API_URL + "&units=" + (GlobalState.getInstance().getUnits() == Units.CELSIUS ? "metric" : "imperial") + String.format("&lat=%.2f&lon=%.2f", lat, lng)), c));
		} catch (Exception e) {
			c.onWeatherFailed("api error");
		}
	}
	
	public static void getWeatherStatus(String location, WeatherCallback c) {
		try {
			new Task().execute(new Pair<URL, WeatherCallback>(new URL(WEATHER_API_URL + "&units=" + (GlobalState.getInstance().getUnits() == Units.CELSIUS ? "metric" : "imperial") + "&q=" + URLEncoder.encode(location, "utf-8")), c)) 	;
		} catch (Exception e) {
			c.onWeatherFailed("api error");
		}
	}
	
	static class Task extends AsyncTask<Pair<URL, WeatherCallback>, Void, JSONObject> {
		private WeatherCallback mCallback;
		
		@Override
		protected JSONObject doInBackground(Pair<URL, WeatherCallback>... params) {
			mCallback = params[0].second;
			
			try {
				HttpURLConnection conn = (HttpURLConnection) params[0].first.openConnection();
				conn.setReadTimeout(15000);
				conn.setRequestProperty("charset", "utf-8");
				InputStream is = conn.getInputStream();
				InputStreamReader r = new InputStreamReader(is);
				Reader in = new BufferedReader(r);
				StringBuffer buffer = new StringBuffer();
				int ch;
				while ((ch = in.read()) > -1) {
					buffer.append((char)ch);
				}
				in.close();
				
				return new JSONObject(buffer.toString());
			} catch (JSONException e) {
				mCallback.onWeatherFailed("json exception");
			} catch (Exception e) {
				e.printStackTrace();
				mCallback.onWeatherFailed("generic error");
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			
			if (result == null)
				return;
			
			try {
				JSONObject main = result.getJSONObject("main");
				JSONObject weather = result.getJSONArray("weather").getJSONObject(0);
				
				WeatherStatus status;
				if (weather.getString("main").toLowerCase().contains("sun"))
					status = WeatherStatus.SUNNY;
				else
					status = WeatherStatus.CLOUDY;
				
				mCallback.onWeatherFinished((float) main.getDouble("temp"), status);
			} catch (JSONException e) {
				e.printStackTrace();
				mCallback.onWeatherFailed("json exception");
			}
		}
	}
	
	interface WeatherCallback {
		public void onWeatherFinished(float temperature, WeatherStatus status);
		
		public void onWeatherFailed(String message);
	}
}
