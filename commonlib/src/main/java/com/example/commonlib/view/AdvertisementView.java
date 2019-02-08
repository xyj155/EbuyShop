package com.example.commonlib.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.commonlib.R;
import com.example.commonlib.R2;

public class AdvertisementView extends Dialog  {

    public AdvertisementView(Context context) {
        super(context);

//
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advertisement_popuplayout);
        //设置点击布局外则Dialog消失
        setCanceledOnTouchOutside(true);
        ImageView viewById1 = findViewById(R2.id.iv_banner);
        ImageView viewById = findViewById(R2.id.iv_close_ad);
//        viewById.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                dismiss();
//            }
//        });
    }

    public void showDialog() {
        Window window = getWindow();
        //设置弹窗动画
        window.setWindowAnimations(R.style.style_dialog);
        //设置Dialog背景色
        window.setBackgroundDrawableResource(R.color.transent);
        WindowManager.LayoutParams wl = window.getAttributes();
        //设置弹窗位置
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
        show();

    }


}
