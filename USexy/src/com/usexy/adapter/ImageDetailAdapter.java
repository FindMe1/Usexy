package com.usexy.adapter;

import java.util.ArrayList;
import java.util.List;

import com.usexy.activity.R;
import com.usexy.bitmapfun.util.ImageCache.ImageCacheParams;
import com.usexy.bitmapfun.util.ImageFetcher;
import com.usexy.bitmapfun.util.ImageWorker.LoadImageCallBack;
import com.usexy.control.ZoomImageView;
import com.usexy.control.ZoomImageView.OneClickListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ImageDetailAdapter extends PagerAdapter {
	private ArrayList<String> pictures;
	private Context context;
	private ImageFetcher imageFetcher;
	private OneClickListener oneClickListener;
	private List<View> itemViews;
	private int instantiate_cut_p = -1;
	private ArrayList<Integer> countArray = new ArrayList<Integer>();

	public ImageDetailAdapter(Context context, ArrayList<String> pictures) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.pictures = pictures;
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		int width = display.getWidth();
		ImageCacheParams cacheParams = new ImageCacheParams(context, "images");
		cacheParams.setMemCacheSizePercent(context, 0.25f);
		cacheParams.memoryCacheEnabled = true;
		imageFetcher = new ImageFetcher(context, width);
		imageFetcher.setLoadingImage(R.drawable.loading_image);
		imageFetcher.addImageCache(cacheParams);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pictures.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	public void creatView(OneClickListener oneClickListener) {
		this.oneClickListener = oneClickListener;
		itemViews = new ArrayList<View>();
		for (int i = 0; i < pictures.size(); i++) {
			View zoomView = View.inflate(context, R.layout.imgs_detail_item,
					null);
			itemViews.add(zoomView);
		}

	}
	@Override
	public void destroyItem(View container, int position, Object object) {
		if (instantiate_cut_p == position) {
			return;
		}
		((ViewPager) container).removeView(itemViews.get(position));
		countArray.remove(Integer.valueOf(position));
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		if (countArray.contains(position)) {

			((ViewPager) container).removeView(itemViews.get(position));
			instantiate_cut_p = position;
		} else {
			countArray.add(position);
		}
		View view = itemViews.get(position);
		ZoomImageView zoomImg = (ZoomImageView) view
				.findViewById(R.id.picture_item_image);
		ProgressBar barN = (ProgressBar) view
				.findViewById(R.id.picture_item_progress_no_length);
		LinearLayout bar = (LinearLayout) view
				.findViewById(R.id.picture_item_progress_layout);
		LoadImageAware aware = new LoadImageAware(barN, bar, zoomImg,
				oneClickListener);

		imageFetcher.loadImage(pictures.get(position), zoomImg, aware);

		((ViewPager) container).addView(view);
		return view;
	}
	class LoadImageAware implements LoadImageCallBack {

		private ProgressBar bar;
		private OneClickListener oneClickListener;
		private ZoomImageView zoomImg;
		private Handler handler;
		private boolean progressAble = false;
		private TextView progressText;
		private LinearLayout progressLayout;
		private ProgressBar barN;

		@SuppressLint("HandlerLeak")
		public LoadImageAware(ProgressBar barN, LinearLayout progressLayout,
				ZoomImageView view, OneClickListener oneClickListener) {
			super();
			this.barN = barN;
			this.progressLayout = progressLayout;
			this.bar = (ProgressBar) progressLayout
					.findViewById(R.id.picture_item_progress);
			this.progressText = (TextView) progressLayout
					.findViewById(R.id.picture_item_progress_text);
			this.oneClickListener = oneClickListener;
			this.zoomImg = view;

			handler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					final int progress = msg.what;
					switch (msg.what) {
					case -2:
						LoadImageAware.this.progressLayout
								.setVisibility(View.VISIBLE);
						LoadImageAware.this.barN.setVisibility(View.GONE);
						break;
					default:
						progressText.setText(progress + "%");
						break;
					}

				}

			};
		}

		@Override
		public void loadImageCompleted(ImageView imageView, Bitmap bitmap) {

			zoomImg.setImage(bitmap);
			zoomImg.setOneClickListener(oneClickListener);
			zoomImg.setVisibility(View.VISIBLE);
			progressLayout.setVisibility(View.GONE);
			LoadImageAware.this.barN.setVisibility(View.GONE);
		}

		@SuppressLint("HandlerLeak")
		@Override
		public void loadImageProgressAble(boolean progressAble) {
			this.progressAble = progressAble;
			if (progressAble) {
				handler.sendEmptyMessage(-2);
			}

		}

		@Override
		public void loadingImage(int currentProgress) {
			if (progressAble) {
				bar.setProgress(currentProgress);
				handler.sendEmptyMessage(currentProgress);
			}

		}

	}

}
