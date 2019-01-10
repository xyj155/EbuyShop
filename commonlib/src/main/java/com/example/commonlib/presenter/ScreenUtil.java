package com.example.commonlib.presenter;

import android.content.Context;

public class ScreenUtil {
    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
