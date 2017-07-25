package com.fanya.test.activity;

import android.os.Bundle;
import android.view.View;

import com.fanya.test.R;
import com.frame.project.abstractview.activity.AbstractByCommonActivity;
import com.frame.project.util.ToastUtil;

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
        commonHolder.setOnClickListener(this, R.id.one, R.id.two, R.id.three,R.id.four);
        settTEXT();
    }
    private void settTEXT(){
        commonHolder.setText(R.id.one,"狗剩子one");
        commonHolder.setText(R.id.two,"狗剩子two");
        commonHolder.setText(R.id.three,"狗剩子three");
        commonHolder.setText(R.id.four,"狗剩子four");
    }
    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.one:
                ToastUtil.INSTRANSE.showSuccess("one",this);
                break;
            case R.id.two:
                ToastUtil.INSTRANSE.showSuccess("two",this);
                break;
            case R.id.three:
                ToastUtil.INSTRANSE.showSuccess("three",this);
                break;
            case R.id.four:
                ToastUtil.INSTRANSE.showSuccess("four",this);
                break;
        }
    }
}
