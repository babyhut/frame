package com.frame.project.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/7/18 15:16
 * 页面名称:
 */

public enum IoUtil {
    INSTANSE ;

    private  final String DEFAULT_ENCODING = "utf-8";//编码
    private  final int PROTECTED_LENGTH = 51200;// 输入流保护 50KB

    public String readInfoStream(InputStream input) throws Exception {
        if (input == null) {
            throw new Exception("输入流为null");
        }
        //字节数组
        byte[] bcache = new byte[2048];
        int readSize = 0;//每次读取的字节长度
        int totalSize = 0;//总字节长度
        ByteArrayOutputStream infoStream = new ByteArrayOutputStream();
        try {
            //一次性读取2048字节
            while ((readSize = input.read(bcache)) > 0) {
                totalSize += readSize;
                if (totalSize > PROTECTED_LENGTH) {
                    throw new Exception("输入流超出50K大小限制");
                }
                //将bcache中读取的input数据写入infoStream
                infoStream.write(bcache,0,readSize);
            }
        } catch (IOException e1) {
            throw new Exception("输入流读取异常");
        } finally {
            try {
                //输入流关闭
                input.close();
            } catch (IOException e) {
                throw new Exception("输入流关闭异常");
            }
        }

        try {
            return infoStream.toString(DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new Exception("输出异常");
        }
    }

    /**
     * 把输入流的内容转化成字符串
     * @param is
     * @return
     */
    public  String readInputStream(InputStream is){
        try {
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            int length=0;
            byte[] buffer=new byte[1024];
            while((length=is.read(buffer))!=-1){
                baos.write(buffer, 0, length);
            }
            is.close();
            baos.close();
            //或者用这种方法
            //byte[] result=baos.toByteArray();
            //return new String(result);
            return baos.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }

    public  String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
