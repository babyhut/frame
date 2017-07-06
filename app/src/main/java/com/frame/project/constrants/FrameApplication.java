package com.frame.project.constrants;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.frame.project.retrfit.AddCookiesInterceptor;
import com.frame.project.retrfit.SaveCookiesInterceptor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xuyanyun on 2017/5/22.
 */

public class FrameApplication extends Application {
    //单例---------------构造开始------------------------------------------------------------
    // 定义私有构造方法（防止通过 new SingletonTest()去实例化）

    public FrameApplication() {
    }

    // 定义一个SingletonTest类型的变量（不初始化，注意这里没有使用final关键字）
    private static FrameApplication Singleton;

    // 定义一个静态的方法（调用时再初始化SingletonTest，使用synchronized 避免多线程访问时，可能造成重的复初始化问题）
    public static synchronized FrameApplication getInstance() {
        if (Singleton == null)
            Singleton = new FrameApplication();
        return Singleton;
    }
    //单例---------------构造结束------------------------------------------------------------


    public OkHttpClient okHttpClient;

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(300, TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(300, TimeUnit.SECONDS)//设置写的超时时间
                    .connectTimeout(300, TimeUnit.SECONDS)//设置连接超时时间
                    .addInterceptor(new AddCookiesInterceptor(this))
                    .addInterceptor(new SaveCookiesInterceptor(this))
                    .build();
        }
        return okHttpClient;
    }

    public ResponseServiceApi responseServiceApi;

    public ResponseServiceApi getResponseServiceApi() {
        if (responseServiceApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(Constants.PROJETURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            responseServiceApi = retrofit.create(ResponseServiceApi.class);
        }
        return responseServiceApi;
    }

    public static SharedPreferences sharedPreferences;

    public void getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERNECES_NAME_FORCOOKIES, Context.MODE_PRIVATE);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getSharedPreferences();
    }
    //存放当前应用程序里面打开的所有的activity
    private List<AppCompatActivity> mList = new LinkedList<>();
    public void addActivity(AppCompatActivity activity) {
        mList.add(activity);
    }
    //退出
    public void exit() {
        try {
            for (AppCompatActivity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
    //关闭某个页面
    //name 必须是完整的包名+类名  中间是.
    public void finishActivity(String  name ){
        for (AppCompatActivity activity : mList) {
            Log.e("activityName-->",activity.getClass().getName());
            if(activity.getClass().getName().equals(name)){
                activity.finish();
                break;
            }
        }
    }
}
