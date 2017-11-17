package com.blue.spring.exception;

import com.blue.spring.resultMessage.IResultMessage;
import org.apache.commons.lang3.ArrayUtils;

import java.text.MessageFormat;

/**
 * Created by zhangxiaobin
 */
public class BusinessException extends RuntimeException{

    // 错误码
    private String errorCode;
    // 展示给用户的错误信息
    private String errorMessage;
    // 展示给开发者的错误信息
    private String errorMessageForDev;

    public <T extends IResultMessage> BusinessException(T resultMessage, Object ...args) {
        super();

        // 设置错误码
        this.errorCode = resultMessage.getCode();

        String[] arguments = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            arguments[i] = String.valueOf(args[i]);
        }

        this.errorMessage = resultMessage.getMessage();
        if (!ArrayUtils.isEmpty(args)) {
            this.errorMessage = MessageFormat.format(resultMessage.getMessage(), arguments);
        }

        this.errorMessageForDev = resultMessage.getMessageForDev();
        if (!ArrayUtils.isEmpty(args)) {
            this.errorMessageForDev = MessageFormat.format(resultMessage.getMessageForDev(), arguments);
        }
    }

    public BusinessException(String errorCode, String errorMessage, String errorMessageForDev) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorMessageForDev = errorMessageForDev;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorMessageForDev() {
        return errorMessageForDev;
    }
}
