package com.fanya.test;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.fanya.test.activity.CommonActivityFindIdActivity;
import com.fanya.test.activity.CommonActivityNoFindIdActivity;
import com.frame.project.abstractview.activity.AbstractByCommonActivity;
import com.frame.project.constrants.Constants;
import com.frame.project.listener.PermissionListener;
import com.frame.project.modle.ResponseModle;
import com.frame.project.util.IntentUtil;
import com.frame.project.util.PermissionUtil;
import com.frame.project.util.ToastUtil;

import org.json.JSONObject;

public class MainActivity extends AbstractByCommonActivity implements PermissionListener{

public Handler handler = new Handler();
    @Override
    protected int getLayoutId() {
        return R.layout.main_test_common;
    }



    @Override
    protected void initView(Bundle savedInstanceState) {
        commonHolder.setOnClickListener(this, R.id.one, R.id.two, R.id.three,R.id.four,R.id.five);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.one:
                IntentUtil.INSTRANSE.openActivity(this , CommonActivityNoFindIdActivity.class,null);
                break;
            case R.id.two:
                IntentUtil.INSTRANSE.openActivity(this , CommonActivityFindIdActivity.class,null);
                break;
            case R.id.three:
                    try{
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("USERCODE","traffic");
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("userJSON ",jsonObject.toString());
                        activityPresenter.postByJson(Constants.requestUrl.LOGINURL,jsonObject1, Constants.requestUrl.LOGINURL);
                        }catch( Exception ex){

                        }
                break;
            case R.id.four:

                break;
            case R.id.five:
                PermissionUtil.INSTRANSE.responsePermission(this , this , new String[]{Constants.permissionStr.CALL_PHONE} );
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


    @Override
        public void hasPermission() {
        ToastUtil.INSTRANSE.showSuccess("给你了", this );
    }

    @Override
    public void hasNoPermission() {
        ToastUtil.INSTRANSE.showError("没给你", this );
    }
        @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
            PermissionUtil.INSTRANSE.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}
