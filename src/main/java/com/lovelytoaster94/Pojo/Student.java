package com.lovelytoaster94.Pojo;

import com.alibaba.fastjson2.annotation.JSONField;

public class Student {
    @JSONField
    private String studentNo;
    @JSONField
    private String studentName;
    @JSONField
    private String studentSex;
    @JSONField
    private int studentAge;
    @JSONField
    private String classNo;
    @JSONField
    private String className;
    @JSONField
    private String majorNo;
    @JSONField
    private String majorName;
    @JSONField
    private String facultyNo;
    @JSONField
    private String facultyName;

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSex() {
        return studentSex;
    }

    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMajorNo() {
        return majorNo;
    }

    public void setMajorNo(String majorNo) {
        this.majorNo = majorNo;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getFacultyNo() {
        return facultyNo;
    }

    public void setFacultyNo(String facultyNo) {
        this.facultyNo = facultyNo;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}
