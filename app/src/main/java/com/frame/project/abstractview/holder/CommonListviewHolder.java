package com.frame.project.abstractview.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/6/26 10:52
 * 页面名称:
 */

public class CommonListviewHolder {
    //现在对于int作为键的官方推荐用SparseArray替代HashMap
    private final SparseArray<View> views;
    private View convertView;
    private Context context;

    private CommonListviewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.context = context;
        this.views = new SparseArray<>();
        this.convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        convertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     */
    public static CommonListviewHolder get(View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new CommonListviewHolder(parent.getContext(),parent, layoutId, position);
        }
        return (CommonListviewHolder) convertView.getTag();
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

    /**
     * 设置字符串
     */
    public CommonListviewHolder setText(int viewId,String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置图片
     */
    public CommonListviewHolder setImageResource(int viewId,int drawableId){
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置图片
     */
    public CommonListviewHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置图片
     */
    public CommonListviewHolder setImageByUrl(int viewId,String url){
        Picasso.with(context).load(url).into((ImageView) getView(viewId));
        return this;
    }
    //隐藏布局
    public CommonListviewHolder showById(int viewId,boolean bb){
        View iv = getView(viewId);
        if(bb){
            iv.setVisibility(View.VISIBLE);
        }else{
            iv.setVisibility(View.INVISIBLE);
        }
        return this;
    }
}
