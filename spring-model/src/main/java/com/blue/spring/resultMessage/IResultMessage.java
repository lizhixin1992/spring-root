package com.blue.spring.resultMessage;

/**
 * Created by zhangxiaobin
 */
public interface IResultMessage {
    /**
     * 获取错误码
     *
     * @return 错误码
     */
    public String getCode();

    /**
     * 获取展示给客户的错误信息
     *
     * @return 错误信息
     */
    public String getMessage();

    /**
     * 获取展示给开发者的错误信息
     *
     * @return 错误信息
     */
    public String getMessageForDev();
}
