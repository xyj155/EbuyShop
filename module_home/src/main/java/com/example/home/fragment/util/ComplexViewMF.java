package com.example.home.fragment.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.commonlib.gson.MarQueenGson;
import com.gongwen.marqueen.MarqueeFactory;
import com.xuyijie.home.R;

public class ComplexViewMF extends MarqueeFactory<LinearLayout, MarQueenGson> {
    private LayoutInflater inflater;

    public ComplexViewMF(Context mContext) {
        super(mContext);
        inflater = LayoutInflater.from(mContext);
    }

    private static final String TAG = "ComplexViewMF";
    @Override
    public LinearLayout generateMarqueeItemView(MarQueenGson data) {
        Log.i(TAG, "generateMarqueeItemView: "+data);
        LinearLayout mView = (LinearLayout) inflater.inflate(R.layout.marqueen_item_layout, null);
        ((TextView) mView.findViewById(R.id.tv_title)).setText(data.getMar_text());
        return mView;
    }
}