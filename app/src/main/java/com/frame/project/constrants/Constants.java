package com.frame.project.constrants;

import android.Manifest;

/**
 * Created by xuyanyun on 2017/5/22.
 */

public interface Constants {
    //项目里保存的键值
    String SHARED_PREFERNECES_NAME = "com.fanya.lovetrain";
    //专门保存cookies的
    String SHARED_PREFERNECES_NAME_FORCOOKIES = "com.fanya.lovetrain.cookies";
    //项目地址
    String PROJETURL = "http://115.28.225.48:8099/training/app/";

    //权限申请
    interface permissionStr {
        int permissionResponse = 0x99;
        //写入sd
        String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        //打电话
        String CALL_PHONE = Manifest.permission.CALL_PHONE;
        //检测网络
        String ACCESS_NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE;
        //相机
        String CAMERA = Manifest.permission.CAMERA;
        //读取通话记录
        String READ_CAll_LOG = Manifest.permission.READ_CALL_LOG;
    }

    //接口请求
    interface requestUrl {
        String LOGINURL = "login.jsp";
        String REGEISTURL = "regeist.jsp";
    }

    interface basis {
        int PAGESSIZE = 10;
        String USERNAME = "";
        String USERPSWD = "";
    }

}
