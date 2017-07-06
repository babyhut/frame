package com.frame.project.abstractview.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frame.project.abstractview.activity.AbstractActivity;
import com.frame.project.annotate.AnnotateUtil;

/**
 * Created by xuyanyun on 2017/6/14.
 */

public abstract class AbstractFragment extends Fragment implements View.OnClickListener{
    /** Fragment当前状态是否可见 */
    protected boolean isVisible;
    private boolean mHasLoadedOnce;
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    protected View fragmentRootView;
    private LayoutInflater mInflater;
    protected AbstractActivity abstractActivity ;
    public AbstractFragment() {
        }
    public AbstractFragment(AbstractActivity abstractActivity) {
        this.abstractActivity = abstractActivity;
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            if(mInflater == null){
                mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
        if(this.fragmentRootView == null){
            this.fragmentRootView = this.inflaterView(mInflater);
            AnnotateUtil.initBindView(this, this.fragmentRootView, null);
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup p = (ViewGroup) fragmentRootView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        isPrepared = true;
        lazyLoad();
        return this.fragmentRootView;
    }
    //加载视图
    protected abstract View inflaterView(LayoutInflater var1);
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }
    /**
     * 不可见
     */
    protected void onInvisible() {

    }
    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected void lazyLoad(){
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        mHasLoadedOnce = true ;
        initView();
    }

    protected abstract void initView();
    @Override
    public void onClick(View v) {
        this.widgetClick(v);
    }
    public void widgetClick(View v) {

    }
}
