package com.fanya.test.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fanya.test.R;
import com.frame.project.abstractview.activity.AbstractActivity;
import com.frame.project.annotate.BindView;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/7/6 16:50
 * 页面名称:
 */

public class CommonActivityFindIdActivity extends AbstractActivity {
    @BindView(id = R.id.one,click = true)
    private TextView one ;
    @BindView(id = R.id.two,click = true)
    private TextView two ;
    @BindView(id = R.id.three,click = true)
    private TextView three ;
    @BindView(id = R.id.four,click = true)
    private TextView four ;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_activity_noid;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        one.setText("狗剩子one");
        two.setText("狗剩子two");
        three.setText("狗剩子three");
        four.setText("狗剩子four");
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
            case R.id.four:
                showSuccess("four");
                break;
        }
    }
}
