package com.frame.project.modle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuyanyun on 2017/5/19.
 * 请求的实例
 */

public class RequestModel implements Serializable {
    private Map<String , Object> map = new HashMap<>();
    private String requestUrlStr = "";

    public void addParam(String key,Object value){
        map.put(key,value);
    };
    public void addUrl(String requestUrlStr){
        this.requestUrlStr  = requestUrlStr ;
    }
    public Map<String , Object> getMaps(){
        return map ;
    }
    public String getRequestUrlStr(){return requestUrlStr;};
}
