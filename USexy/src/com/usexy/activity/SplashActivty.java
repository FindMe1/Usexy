package com.usexy.activity;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;


public class SplashActivty extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		Timer timer=new Timer();
		TimerTask timerTask=new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SplashActivty.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		};
		timer.schedule(timerTask, 3000);
		
	}

}
