package com.frame.project.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.fanya.test.R;
import com.frame.project.constrants.Constants;
import com.frame.project.ui.CustomHandle;

import es.dmoral.toasty.Toasty;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/7/25 15:05
 * 页面名称:
 */

public enum ToastUtil {
    INSTRANSE ;

    private CustomHandle customHandle ;
    //初始化handler
    private void initHandler(){
        if(customHandle == null){
            customHandle = new CustomHandle();
        }
    }
    //发送再次点击的消息

    //错误
    public void showError(String str, Context mContext) {
        initHandler();
        if(customHandle.isCanClick){
            Toasty.error(mContext, str, Toast.LENGTH_SHORT, true).show();
            customHandle.obtainMessage(Constants.basis.one).sendToTarget();
        }

    }
    //成功
    public void showSuccess(String str, Context mContext) {
        initHandler();
        if(customHandle.isCanClick){
            Toasty.success(mContext, str, Toast.LENGTH_SHORT, true).show();
            customHandle.obtainMessage(Constants.basis.one).sendToTarget();
        }


    }
    /**
     * @param str       显示的文字
     * @param drawable  左边显示的图案
     * @param textColor 文字的颜色
     * @param tintColor toast背景色
     */
    public void showCustom(String str, Drawable drawable, int textColor, int tintColor, Context mContext) {
        initHandler();
        if(customHandle.isCanClick){
            Toasty.custom(mContext, str, drawable, textColor, tintColor, true, true).show();
            customHandle.obtainMessage(Constants.basis.one).sendToTarget();
        }


    }

    public void showCustom(String str, Drawable drawable, Context mContext) {
        initHandler();
        if(customHandle.isCanClick){
            showCustom(str, drawable, mContext.getResources().getColor(R.color.colorPrimaryDark), mContext.getResources().getColor(R.color.colorPrimary),mContext);
            customHandle.obtainMessage(Constants.basis.one).sendToTarget();
        }


    }

    public void showCustomError(String str, Context mContext) {
        showCustom(str, mContext.getResources().getDrawable(R.drawable.cry),mContext);
    }
    public void showCustomSuccess(String str, Context mContext) {
        showCustom(str, mContext.getResources().getDrawable(R.drawable.smile),mContext);
    }
}
