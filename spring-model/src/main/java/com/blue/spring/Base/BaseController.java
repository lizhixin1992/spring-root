package com.blue.spring.Base;

import com.blue.spring.web.StringEscapeEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditor;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 基础控制层
 * @email:
 * @author: lizhixin
 * @createDate: 13:29 2017/11/16
 */
public class BaseController {

    private static Logger _log = LoggerFactory.getLogger(BaseController.class);


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        try {
            //spring 4.2以前 可以使用这种方式
//            PropertyEditor propertyEditor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
//            binder.registerCustomEditor(Date.class, propertyEditor);
            //spring 4.2以后 可以使用这种方式
            binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
            //StringEscapeEditor是对前端传的参数进行转义（对于一些字符）
            binder.registerCustomEditor(String.class, new StringEscapeEditor());
        } catch (Exception e) {
            _log.error("BaseController初始化时发生错误！");
            throw new RuntimeException(e);
        }
    }


    /**
     *@description: 从cook中获取value
     *@params:
     *@createDate: 10:01 2017/11/8
     */
    protected String getCookieValue(HttpServletRequest request, String keyName){
        String cookValue = "";
        try {
            Cookie cookie = getCookieByName(request,keyName);
            cookValue = cookie.getValue();
            cookValue = cookValue.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            cookValue = cookValue.replaceAll("\\+", "%2B");
            cookValue = URLDecoder.decode(cookValue, "utf-8");
        } catch (Exception e) {
            _log.error("从cook中获取value异常");
            e.printStackTrace();
        }
        return cookValue;
    }

    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    protected static Cookie getCookieByName(HttpServletRequest request,String name){
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        Cookie cookie = null;
        try {
            if(cookieMap.containsKey(name)){
                cookie = cookieMap.get(name);
            }
        } catch (Exception e) {
            _log.error("getCookieByName error");
            e.printStackTrace();
        }
        return cookie;
    }

    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        try {
            Cookie[] cookies = request.getCookies();
            if(null!=cookies){
                for(Cookie cookie : cookies){
                    cookieMap.put(cookie.getName(), cookie);
                }
            }
        } catch (Exception e) {
            _log.error("ReadCookieMap error");
            e.printStackTrace();
        }
        return cookieMap;
    }

    /**
     *@description: 设置Session
     *@params:
     *@createDate: 17:30 2017/11/7
     */
    protected void setKeyValueToSession(HttpServletRequest request,String key,String value){
        try {
            request.getSession().setAttribute(key,value);
        } catch (Exception e) {
            _log.error("setKeyValueToSession error");
            e.printStackTrace();
        }
    }

    /**
     *@description: 设置cookie
     *@params:
     *@createDate: 11:48 2017/11/8
     */
    protected void setKeyValueToCookie(HttpServletRequest request, HttpServletResponse response, String key, String value){
        try {
            Cookie idcookie = new Cookie(key, value);
//        idcookie.setDomain(".cnlive.com");
//        idcookie.setPath("/videoController");
            idcookie.setMaxAge(3600*24);
            response.addCookie(idcookie);
        } catch (Exception e) {
            _log.error("setKeyValueToCookie error");
            e.printStackTrace();
        }
    }

}
