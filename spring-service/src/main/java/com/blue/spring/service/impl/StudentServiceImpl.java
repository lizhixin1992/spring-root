package com.blue.spring.service.impl;

import com.blue.spring.dao.StudentDao;
import com.blue.spring.model.Student;
import com.blue.spring.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @email:
 * @author: lizhixin
 * @createDate: 14:03 2017/8/23
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentDao studentDao;

    public Boolean addStudent(Student student) {
        return studentDao.insert(student);
    }

    public Boolean deleteStudentById(Integer id) {
        Student student = new Student();
        student.setId(id);
        return studentDao.delete(student);
    }

    public Boolean updateStudentById(Student student) {
        return studentDao.update(student);
    }

    public List<Student> findAllStudent(Student student) {
        return studentDao.select(student);
    }
}
