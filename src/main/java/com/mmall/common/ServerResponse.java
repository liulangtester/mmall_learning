package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by root on 10/19/19.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)//JSON序列化后含有null都不要返回到前端
public class ServerResponse<T> implements Serializable{
    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status){
        this.status=status;
    }
    /*以下两个方法，如果第二个都是String，会默认调用第一个，
    下面的createBySuccess等开放方法进行指定调用加以区分*/
    private ServerResponse(int status, String msg){
        this.status=status;
        this.msg=msg;
    }

    private ServerResponse(int status, T data){
        this.status=status;
        this.data=data;
    }

    private ServerResponse(int status, String msg, T data){
        this.status=status;
        this.msg=msg;
        this.data=data;
    }


    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    @JsonIgnore//序列化以后不会显示在JSON里面，其他公有方法都会显示
    /**
     * ResponseCode.SUCCESS.getCode()等于0，即获取到枚举对象SUCCESS的code
     * @return status=0返回true,否则返回false
     */
    public boolean isSucess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    /**
     * 不带参数的createBySuccess方法,调用ServerResponse(int status)构造方法
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    /**
     * 带String msg参数的createBySuccess方法,调用ServerResponse(int status, String msg)构造方法
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    /**
     * 带T data参数的createBySuccess方法,调用ServerResponse(int status, T data)构造方法
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    /**
     * 带String msg,T data参数的createBySuccess方法,调用ServerResponse(int status, String msg, T data)构造方法
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }


    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage) {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode, String errorMessage) {
        return new ServerResponse<T>(errorCode,errorMessage);
    }

}