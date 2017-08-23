package com.blue.spring.model;

/**
 * @description:
 * @email:
 * @author: lizhixin
 * @createDate: 13:50 2017/8/23
 */
public class Student {
    private Integer id;
    private String name;//姓名
    private Boolean sex;//性别
    private String address;//地址

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", address='" + address + '\'' +
                '}';
    }
}
