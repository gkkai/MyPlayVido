package com.example.kk.myplayvido;

import android.content.Context;

/**
 * Created by kk on 2017/9/6.
 */

public class DensityUtil {
    public static final float getHeightInPx(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    }
    public static final float getWidthInPx(Context context) {
        final float width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }


    public static float dip2px(VitamioPlayerActivity vitamioPlayerActivity, float v) {
        return 2;
    }
}
