package com.frame.project.abstractview.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.frame.project.abstractview.adapter.AbstractRecyclerviewAdapter;
import com.frame.project.constrants.Constants;
import com.frame.project.presenter.AbstractInterface;
import com.frame.project.presenter.ActivityPresenter;
import com.frame.project.recyclerview.OnLoadMoreListener;
import com.frame.project.recyclerview.RecyclerViewWithFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuyanyun on 2017/6/12.
 */

public abstract class AbstractPullToRefreshActivity<T> extends AbstractActivity implements
        SwipeRefreshLayout.OnRefreshListener,OnLoadMoreListener,AbstractInterface.IMainView{
    //最后一页
    protected int lastPage = 1;
    //接口里面带的入参
    protected int requstPage = 1;
    protected SwipeRefreshLayout swipeRefreshLayout ;
    protected RecyclerViewWithFooter recyclerViewWithFooter ;
    protected AbstractRecyclerviewAdapter abstractRecyclerviewAdapter ;

    @Override
    protected int getLayoutId() {
        return getLayId();
    }
    //获取布局的id
    public abstract  int getLayId();
    @Override
    protected void initView(Bundle savedInstanceState) {
        activityPresenter = new ActivityPresenter(this , this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(getSwipeRefreshLayoutId());
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerViewWithFooter = (RecyclerViewWithFooter) findViewById(getRecyclerViewId());
        initadapter();
        initRecycleview();
        initOtherView(savedInstanceState);
    }
    public abstract void initOtherView(Bundle savedInstanceState);
    //获取刷新的的id
    public abstract int getSwipeRefreshLayoutId();
    //获取recycleview的id
    public abstract int getRecyclerViewId();
    //获取item的id
    public abstract int getRecyclerViewItemId();
    //配置recycleview
    private void initRecycleview(){
        recyclerViewWithFooter.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewWithFooter.setEmpty();
        recyclerViewWithFooter.setAdapter(abstractRecyclerviewAdapter);
        recyclerViewWithFooter.setOnLoadMoreListener(this);
    }
    //设置adapter
    //获取对应adapter
    private void initadapter(){
        abstractRecyclerviewAdapter = getPtrlvAdapter(new ArrayList<T>() , getRecyclerViewItemId(),this );
    }
    //获取对应adapter
    public abstract AbstractRecyclerviewAdapter<T> getPtrlvAdapter(List<T> data ,int layId , AbstractActivity abstractActivity);

    //获取没有记录时显示的id
//    public abstract Integer geNoShowId();
    //
//    protected void showNorecordTip() {
//        recyclerViewWithFooter.setVisibility(View.GONE);
//        Integer id = geNoShowId();
//        if (id != null) {
//            View view = findViewById(id);
//            if (view != null) {
//                view.setVisibility(View.VISIBLE);
//            }
//        }
//    }
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

}
