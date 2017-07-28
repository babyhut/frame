package com.frame.project.util;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/7/28 11:11
 * 页面名称:
 */

public enum  SavePicUtil {

    INSTARNS ;

    public  String getSDCardPath() {
        File sdcardDir = null;
        boolean sdcardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
        }else {
            sdcardDir = Environment.getRootDirectory();
        }
        return sdcardDir.toString();
    }
    public  int  savePic(Bitmap b, File filePath) {
        FileOutputStream fos = null;
        int code = 0 ;

        try {
            fos = new FileOutputStream(filePath);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
                code = 1;
                return code  ;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return code ;
        } catch (IOException e) {
            e.printStackTrace();
            return code  ;
        }
        return code ;
    }
    }
