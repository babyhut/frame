package com.frame.project.presenter;


import com.frame.project.modle.RequestModel;
import com.frame.project.modle.ResponseModle;

/**
 * Created by xuyanyun on 2017/5/22.
 */

public interface AbstractInterface {
    interface IMainView extends AbstractView{
        void onCompleted();
        void onError(String falMessage, String responseTag);
        //防止某些傻逼不知道怎么在responseBody里去区分请求是哪一个,还是直接加一个tag区别较快
        void onNext(ResponseModle responseModle, String responseTag);
    }
    /**
     *  url 请求的路径
     *  map 请求的参数
     *  tag 请求的标识
     */
    interface IMainPresenter  extends AbstractPresenter{

        void postResponse(RequestModel requestModel, String tag);
        void getResponse(RequestModel requestModel, String tag);
    }
}
