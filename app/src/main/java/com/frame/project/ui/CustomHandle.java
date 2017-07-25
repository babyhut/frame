package com.frame.project.ui;

import android.os.Handler;
import android.os.Message;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/7/25 17:24
 * 页面名称:
 */

public class CustomHandle  extends Handler  {

    public final int timeClick = 3000;
    public  final int SHOW = 1;
    public boolean isCanClick = true ;

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW:
                showAgain();
                break;
        }
    }

    private void showAgain(){
        isCanClick = false;
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                isCanClick = true ;
            }
        },timeClick);
    }

}
