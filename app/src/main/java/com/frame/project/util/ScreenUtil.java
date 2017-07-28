package com.frame.project.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/7/28 14:43
 * 页面名称:
 */

public enum ScreenUtil {
    INSTRANSE;
    public int getScreenHeight(Context mContext) {
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
    public int getScreenWidth(Context mContext) {
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
}
