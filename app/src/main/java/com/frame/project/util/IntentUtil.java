package com.frame.project.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fanya.test.R;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/7/25 15:30
 * 页面名称:
 */

public enum IntentUtil {
    INSTRANSE ;
    private Intent intent = null;
    private Bundle bundle = null;
    //下一个页面获取传参数据
    protected Bundle getBundle(AppCompatActivity appCompatActivity) {
        if (intent == null || bundle == null) {
            intent = appCompatActivity.getIntent();
            if (bundle == null) {
                bundle = intent.getExtras();
                if (bundle == null) {
                    bundle = new Bundle();
                }
            }
        }
        return bundle;
    }


    //打开页面,不关闭
    public void openActivity(AppCompatActivity from , Class<?> cls, Bundle extras) {
        closeActivity(from , cls, extras, false);
    }

    public void jumpActivity(AppCompatActivity from , Class<?> cls, Bundle extras) {
        closeActivity(from , cls, extras, true);
    }

    //打开页面,关闭页面
    public void closeActivity(AppCompatActivity from , Class<?> tocls, Bundle extras, boolean b) {
        intent = new Intent();
        if (extras != null) {
            intent.putExtras(extras);
        }
        intent.setClass(from, tocls);
        from.startActivity(intent);
        if (b) {
            from.finish();
        }
        from.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }
    //带请求码过去的
    public void openActivityForResult(AppCompatActivity from , Class<?> to, int responseCode, Bundle bundle) {
        intent = new Intent(from, to);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        from.startActivityForResult(intent, responseCode);
        from.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }
}
