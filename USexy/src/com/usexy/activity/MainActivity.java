package com.usexy.activity;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mobads.AdSize;
import com.baidu.mobads.AdView;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.usexy.adapter.CardsAnimationAdapter;
import com.usexy.adapter.ImageAdapter;
import com.usexy.control.XListView;
import com.usexy.control.XListView.IXListViewListener;
import com.usexy.util.Images;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements IXListViewListener,
		OnItemClickListener {
	private Images images;
	private List<String> imgurls;
	private XListView listView;
	private ImageAdapter adapter;
	public static final String SAVED_DATA_KEY = "SAVED_DATA";
	private int imgItems = 20;
	private List<String> currentItems = new ArrayList<String>();
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		listView = (XListView) findViewById(R.id.list_view);
		images = Images.getInstance();
		imgurls = images.getStringFromAssert("self.ini", this);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(false);
		setItems();
		adapter = new ImageAdapter(this, currentItems);
		AnimationAdapter animationAdapter = new CardsAnimationAdapter(adapter);
		animationAdapter.setAbsListView(listView);
		listView.setAdapter(animationAdapter);

		mHandler = new Handler();
		listView.setXListViewListener(this);
		listView.setOnItemClickListener(this);
	}

	private void setItems() {
		// TODO Auto-generated method stub
		if (currentItems.size() > 0) {
			currentItems.clear();
		}
		if (imgItems <= imgurls.size()) {
			for (int i = 0; i < imgItems; i++) {
				currentItems.add(imgurls.get(i));
			}
			imgItems = imgItems + 20;
		} else {
			for (int i = 0; i < imgurls.size(); i++) {
				currentItems.add(imgurls.get(i));
			}
		}

	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		// mHandler.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// // mAdapter.notifyDataSetChanged();
		// mAdapter = new ArrayAdapter<String>(XListViewActivity.this,
		// R.layout.list_item, items);
		// mListView.setAdapter(mAdapter);
		// onLoad();
		// }
		// }, 2000);
		Log.e("onRefresh", "上拉....");
		onLoad();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		Log.e("onRefresh", "下拉....");
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				setItems();
				adapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MainActivity.this, ImageDetailActivity.class);
		intent.putStringArrayListExtra(ImageDetailActivity.MODEL,
				(ArrayList<String>) currentItems);
		intent.putExtra(ImageDetailActivity.CURRENT_INDEX, position - 1);
		this.startActivity(intent);

	}

}
