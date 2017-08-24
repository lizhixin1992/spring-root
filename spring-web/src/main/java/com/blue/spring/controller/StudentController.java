package com.blue.spring.controller;

import com.blue.spring.model.ResultVo;
import com.blue.spring.model.Student;
import com.blue.spring.service.StudentService;
import com.sun.net.httpserver.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.w3c.dom.NodeList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @email:
 * @author: lizhixin
 * @createDate: 9:46 2017/8/24
 */
@RequestMapping("/student")
@Controller
public class StudentController {
    private Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Resource
    private StudentService studentService;

    @RequestMapping("/toStudentView")
    public String toStudentView(HttpServletRequest request, Model model){
        logger.info("已经进入");
        List<Student> list = studentService.findAllStudent(null);
        model.addAttribute("list",list);
        return "StudentView";
    }

}
