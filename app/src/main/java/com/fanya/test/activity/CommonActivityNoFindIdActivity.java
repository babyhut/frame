package com.fanya.test.activity;

import android.os.Bundle;

import com.fanya.test.R;
import com.frame.project.abstractview.activity.AbstractByCommonActivity;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/7/6 16:50
 * 页面名称:
 */

public class CommonActivityNoFindIdActivity extends AbstractByCommonActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_activity_noid;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //比如这里可以做设置标题,设置返回键
        settTEXT();
    }
    private void settTEXT(){
        commonHolder.setText(R.id.one,"狗剩子one");
        commonHolder.setText(R.id.two,"狗剩子two");
        commonHolder.setText(R.id.three,"狗剩子three");
        commonHolder.setText(R.id.four,"狗剩子four");
    }
}
