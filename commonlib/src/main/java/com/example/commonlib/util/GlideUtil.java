package com.example.commonlib.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.commonlib.MyApp;
import com.example.commonlib.R;

public class GlideUtil {
    public static void loadGeneralImage(Object url, ImageView imageView) {
        Glide.with(MyApp.getInstance()).asBitmap().load(url).into(imageView);
    }

    public static void loadGeneralImageWithAnim(Object url, ImageView imageView) {
        Glide.with(MyApp.getInstance()).asBitmap().load(url).transition(BitmapTransitionOptions.withCrossFade(1500)).into(imageView);
    }


    public static void loadRoundCornerImage(String url, ImageView imageView) {
        RoundedCorners roundedCorners = new RoundedCorners(10);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Glide.with(MyApp.getInstance()).asBitmap().apply(options).load(url).into(imageView);
    }

    public static void loadRoundCornerAvatarImage(Object url, ImageView imageView, int radius) {
        RoundedCorners roundedCorners = new RoundedCorners(radius);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300).error(R.mipmap.img_home_day);
        Glide.with(MyApp.getInstance()).asBitmap().apply(options).load(url).into(imageView);
    }
}
