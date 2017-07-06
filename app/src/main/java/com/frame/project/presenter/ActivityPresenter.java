package com.frame.project.presenter;

import android.content.Context;
import android.util.Log;

import com.frame.project.constrants.FrameApplication;
import com.frame.project.modle.RequestModel;
import com.frame.project.modle.ResponseModle;
import com.frame.project.retrfit.ProgressCancelListener;
import com.frame.project.retrfit.ProgressDialogHandler;
import com.frame.project.util.JsonUtil;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xuyanyun on 2017/5/22.
 * activity里面请求的
 */

public class ActivityPresenter implements AbstractInterface.IMainPresenter, ProgressCancelListener {
    private Context mContext;
    private Subscription mSubscription;//订阅
    private AbstractInterface.IMainView iMainView;//控件
    private ProgressDialogHandler mHandler;
    private JsonUtil jsonUtil;

    public ActivityPresenter(Context mContext, AbstractInterface.IMainView iMainView) {
        this.mContext = mContext;
        this.iMainView = iMainView;
        mHandler = new ProgressDialogHandler(mContext, this, true);
        jsonUtil = new JsonUtil();
    }

    private void showProgressDialog() {
        if (mHandler == null) {
            mHandler = new ProgressDialogHandler(mContext, this, true);
        }
        mHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
    }

    private void dismissProgressDialog() {
        if (mHandler != null) {
            mHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mHandler = null;
        }
    }

    //当dailog还在旋转时,点击返回键取消请求
    @Override
    public void onCancelProgress() {
        unSubscribe();
    }

    @Override
    public void unSubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void postResponse(RequestModel requestModel, final String tag) {
        showProgressDialog();
        mSubscription = FrameApplication.getInstance().getResponseServiceApi()
                .post(requestModel.getRequestUrlStr(), requestModel.getMaps())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        dismissProgressDialog();
                        iMainView.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressDialog();
                        if (e instanceof SocketTimeoutException) {
                            iMainView.onError("网络中断，请检查您的网络状态！", tag);
                        } else if (e instanceof ConnectException) {
                            iMainView.onError("连接错误!", tag);
                        } else {
                            iMainView.onError("自己处理吧!", tag);
                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseBody string) {
                        dismissProgressDialog();
                        try {
                            String str = string.string();
                            Log.e(tag, str);
                            ResponseModle responseModle = jsonUtil.parseJson(str, ResponseModle.class);
                            //这里没有判断返回值
                            //有些接口返回200  ,有些自定义过
                            iMainView.onNext(responseModle, tag);
                        } catch (IOException e) {
                            e.printStackTrace();
                            iMainView.onError("解析失败", tag);
                        }


                    }
                });
        cancelProcess();
    }

    @Override
    public void getResponse(RequestModel requestModel, final String tag) {
        showProgressDialog();
        mSubscription = FrameApplication.getInstance().getResponseServiceApi()
                .get(requestModel.getRequestUrlStr(), requestModel.getMaps())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        dismissProgressDialog();
                        iMainView.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressDialog();
                        if (e instanceof SocketTimeoutException) {
                            iMainView.onError("网络中断，请检查您的网络状态！", tag);
                        } else if (e instanceof ConnectException) {
                            iMainView.onError("连接错误!", tag);
                        } else {
                            iMainView.onError("自己处理吧!", tag);
                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseBody string) {
                        dismissProgressDialog();
                        try {
                            String str = string.string();
                            Log.e(tag, str);
                            ResponseModle responseModle = jsonUtil.parseJson(str, ResponseModle.class);
                            //这里没有判断返回值
                            //有些接口返回200  ,有些自定义过
                            iMainView.onNext(responseModle, tag);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
        cancelProcess();
    }

    //这个方法主要是防止一直在刷新
    private void cancelProcess() {
        if (mHandler != null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissProgressDialog();
                }
            }, 6000);
        }
    }
}
