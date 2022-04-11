package com.imall.note.po;

/**
 * @className: Student
 * @descripe:
 * @author: zpj
 * @date: 2019/7/3
 * @version: 1.0
 */
public class Student {
    public String name;
    public int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
