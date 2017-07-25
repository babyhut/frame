package com.frame.project.abstractview.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanya.test.R;
import com.frame.project.abstractview.holder.CommonActivityHolder;
import com.frame.project.annotate.AnnotateUtil;
import com.frame.project.constrants.Constants;
import com.frame.project.constrants.FrameApplication;
import com.frame.project.listener.CommonClickListener;
import com.frame.project.modle.ResponseModle;
import com.frame.project.presenter.AbstractInterface;
import com.frame.project.presenter.ActivityPresenter;
import com.frame.project.requestdao.RequestInterfaceDao;
import com.frame.project.requestdao.RequestInterfaceDaoImpl;

/**
 * Created by xuyanyun on 2017/5/22.
 */

public abstract class AbstractByCommonActivity extends AppCompatActivity
        implements CommonClickListener.CommonClick,AbstractInterface.IMainView{

    //---------------------------页面前期初始化------------------------------------------

    /**
     * 界面初始化前期准备,在setContentview之前
     * 比如设置输入法遮挡,全屏,没有标题之类的
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
     * @return 布局id
     */
    protected abstract int getLayoutId();

    @Override
    public void onClick(View v, Object object) {
        this.widgetClick(v);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        AnnotateUtil.initBindView(this);
    }
    public CommonActivityHolder commonHolder ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeViewInit();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            activityPresenter = new ActivityPresenter(this, this);
            //这个基类就是不需要再找ID
            commonHolder = new CommonActivityHolder(this);
            initView(savedInstanceState);
            addView(Constants.basis.one);
        }
    }

    /**
     * 初始化布局以及View控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    //---------------------------页面前期初始化------------------------------------------
    public void widgetClick(View v) {
    }

    //////////////////获取请求的RequestDao//////////获取sharedPerferencees///////////////////////////////
    private RequestInterfaceDao requestDao;

    public RequestInterfaceDao getRequestDao() {
        if (requestDao == null) {
            requestDao = new RequestInterfaceDaoImpl();
        }
        return requestDao;
    }

    ////////////////////////////获取sharedPerferencees///////////////////////////////
    //设置标题,或者其他的文字都行
    protected void setTitle(int resId, String text) {
        TextView textView = (TextView) findViewById(resId);
        if (null != textView) {
            textView.setText(text);
        }
    }

    //返回键
    protected void setBack(int resId) {
        View mBack =  findViewById(resId);
        if (null != mBack) {
            mBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(0x99);
                    finish();
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                }
            });
        }
    }

    //返回键
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }

    /**
     * @param resId
     * @param bb    true 显示 false 影藏
     *              针对的是标题的右边按钮
     */
    protected void setShow(int resId, boolean bb) {
        View view = findViewById(resId);
        if (null != view) {
            if (bb) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    protected void setShow(int resId) {
        setShow(resId, true);
    }


    //这个请求的并没有在基类里面初始化,写在这里主要是因为我自己老是会忘记destory
    protected ActivityPresenter activityPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityPresenter.unSubscribe();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(String falMessage, String responseTag) {
        onFailed(falMessage, responseTag);
    }

    @Override
    public void onNext(ResponseModle responseModle, String responseTag) {
        onSuccess(responseModle, responseTag);
    }

    public void onSuccess(ResponseModle responseModle, String responseTag) {
    }

    ;

    public void onFailed(String responseModle, String responseTag) {
    }

    ;


    /**
     * @param position  1 正在加载中   2 没有数据的页面    3  加载错误的页面  4 正常显示的页面
     */
    LinearLayout linearLayout;//这个是添加的view

    public void addView(int position) {
        if (null == linearLayout) {
            linearLayout = (LinearLayout) findViewById(R.id.view_lay_addview);
        }
        if (null != linearLayout) {
            linearLayout.removeAllViews();
            View view = null;
            switch (position) {
                case 1:
                    view = LayoutInflater.from(this).inflate(R.layout.view_loading, null);
                    linearLayout.addView(view);
                    linearLayout.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    view = LayoutInflater.from(this).inflate(R.layout.view_empty, null);
                    linearLayout.addView(view);
                    linearLayout.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    view = LayoutInflater.from(this).inflate(R.layout.view_error, null);
                    linearLayout.addView(view);
                    linearLayout.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    linearLayout.setVisibility(View.GONE);
                    break;
            }

        }
    }

}
