package com.frame.project.requestdao;

import com.frame.project.constrants.Constants;
import com.frame.project.modle.RequestModel;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/7/6 17:05
 * 页面名称:
 */

public class RequestInterfaceDaoImpl implements RequestInterfaceDao {
    @Override
    public RequestModel login(String... strings) {
        RequestModel rm = new RequestModel();
        //比如,这里的入参千万不能错
        rm.addUrl(Constants.requestUrl.LOGINURL);
        rm.addParam("userName",strings[0]);
        rm.addParam("passWord",strings[1]);
        return rm ;
    }

    @Override
    public RequestModel regeist(String... strings) {
        RequestModel rm = new RequestModel();
        //比如,这里的入参千万不能错
        rm.addUrl(Constants.requestUrl.LOGINURL);
        rm.addParam("username",strings[0]);
        rm.addParam("REGEISTURL",strings[1]);
        return rm ;
    }
}
