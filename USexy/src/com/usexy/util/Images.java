package com.usexy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class Images {
	public List<String> urls;
	private static Images instance;

	public static Images getInstance() {
		if (instance == null) {
			instance = new Images();
		}

		return instance;
	}

	public List<String> getStringFromAssert(String fileName, Context context) {
		if (urls == null) {
			urls = new ArrayList<String>();
		}
		if (urls!=null&&urls.size() > 0) {
			urls.clear();
		}
		InputStream inputStream;
		try {
			inputStream = context.getAssets().open(fileName);

			if (inputStream != null) {
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(inputStream, "UTF-8"));
				String url = "";
				while ((url = bufferedReader.readLine()) != null) {
					urls.add(url);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return urls;
	}

}
