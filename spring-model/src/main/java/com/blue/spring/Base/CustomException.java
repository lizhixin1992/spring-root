package com.blue.spring.Base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Response;

/**
 * @description:
 * @email:
 * @author: lizhixin
 * @createDate: 14:58 2017/10/23
 */
public class CustomException extends Exception{
    public final Response response;

    private String mDetails = null;

    public CustomException(Response response) {
        this.response = response;
        try {
            JSONObject jsonObj = JSON.parseObject(response.body().string());
            mDetails = jsonObj.toString();
        } catch (Exception e) {
            e.printStackTrace();
            mDetails += e.getMessage();
        }
    }

    public CustomException(String msg) {
        super(msg);
        response = null;
    }

    public CustomException(Exception e) {
        super(e);
        this.response = null;
    }

    public int code() {
        return response == null ? -1 : response.code();
    }

    public String getMessage() {
        return response == null ? super.getMessage() : response.message();
    }

    public String getDetails() {
        return mDetails;
    }
}
