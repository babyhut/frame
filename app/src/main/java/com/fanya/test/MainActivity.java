package com.fanya.test;

import android.os.Bundle;
import android.view.View;

import com.frame.project.abstractview.activity.AbstractByCommonActivity;
import com.frame.project.modle.ResponseModle;

public class MainActivity extends AbstractByCommonActivity {


    @Override
    protected int getLayoutID() {
        return R.layout.main_test_common;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        commonHolder.setOnClickListener(this, R.id.one, R.id.two, R.id.three);
        commonHolder.setText(R.id.one, "傻逼");
        commonHolder.setText(R.id.two, "逗逼");
        commonHolder.setText(R.id.three, "二笔");
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.one:
                showSuccess("one");
                break;
            case R.id.two:
                showSuccess("two");
                break;
            case R.id.three:
                showSuccess("three");
                break;
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(String falMessage, String responseTag) {

    }

    @Override
    public void onNext(ResponseModle responseModle, String responseTag) {

    }
}
