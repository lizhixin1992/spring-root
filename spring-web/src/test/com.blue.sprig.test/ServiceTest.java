package com.blue.sprig.test;

import com.blue.spring.model.Student;
import com.blue.spring.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @description:
 * @email:
 * @author: lizhixin
 * @createDate: 14:29 2017/8/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:config/applicationContext.xml")
public class ServiceTest {
    @Resource
    private StudentService studentService;

    @Test
    public void addTest() throws Exception {
        Student student = new Student();
        student.setId(new Integer("3"));
        student.setName("王同学");
        student.setSex(false);
        student.setAddress("北京");
        System.out.println(studentService.addStudent(student));
    }

    @Test
    public void deleteTest() throws Exception {
        System.out.println(studentService.deleteStudentById(1));
    }

    @Test
    public void updateTest() throws Exception {
        Student student = new Student();
        student.setId(new Integer("3"));
        student.setAddress("台湾");
        System.out.println(studentService.updateStudentById(student));
    }

    @Test
    public void select() throws Exception {
        Student student=new Student();
        student.setId(new Integer("1"));
        List<Student> list = studentService.findAllStudent(null);
        for (Student s : list) {
            System.out.println(s.toString());
        }
    }


    public static void main(String[] args) {
        try {
            InputStream in = new FileInputStream("D:\\test.properties.bak");
            Properties properties = new Properties();
            properties.load(in);
            System.out.println(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
