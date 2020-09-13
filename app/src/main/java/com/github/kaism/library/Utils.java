package com.github.kaism.library;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Utils {

	public static void loadImage(ImageView imageView, String url) {
		imageView.setClipToOutline(true);
		Picasso.get()
				.load(url)
				.placeholder(R.drawable.ic_no_image)
				.into(imageView);
	}
}
