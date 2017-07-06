package com.frame.project.constrants;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by xuyanyun on 2017/5/22.
 */

public interface ResponseServiceApi {

    @GET()
    <T> Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, Object> maps);
    @POST()
    <T> Observable<ResponseBody> post(@Url String url, @QueryMap Map<String, Object> maps);
}
