package com.security.util;

public interface ResultCode {
    /**成功*/
    public static Integer SUCCESS = 20000;
    /**失败*/
    public static Integer ERROR = 20001;
    /**未授权(匿名)*/
    public static Integer UNAUTHORIZED_01 = 20002;
    /**未授权(认证后)*/
    public static Integer UNAUTHORIZED_02 = 20003;
}
