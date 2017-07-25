package com.frame.project.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/7/25 15:59
 * 页面名称:
 */

public enum ImageLoadUtil {
    INSTANSE ;
    //显示图片
    protected void loadPic(ImageView imageViewId, String picurl, Context mContext) {
        if (!TextUtils.isEmpty(picurl)) {
            Picasso.with(mContext)
                    .load(picurl)
                    .into(imageViewId);
        }
    }
}
