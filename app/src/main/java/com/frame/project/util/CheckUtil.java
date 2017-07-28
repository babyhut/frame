package com.frame.project.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by xuyanyun on 2017/6/9.
 */

public enum CheckUtil {
    INSTANCE ;

    /* 13+任意数
        * 15+除4的任意数
        * 18+除1和4的任意数
        * 17+除9的任意数
        * 147
                */
    public  boolean isPhone(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    public  boolean isNull(String string){
        if (TextUtils.isEmpty(string) || string.equals("null"))  {
            return true;
        }
        return false;
    }

}
