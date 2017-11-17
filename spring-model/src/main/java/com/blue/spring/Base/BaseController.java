package com.blue.spring.Base;

import com.blue.spring.web.StringEscapeEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            PropertyEditor propertyEditor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
            binder.registerCustomEditor(Date.class, propertyEditor);
//            binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
            binder.registerCustomEditor(String.class, new StringEscapeEditor());
        } catch (Exception e) {
            _log.error("BaseController初始化时发生错误！");
            throw new RuntimeException(e);
        }
    }



}
