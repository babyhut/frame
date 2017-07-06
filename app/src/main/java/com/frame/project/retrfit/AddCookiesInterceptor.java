package com.frame.project.retrfit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.frame.project.constrants.FrameApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xuyanyun on 2017/5/10.
 */

public class AddCookiesInterceptor  implements Interceptor {
    private Context mContext;

    public AddCookiesInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String cookie = getCookie(request.url().toString(), request.url().host());
        Log.e("cookies-->",cookie+"");
        if (!TextUtils.isEmpty(cookie)) {
            builder.addHeader("Cookie", cookie);
        }

        return chain.proceed(builder.build());
    }

    private String getCookie(String url, String domain) {
//        SharedPreferences sp = mContext.getSharedPreferences(Constants.SHARED_PREFERNECES_NAME, Context.MODE_PRIVATE);


        if (!TextUtils.isEmpty(url)&& FrameApplication.getInstance().sharedPreferences.contains(url)
                &&!TextUtils.isEmpty(FrameApplication.getInstance().sharedPreferences.getString(url,""))) {
            return  FrameApplication.getInstance().sharedPreferences.getString(url,"");
        }
        if (!TextUtils.isEmpty(domain)&&FrameApplication.getInstance().sharedPreferences.contains(domain)
                && !TextUtils.isEmpty(FrameApplication.getInstance().sharedPreferences.getString(domain,""))) {
            return FrameApplication.getInstance().sharedPreferences.getString(domain,"");
        }

        return null;
    }


}