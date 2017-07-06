package com.frame.project.requestdao;

import com.frame.project.modle.RequestModel;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/7/6 17:00
 * 页面名称:
 */

public interface RequestInterfaceDao {
    //登陆
    RequestModel login(String... strings);

    //注册
    RequestModel regeist(String... strings);
}
