package com.frame.project.listener;

import android.view.View;

/**
 */

public class CommonClickListener implements View.OnClickListener {

    private Object mHolder;
    private CommonClick mOnClickListener;

    public CommonClickListener(Object holder) {
        this.mHolder = holder;
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null)
            mOnClickListener.onClick(v, mHolder);
    }

    public interface CommonClick<T> {
        void onClick(View v, T holder);
    }

    public void setOnClickListener(CommonClick mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }
}
