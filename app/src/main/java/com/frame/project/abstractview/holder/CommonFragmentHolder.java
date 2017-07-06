package com.frame.project.abstractview.holder;

import android.content.Context;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * 通用的RecyclerView的Holder
 */

public class CommonFragmentHolder {
    public View mConvertView;
    public int position;
    private SparseArray<View> mViews;
    public CommonFragmentHolder(View View) {
        super();
        this.mConvertView = View;
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

    public CommonFragmentHolder setTextViewText(@IdRes int textViewId, String text) {
        TextView tv = getView(textViewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        } else {
            tv.setText(" ");
        }
        return this;
    }

    public CommonFragmentHolder setImageByUrl(Context context , int imageViewId , String  imageByUrl, int defaultId){
        ImageView imageView = getView(imageViewId);
        if(!TextUtils.isEmpty(imageByUrl) ){
            Picasso.with(context)
                    .load(imageByUrl)
                    .error(defaultId)
                    .into(imageView);
        }
        return this;
    }
    public CommonFragmentHolder setDrawable(Context context , int imageViewId, int drawable){
        ImageView imageView = getView(imageViewId);
        Picasso.with(context).load(drawable).into(imageView);
        return this;
    }
    public CommonFragmentHolder showById(int imageViewId ){
        View view = getView(imageViewId);
        if(null!=view ){
            view.setVisibility(View.VISIBLE);
        }
        return this;
    }
    public CommonFragmentHolder showById(int imageViewId, boolean bb){
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
    public CommonFragmentHolder hidenbyId(int imageViewId  ){
        View view = getView(imageViewId);
        if(null!=view ){
            view.setVisibility(View.GONE);
        }
        return this;
    }

    public CommonFragmentHolder setBackground(int imageViewId , int drawable){
        View view = getView(imageViewId);
        if(null!=view  && drawable !=0){
            view.setBackgroundResource(drawable);
        }
        return this;
    }

    public CommonFragmentHolder setTextColor(int textViewId, int color) {
        TextView view = getView(textViewId);
        if (null != view && color != 0 ) {
            view.setTextColor(mConvertView.getResources().getColor(color));
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
