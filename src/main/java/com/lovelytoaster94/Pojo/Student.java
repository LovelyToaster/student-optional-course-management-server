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
    private String studentFaculties;
    @JSONField
    private String studentClass;

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

    public String getStudentFaculties() {
        return studentFaculties;
    }

    public void setStudentFaculties(String studentFaculties) {
        this.studentFaculties = studentFaculties;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }
}
