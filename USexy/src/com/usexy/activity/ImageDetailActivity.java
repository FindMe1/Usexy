package com.usexy.activity;

import java.util.ArrayList;


import com.usexy.adapter.ImageDetailAdapter;
import com.usexy.control.ZoomImageView.OneClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageDetailActivity extends Activity implements OnClickListener,
		OnPageChangeListener, OneClickListener {
	private String Tag="ImageDetailActivity";
	public final static String CURRENT_INDEX = "current_index";
	public final static String MODEL = "model";
	private ArrayList<String> imgDetails = null;
	private int currentIndex = 0;
	private ViewPager viewPager;
	private ImageView back;
	private ImageView share;
	private TextView textCurrent;
	private ImageDetailAdapter adapter;
	private LinearLayout itemTitleLayout;
	private boolean toggle=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.image_detail);
		Intent intent = getIntent();
		imgDetails=intent.getStringArrayListExtra(MODEL);
		currentIndex=intent.getIntExtra(CURRENT_INDEX, 0);
		itemTitleLayout=(LinearLayout)findViewById(R.id.product_item_title);
		viewPager=(ViewPager)findViewById(R.id.image_detail_viewPager);
		back=(ImageView)findViewById(R.id.image_item_back);
		share=(ImageView)findViewById(R.id.image_item_share);
		textCurrent=(TextView)findViewById(R.id.image_item_page);
		viewPager.setOnPageChangeListener(this);
		adapter=new ImageDetailAdapter(this, imgDetails);
		adapter.creatView(this);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(currentIndex);
		textCurrent.setText((currentIndex+1)+"/"+imgDetails.size());
		back.setOnClickListener(this);
		share.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.image_item_back:
			onBackPressed();
			break;
		case R.id.image_item_share:
			
			;
			break;
		default:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		textCurrent.setText((arg0 + 1) + "/" + imgDetails.size());
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		Log.e(Tag, "被双击了");
		if(!toggle){
			Animation animation=new AlphaAnimation(1, 0);
			animation.setDuration(500);
			itemTitleLayout.setAnimation(animation);
			itemTitleLayout.setVisibility(View.GONE);;
			toggle=true;
		}
		else{
			Animation animation=new AlphaAnimation(0, 1);
			animation.setDuration(500);
			itemTitleLayout.setAnimation(animation);
		itemTitleLayout.setVisibility(View.VISIBLE);
		toggle=false;
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
