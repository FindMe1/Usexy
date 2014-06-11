package com.usexy.adapter;

import java.util.List;
import java.util.Random;

import com.android.volley.toolbox.ImageLoader;
import com.meixiu.data.ImageCacheManager;
import com.usexy.activity.R;
import com.usexy.bitmapfun.util.ImageCache.ImageCacheParams;
import com.usexy.bitmapfun.util.ImageFetcher;
import com.usexy.control.DynamicHeightImageView;
import com.usexy.util.DensityUtils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter {
	private Context context;
	private int width;
	private ImageFetcher mImageWorker;
	private List<String> listimgs;
	private  Random mRandom;
	private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
	private ImageLoader.ImageContainer imageRequest;
	private Drawable mDefaultImageDrawable;
	private static final int[] COLORS = {R.color.holo_blue_light, R.color.holo_green_light, R.color.holo_orange_light, R.color.holo_purple_light, R.color.holo_red_light};
	private Resources mResource;
    private static final int IMAGE_MAX_HEIGHT = 240;
	public ImageAdapter(Context context, List<String> listimgs) {
		// TODO Auto-generated constructor stub
		this.context = context;
		mResource = context.getResources();
		this.listimgs = listimgs;
		mRandom = new Random();
		ImageCacheParams cacheParams = new ImageCacheParams(context, "images");
		cacheParams.setMemCacheSizePercent(context, 0.25f);
		cacheParams.memoryCacheEnabled = true;
		// The ImageWorker takes care of loading images into our ImageView
		// children asynchronously
		mImageWorker = new ImageFetcher(context, width);
		mImageWorker.setLoadingImage(R.drawable.loading_image);
		mImageWorker.addImageCache(cacheParams);
	}

	public ImageFetcher getmImageWorker() {
		return mImageWorker;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listimgs.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listimgs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.gridview_item, null);
			holder = new ViewHolder();
			holder.icon1 = (ImageView) convertView
					.findViewById(R.id.grid_itemimage);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//		double positionHeight = getPositionRatio(position);
//		holder.icon1.setHeightRatio(positionHeight);
		
//		mImageWorker.loadImage(listimgs.get(position), holder.icon1);
		if(imageRequest!=null){
			imageRequest.cancelRequest();
		}
		mDefaultImageDrawable = new ColorDrawable(mResource.getColor(COLORS[position % COLORS.length]));
		imageRequest=ImageCacheManager.loadImage(listimgs.get(position), ImageCacheManager
                .getImageListener(holder.icon1, mDefaultImageDrawable, mDefaultImageDrawable), 0, DensityUtils.dip2px(context, IMAGE_MAX_HEIGHT));
		return convertView;
	}

	static class ViewHolder {
		ImageView icon1;
	}

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
//            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }
    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }

}
