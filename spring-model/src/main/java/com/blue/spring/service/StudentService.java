package com.blue.spring.service;

import com.blue.spring.model.Student;

import java.util.List;

/**
 * @description:
 * @email:
 * @author: lizhixin
 * @createDate: 14:01 2017/8/23
 */
public interface StudentService {
    Boolean addStudent(Student student);

    Boolean deleteStudentById(Integer id);

    Boolean updateStudentById(Student student);

    List<Student> findAllStudent(Student student);
}
