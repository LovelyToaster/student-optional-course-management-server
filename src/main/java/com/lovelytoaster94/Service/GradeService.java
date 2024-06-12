package com.lovelytoaster94.Service;

import com.lovelytoaster94.Pojo.Grade;

import java.util.List;

public interface GradeService {
    List<Grade> allGradeInfo();

    boolean modifyGradeInfo(Grade grade);

    boolean deleteGradeInfo(String no);

    List<Grade> searchGradeInfo(Grade grade);

}
