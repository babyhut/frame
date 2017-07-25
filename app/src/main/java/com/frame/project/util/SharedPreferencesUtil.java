package com.frame.project.util;

import com.frame.project.sharedpreferences.ISharedPreferencesDao;
import com.frame.project.sharedpreferences.SharedPreferencesDaoImpl;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/7/25 16:01
 * 页面名称:
 */

public enum SharedPreferencesUtil {
    INSTRANSE;

    private ISharedPreferencesDao shareDao;

    public ISharedPreferencesDao getShareDao() {
        if (shareDao == null) {
            shareDao = new SharedPreferencesDaoImpl();
        }
        return shareDao;
    }
}
