package com.blue.spring.controller;

import com.blue.spring.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    @Resource(name = "studentService")
    StudentService studentService;

    @RequestMapping("/toStudentView")
//    @ResponseBody
    public String toStudentView(HttpServletRequest request, Model model){
//        List<Student> studentlist = studentService.findAllStudent(null);
//        logger.info("Student ====" + studentlist.toString());
//        model.addAttribute("studentlist",studentlist);
//        model.addAttribute("studentlist","aaaaaa");
        return "student/StudentView";
    }

    @RequestMapping(method = RequestMethod.POST,value = "/create")
    public String createUser(HttpServletRequest request, String username, String password){
        String a = request.getParameter("username");
        System.out.println(username);
        System.out.println(password);
        return "index";
    }

}
