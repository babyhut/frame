package com.frame.project.abstractview.holder;

import android.content.Context;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.project.abstractview.activity.AbstractByCommonActivity;
import com.frame.project.listener.CommonClickListener;
import com.squareup.picasso.Picasso;

/**
 * 通用的RecyclerView的Holder
 */

public class CommonActivityHolder {
    public AbstractByCommonActivity mConvertView;
    private SparseArray<View> mViews;
    public CommonActivityHolder(AbstractByCommonActivity itemView) {
        super();
        this.mConvertView = itemView;
        this.mViews = new SparseArray<>();
    }
    /**
     * 得到item上的控件
     *
     * @param viewId 控件的id
     * @return 对应的控件
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;

    }

    public CommonActivityHolder setText(@IdRes int textViewId, String text) {
        TextView tv = getView(textViewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        } else {
            tv.setText(" ");
        }
        return this;
    }

    public CommonActivityHolder setImageByUrl(Context context , int imageViewId , String  imageByUrl, int defaultId){
        ImageView imageView = getView(imageViewId);
        if(!TextUtils.isEmpty(imageByUrl) ){
            Picasso.with(context)
                    .load(imageByUrl)
                    .error(defaultId)
                    .into(imageView);
        }
        return this;
    }
    public CommonActivityHolder setDrawable(Context context , int imageViewId, int drawable){
        ImageView imageView = getView(imageViewId);
        Picasso.with(context).load(drawable).into(imageView);
        return this;
    }
    public CommonActivityHolder showById(int imageViewId ){
        View view = getView(imageViewId);
        if(null!=view ){
            view.setVisibility(View.VISIBLE);
        }
        return this;
    }
    public CommonActivityHolder showById(int imageViewId, boolean bb){
        View view = getView(imageViewId);
        if(null!=view ){
            if(bb){
                view.setVisibility(View.VISIBLE);
            }else{
                view.setVisibility(View.INVISIBLE);
            }
        }
        return this;
    }
    public CommonActivityHolder hidenbyId(int imageViewId  ){
        View view = getView(imageViewId);
        if(null!=view ){
            view.setVisibility(View.GONE);
        }
        return this;
    }

    public CommonActivityHolder setBackground(int imageViewId , int drawable){
        View view = getView(imageViewId);
        if(null!=view  && drawable !=0){
            view.setBackgroundResource(drawable);
        }
        return this;
    }

    public CommonActivityHolder setTextColor(int textViewId, int color) {
        TextView view = getView(textViewId);
        if (null != view && color != 0 ) {
            view.setTextColor(mConvertView.getResources().getColor(color));
        }
        return this;
    }
    public CommonActivityHolder setOnClickListener(CommonClickListener.CommonClick clickListener, @IdRes int... viewIds) {
        CommonClickListener listener = new CommonClickListener(this);
        listener.setOnClickListener(clickListener);
        for (int id : viewIds) {
            View v = getView(id);
            v.setOnClickListener(listener);
        }
        return this;
    }
    public void destoryView(){
        for (int i = 0, key = mViews.keyAt(i); i < mViews.size(); i++) {
            Object obj = mViews.get(key);
            obj = null ;
        }
    }
}
