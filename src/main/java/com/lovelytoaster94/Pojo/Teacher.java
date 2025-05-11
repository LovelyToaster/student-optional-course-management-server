package com.lovelytoaster94.Pojo;

import com.alibaba.fastjson2.annotation.JSONField;

public class Teacher {
    @JSONField
    private String teacherNo;
    @JSONField
    private String teacherName;
    @JSONField
    private String teacherSex;
    @JSONField
    private int teacherAge;
    @JSONField
    private String teacherDegree;
    @JSONField
    private String teacherJob;
    @JSONField
    private String teacherGraduateInstitutions;
    @JSONField
    private String facultyNo;
    @JSONField
    private String facultyName;
    @JSONField
    private String email;

    public String getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(String teacherNo) {
        this.teacherNo = teacherNo;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherSex() {
        return teacherSex;
    }

    public void setTeacherSex(String teacherSex) {
        this.teacherSex = teacherSex;
    }

    public int getTeacherAge() {
        return teacherAge;
    }

    public void setTeacherAge(int teacherAge) {
        this.teacherAge = teacherAge;
    }

    public String getTeacherDegree() {
        return teacherDegree;
    }

    public void setTeacherDegree(String teacherDegree) {
        this.teacherDegree = teacherDegree;
    }

    public String getTeacherJob() {
        return teacherJob;
    }

    public void setTeacherJob(String teacherJob) {
        this.teacherJob = teacherJob;
    }

    public String getTeacherGraduateInstitutions() {
        return teacherGraduateInstitutions;
    }

    public void setTeacherGraduateInstitutions(String teacherGraduateInstitutions) {
        this.teacherGraduateInstitutions = teacherGraduateInstitutions;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
