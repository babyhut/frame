package com.frame.project.abstractview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.frame.project.abstractview.holder.CommonListviewHolder;

import java.util.List;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/6/26 10:50
 * 页面名称:
 */

public abstract  class AbstractListiewAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> list;
    private int layId ;
    public AbstractListiewAdapter(Context context, List<T> list , int layId) {
        this.context = context;
        this.list = list;
        this.layId = layId ;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CommonListviewHolder holder = CommonListviewHolder.get(view,viewGroup, layId,i);
        convert(holder,getItem(i));
        return holder.getConvertView();
    }

    public abstract void convert(CommonListviewHolder holder,T item);

}
