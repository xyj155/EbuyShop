package com.example.commonlib.view.previewphoto;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.commonlib.MyApp;
import com.example.commonlib.R;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static com.payelves.sdk.EPay.TAG;


/**
 * 类       名:图片预览
 * 说       明:
 * date   2017/03/6
 * author   maimingliang
 */
public class PhotoPagerAdapter extends PagerAdapter {

    public interface PhotoViewClickListener {
        void OnPhotoTapListener(View view, float v, float v1);
    }

    public PhotoViewClickListener listener;

    //    private List<String> paths = new ArrayList<>();
    private List<String> paths = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int resId = R.mipmap.app_icon;
    private boolean isSkipMemory = false;

    public PhotoPagerAdapter(Context mContext, List<String> paths, int resId) {
        Log.i(TAG, "PhotoPagerAdapter: "+paths.get(0));
        this.mContext = mContext;
        this.paths = paths;
        this.resId = resId;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setPhotoViewClickListener(PhotoViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = mLayoutInflater.inflate(R.layout.item_preview, container, false);

        final PhotoView imageView = (PhotoView) itemView.findViewById(R.id.iv_pager);


        final ImageView smallImageView = (ImageView) itemView.findViewById(R.id.small_img);

        final String path = paths.get(position);
//        String testUrl ="/data/user/0/com.elk.tourist/cache/image_manager_disk_cache/889639cde3de11f3355d783cfd5e99ad966cadf5543bd8ae0a1c592b7904b2b6.0" ;
        RequestOptions requestOptions = new RequestOptions();
        requestOptions
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(resId);
        if (!TextUtils.isEmpty(path)) {

            smallImageView.setVisibility(View.VISIBLE);

            Glide.with(MyApp.getInstance())
                    .load(path)
                    .apply(requestOptions)
                    .into(smallImageView);
        } else {
            smallImageView.setVisibility(View.GONE);
        }
        Log.i(TAG, "instantiateItem: "+path);

        Glide.with(mContext)
                .load(path)
                .apply(requestOptions)
                .into(new Target<Drawable>() {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        if (smallImageView != null) {
                            smallImageView.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (smallImageView != null) {
                            smallImageView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void getSize(@NonNull SizeReadyCallback cb) {

                    }

                    @Override
                    public void removeCallback(@NonNull SizeReadyCallback cb) {

                    }

                    @Override
                    public void setRequest(@Nullable Request request) {

                    }

                    @Nullable
                    @Override
                    public Request getRequest() {
                        return null;
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onStop() {

                    }

                    @Override
                    public void onDestroy() {

                    }
                });


        imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                if (listener != null) {
                    listener.OnPhotoTapListener(view, v, v1);
                }
            }

        });

        container.addView(itemView);

        return itemView;
    }


    @Override
    public int getCount() {
        return paths.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
