package com.blue.spring.resultMessage;

public enum CommonResultMessage implements IResultMessage {

    UNKOWN_ERROR("-1", "系统内部错误", "系统内部错误"),
    REQUIRE_VALUE("90001", "缺少必须值，{0}", "缺少必须值，{0}"),
    OUT_RANGE("90002","参数错误,{0}","参数{0},不能超过{2}左右{3},目前值为{1}"),
    RULES_NOT_MATCH("90003", "{0}与约定格式不匹配", "{0} = {1}与约定格式不匹配"),
    OUT_ARRAY("90006","{0}超出取值范围","{0}超出取值范围"),
    IS_NULL("90007","{0}不能为空","{0}不能为空"),
    PARAM_ERROR("90009","参数{0}的值与约定格式不匹配","参数{0}的值与约定格式不匹配"),
    CONTENT_WRONG("90012", "内容可能已被篡改,拒绝服务", ""),
    DYNAMIC_EXCEPTION_INFORMATION("10", "{0}", "{0}"),
    
    //innerapi----result
    SIGN_URL_INVALID("80001", "请求URL无效或缺少参数", "请求URL无效或缺少参数"),
    SECURITY_PLATFORM_NOTSUPPORT("80002", "不支持当前平台,{0}", "不支持当前平台,{0}"),
    SECURITY_PLATFORM_MD5ERROR("80003", "验证失败", "验证失败,联系开发人员"),
    
    //transcode----result
    TRANSCODE_SYS_EXCEPTION("1009", "系统异常，请稍后重试", "系统异常，请稍后重试"),
    TRANSCODE_CONTENT_ILLEGAL("1010", "内容不合法", "内容不合法"),
    TRANSCODE_FILENAME_INVALID("1014", "无效文件名", "无效文件名"),
    TRANSCODE_SOURCE_ILLEGAL("1016", "源信息不合法", "源信息不合法"),
    TRANSCODE_SOURCE_OVERRUN("1017", "源文件个数超限", "源文件个数超限"),
    TRANSCODE_EXTEND_ILLEGAL("1018", "扩展参数不合法", "扩展参数不合法"),
    TRANSCODE_CHOUZHEN_ERROR("4001", "抽帧设置的时间间隔不满足抽出默认的8张图片", "抽帧设置的时间间隔不满足抽出默认的8张图片"),
    TRANSCODE_OTHER("1000001", "转码系统异常，请稍后重试", "转码系统异常，请稍后重试")
    ;

    public final String code;
    public final String message;
    public final String messageForDev;

    CommonResultMessage(String code, String message, String messageForDev) {
        this.code = code;
        this.message = message;
        this.messageForDev = messageForDev;
    }

    public static CommonResultMessage getResult(String code) {
        for (CommonResultMessage resultMessage : CommonResultMessage.values()) {
            if (resultMessage.code.equals(code)) {
                return resultMessage;
            }
        }
        return null;
    }
    
    /**
      * 转码失败描述的获取方法
      * @param code 错误编码
      * @return 
      * @author  wangchaojie　2017年4月6日
      * 
     */
    public static String getTranscodeErrorMessage(String code) {
    	for (CommonResultMessage resultMessage : CommonResultMessage.values()) {
    		if (resultMessage.code.equals(code)) {
    			return resultMessage.message;
    		}
    	}
    	//1000 至 1009	系统异常，请稍后重试
    	if(Integer.parseInt(code) >= 1000 && Integer.parseInt(code) <= 1009){
 			return getTranscodeErrorMessage("1009");
 		//其他	转码系统异常，请稍后重试
 		}else{
 			return  getTranscodeErrorMessage("1000001");
 		}
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getMessageForDev() {
        return this.messageForDev;
    }
}
