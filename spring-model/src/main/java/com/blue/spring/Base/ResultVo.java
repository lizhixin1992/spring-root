package com.blue.spring.Base;

/**
 * @description:
 * @email:
 * @author: lizhixin
 * @createDate: 9:54 2017/8/24
 */
public class ResultVo {
    private Object code;
    private Object message;
    private Object data;

    public ResultVo() {
    }

    public ResultVo(Object code, Object message) {
        this.code = code;
        this.message = message;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "code=" + code +
                ", message=" + message +
                ", data=" + data +
                '}';
    }
}
