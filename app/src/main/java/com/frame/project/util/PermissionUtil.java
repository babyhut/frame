package com.frame.project.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.frame.project.constrants.Constants;
import com.frame.project.listener.PermissionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/7/25 15:40
 * 页面名称:
 */

public enum PermissionUtil   {
    INSTRANSE ;
    private PermissionListener permissionListener ;
    //请求权限
    public void responsePermission(AppCompatActivity appCompatActivity ,PermissionListener permissionListener, String... permissionName) {
        this.permissionListener = permissionListener;
        if (Build.VERSION.SDK_INT < 21) {
            permissionListener.hasNoPermission();
            return;
        }
        List<String> pList = new ArrayList<>();
        for (String str : permissionName) {
            pList.add(str);
        }
        String[] strings = pList.toArray(new String[pList.size()]);
        boolean bb = checkPermissionAllGranted(appCompatActivity,strings);
        if (!bb) {
            //如果没有,请求
            ActivityCompat.requestPermissions(appCompatActivity, strings, Constants.permissionStr.permissionResponse);
        } else {
            //有权限
            permissionListener.hasPermission();
        }
    }


    /**
     * 检查APP是否拥有指定的所有权限
     */
    protected boolean checkPermissionAllGranted(Context mContext, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        if (requestCode == Constants.permissionStr.permissionResponse) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //给了权限可以直接去操作
//                permissionListener.hasPermission();
//            } else {
//                ToastUtil.INSTRANSE.showError("权限都不给,没法玩了",this);
//            }
//        }
//    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        if (requestCode == Constants.permissionStr.permissionResponse) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //给了权限可以直接去操作
                permissionListener.hasPermission();
            } else {
                permissionListener.hasNoPermission();
//                ToastUtil.INSTRANSE.showError("权限都不给,没法玩了",this);
            }
        }
    }
}
