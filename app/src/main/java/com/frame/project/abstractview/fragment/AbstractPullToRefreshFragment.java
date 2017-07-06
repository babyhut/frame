package com.frame.project.abstractview.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.frame.project.abstractview.activity.AbstractActivity;
import com.frame.project.abstractview.adapter.AbstractRecyclerviewAdapter;
import com.frame.project.constrants.Constants;
import com.frame.project.presenter.AbstractInterface;
import com.frame.project.presenter.ActivityPresenter;
import com.frame.project.recyclerview.OnLoadMoreListener;
import com.frame.project.recyclerview.RecyclerViewWithFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/6/26 14:59
 * 页面名称:
 */

public abstract class AbstractPullToRefreshFragment<T> extends AbstractFragment
        implements
        SwipeRefreshLayout.OnRefreshListener,OnLoadMoreListener,AbstractInterface.IMainView{
    //最后一页
    protected int lastPage = 1;
    //接口里面带的入参
    protected int requstPage = 1;
    protected SwipeRefreshLayout swipeRefreshLayout ;
    protected RecyclerViewWithFooter recyclerViewWithFooter ;
    protected AbstractRecyclerviewAdapter abstractRecyclerviewAdapter ;
    protected ActivityPresenter activityPresenter;


    public AbstractPullToRefreshFragment(AbstractActivity abstractActivity) {
        super(abstractActivity);
    }

    @Override
    protected View inflaterView(LayoutInflater var1) {
        return    var1.inflate(getLayId(),null);

    }
    //获取布局的id
    public abstract  int getLayId();
    @Override
    protected void initView() {
        activityPresenter = new ActivityPresenter(getActivity() , this);
        swipeRefreshLayout = (SwipeRefreshLayout) fragmentRootView.findViewById(getSwipeRefreshLayoutId());
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerViewWithFooter = (RecyclerViewWithFooter) fragmentRootView.findViewById(getRecyclerViewId());
        initadapter();
        initRecycleview();
    }
    //获取刷新的的id
    public abstract int getSwipeRefreshLayoutId();
    //获取recycleview的id
    public abstract int getRecyclerViewId();
    //获取item的id
    public abstract int getRecyclerViewItemId();
    //配置recycleview
    private void initRecycleview(){
        recyclerViewWithFooter.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewWithFooter.setEmpty();
        recyclerViewWithFooter.setAdapter(abstractRecyclerviewAdapter);
        recyclerViewWithFooter.setOnLoadMoreListener(this);
    }
    //设置adapter
    //获取对应adapter
    private void initadapter(){
        abstractRecyclerviewAdapter = getPtrlvAdapter(new ArrayList<T>() , getRecyclerViewItemId(), abstractActivity);
    }
    //获取对应adapter
    public abstract AbstractRecyclerviewAdapter<T> getPtrlvAdapter(List<T> data , int layId , AbstractActivity abstractActivity);
    @Override
    public void onRefresh() {
        requstPage = 1;
        swipeRefreshLayout.setRefreshing(true);
        abstractRecyclerviewAdapter.setData(null);
        refresh();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        },2000);
    }
    private Handler handler = new Handler();
    protected abstract void refresh();
    @Override
    public void onLoadMore() {
        requstPage++;
        //这里判断请求的页码是不是大于总共的页码
        if(requstPage>lastPage){
            recyclerViewWithFooter.setEnd("没有更多数据了....");
            return ;
        }
        loadMore();
    }

    protected abstract void loadMore();

    /**
     * 更新数据
     * @param mDatasList
     * @param pageCount  一共多少页
     */
    protected void refreshData(List<T> mDatasList,  int pageCount) {
        if (mDatasList != null && !mDatasList.isEmpty()) {
            // 获取最后一页
            lastPage = pageCount;
            if (requstPage > lastPage) {
                recyclerViewWithFooter.setEnd("没有更多数据了");
            } else {
                abstractRecyclerviewAdapter.addData(mDatasList);
                if(mDatasList.size()< Constants.basis.PAGESSIZE){
                    recyclerViewWithFooter.setEnd("没有更多数据了");
                }else{
                    recyclerViewWithFooter.setLoading();
                }
            }
        } else {
            //这里要判断总数据是不是为空如果总数据为空可以显示暂无数据的页面

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(activityPresenter!=null){
            activityPresenter.unSubscribe();
        }

    }
}
