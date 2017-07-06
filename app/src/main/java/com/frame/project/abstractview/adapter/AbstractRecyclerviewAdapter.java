package com.frame.project.abstractview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frame.project.abstractview.activity.AbstractActivity;
import com.frame.project.abstractview.holder.CommonRecyclerHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuyanyun on 2017/5/11.
 */

public abstract class AbstractRecyclerviewAdapter<T> extends RecyclerView.Adapter {
    protected List<T> mData;
    private int layoutId;
    private View mView;
    protected AbstractActivity activity ;
    public void addData(List<T> dataa) {
        if (dataa != null) {
            mData.addAll(dataa);
        }
        notifyDataSetChanged();
    }
    //初始数据
    public void setData(List<T> dataa) {
        mData.clear();
        addData(dataa);
    }

    public AbstractRecyclerviewAdapter(List<T> data, int layoutId , AbstractActivity activity) {
        this.mData = data == null ? (List<T>) new ArrayList<>() : data;
        this.layoutId = layoutId;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(layoutId, parent, false);
        return new CommonRecyclerHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommonRecyclerHolder) {
            CommonRecyclerHolder commonHolder = (CommonRecyclerHolder) holder;
            commonHolder.position = position;
            convert(commonHolder, mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return (mData != null) ? mData.size() : 0;
    }
    public List<T> getData(){
    return (mData != null) ? mData : new ArrayList<T>();
}
    public abstract void convert(CommonRecyclerHolder holder, T t);

}
