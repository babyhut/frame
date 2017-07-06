package com.frame.project.abstractview.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fanya.test.R;
import com.frame.project.abstractview.holder.CommonActivityHolder;
import com.frame.project.constrants.Constants;
import com.frame.project.constrants.FrameApplication;
import com.frame.project.listener.CommonClickListener;
import com.frame.project.presenter.AbstractInterface;
import com.frame.project.presenter.ActivityPresenter;
import com.frame.project.sharedpreferences.ISharedPreferencesDao;
import com.frame.project.sharedpreferences.SharedPreferencesDaoImpl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by xuyanyun on 2017/5/22.
 */

public abstract class AbstractByCommonActivity extends AppCompatActivity
        implements CommonClickListener.CommonClick,AbstractInterface.IMainView{

    /**
     * 界面初始化前期准备,在setContentview之前
     * 比如设置输入法遮挡,全屏,没有标题之类的
     *
     */
    protected void beforeViewInit() {
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FrameApplication.getInstance().addActivity(this);
    }
    /**
     * 获取布局名称
     *
     * @return  布局id
     */
    protected abstract int getLayoutID();
    public void widgetClick(View v) {

    }
    //这个方法在模拟起上测试会闪退
    private boolean canOnline(){
        if(isAvailable(this) && isConnected(this)){
            showError("网络不通畅");
            return false;
        }else{
            return true;
        }
    }
    @Override
    public void onClick(View v,Object object) {
        this.widgetClick(v);
    }

    /**
     * 初始化布局以及View控件
     */
    protected abstract void initView(Bundle savedInstanceState);
    public CommonActivityHolder commonHolder ;
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        //这个基类就是不需要再找ID
        commonHolder = new CommonActivityHolder(this);
        activityPresenter = new ActivityPresenter(this , this );
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeViewInit();
        if (getLayoutID() != 0) {
            setContentView(getLayoutID());
            initView(savedInstanceState);
        }
    }
    ////////////////////////////页面跳转///////////////////////////////
    private Intent intent = null;
    private Bundle bundle = null;
    //获取传参数据
    protected Bundle getBundle() {
        if (intent == null || bundle == null) {
            intent = getIntent();
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
    public void openActivity(Class<?> cls, Bundle extras) {
        closeActivity(cls,extras,false);
    }
    public void jumpActivity(Class<?> cls, Bundle extras) {
        closeActivity(cls,extras,true);
    }
    //打开页面,关闭页面
    public void closeActivity(Class<?> cls, Bundle extras,boolean b) {
        intent = new Intent();
        if(extras!=null){
            intent.putExtras(extras);
        }
        intent.setClass(this, cls);
        startActivity(intent);
        if(b){
            this.finish();
        }
        overridePendingTransition(R.anim.zoomin,R.anim.zoomout);
    }
    //带请求码过去的,但不关闭页面
    public void openActivityForResult(Class<?> to, int responseCode, Bundle bundle) {
        intent = new Intent(this, to);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, responseCode);
        overridePendingTransition(R.anim.zoomin,R.anim.zoomout);
    }
    ////////////////////////////页面跳转///////////////////////////////
    ////////////////////////////获取sharedPerferencees///////////////////////////////

    private ISharedPreferencesDao shareDao;

    public ISharedPreferencesDao getShareDao() {
        if (shareDao == null) {
            shareDao = new SharedPreferencesDaoImpl();
        }
        return shareDao;
    }
    ////////////////////////////获取sharedPerferencees///////////////////////////////

    ////////////////////////////////toast///////////////////////////////////////
    //错误
    public void  showError(String str){
        Toasty.error(this, str, Toast.LENGTH_SHORT, true).show();
    }
    //成功
    public void showSuccess(String str){
        Toasty.success(this, str, Toast.LENGTH_SHORT, true).show();
    }

    /**
     *
     * @param str   显示的文字
     * @param drawable   左边显示的图案
     * @param textColor  文字的颜色
     * @param tintColor  toast背景色
     */
    public void showCustom(String str, Drawable drawable , int textColor , int tintColor){
        Toasty.custom(this, str, drawable, textColor, tintColor,  true, true).show();
    }
    public void showCustom(String str, Drawable drawable){
        showCustom(str,drawable,getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorPrimary));
    }
    public void showCustomError(String str){
        showCustom(str,getResources().getDrawable(R.drawable.cry));
    }
    public void showCustomSuccess(String str){
        showCustom(str,getResources().getDrawable(R.drawable.smile));
    }
    /**
     * 判断网络是否可用
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @param context 上下文
     * @return {@code true}: 可用<br>{@code false}: 不可用
     */
    public  boolean isAvailable(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isAvailable();
    }

    /**
     * 判断网络是否连接
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @param context 上下文
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public  boolean isConnected(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isConnected();
    }
    /**
     * 获取活动网络信息
     *
     * @param context 上下文
     * @return NetworkInfo
     */
    private  NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
    //权限处理------------------------------------------------------------------------------------------------


    public void responsePermission(String... permissionName){
        if (Build.VERSION.SDK_INT < 21) {
            hasPermission();
            return;
        }
        List<String> pList = new ArrayList<>();
        for (String str:permissionName){
            pList.add(str);
        }
        String [] strings = pList.toArray(new String[pList.size()]);
        boolean bb = checkPermissionAllGranted(strings);
        if(!bb){
            //如果没有,请求
            ActivityCompat.requestPermissions(this, strings, Constants.permissionStr.permissionResponse);
        }else{
            //有权限
            hasPermission();
        }
    }
    //有权限或者申请的时候给了,就继续做下面的动作
    protected void hasPermission(){

    }
    protected void hasNoPermission(){

    }

    /**
     * 检查APP是否拥有指定的所有权限
     */
    protected boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == Constants.permissionStr.permissionResponse){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //给了权限可以直接去操作
                hasPermission();
            }else{
                showError("权限都不给,没法玩了");
            }
        }
    }

    protected void setTitle(int resId , String text){
        TextView textView = (TextView) findViewById(resId);
            if(null!=textView){
                    textView.setText(text);
            }
    }
    protected void setBack(int resId ){
        RelativeLayout mBack = (RelativeLayout) findViewById(resId);
        if(null!=mBack){
            mBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(R.anim.zoomin,R.anim.zoomout);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoomin,R.anim.zoomout);
    }

    /**
     *
     * @param resId
     * @param bb  true 显示 false 影藏
     *            针对的是标题的右边按钮
     */
    protected void setShow(int resId,boolean bb){
        View view = findViewById(resId);
        if(null!=view){
            if(bb){
            view.setVisibility(View.VISIBLE);
            }else{
            view.setVisibility(View.INVISIBLE);
            }
        }
    }
    protected void setShow(int resId){
        setShow(resId,true);
    }
    protected void loadPic(ImageView imageViewId,String picurl){
        if(!TextUtils.isEmpty(picurl) ){
            Picasso.with(this)
                    .load(picurl)
                    .into(imageViewId);
        }
    }
    //这个请求的并没有在基类里面初始化,写在这里主要是因为我自己老是会忘记destory
    protected ActivityPresenter activityPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityPresenter.unSubscribe();
    }
}
