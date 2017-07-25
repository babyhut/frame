package com.frame.project.listener;

/**
 * 创建人: xuyanyun
 * 联系方式:
 * 创建时间: 2017/7/25 17:05
 * 页面名称:
 */

public interface PermissionListener {
        //有权限或者申请的时候给了,就继续做下面的动作
        void hasPermission() ;
        //没有权限
        void hasNoPermission() ;
}
