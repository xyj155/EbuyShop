package com.example.home.fragment.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.home.fragment.entity.ComplexItemEntity;
import com.gongwen.marqueen.MarqueeFactory;
import com.xuyijie.home.R;

public class ComplexViewMF extends MarqueeFactory<LinearLayout, ComplexItemEntity> {
    private LayoutInflater inflater;

    public ComplexViewMF(Context mContext) {
        super(mContext);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public LinearLayout generateMarqueeItemView(ComplexItemEntity data) {
        LinearLayout mView = (LinearLayout) inflater.inflate(R.layout.marqueen_item_layout, null);
        ((TextView) mView.findViewById(R.id.tv_title)).setText(data.getTitle());
        return mView;
    }
}