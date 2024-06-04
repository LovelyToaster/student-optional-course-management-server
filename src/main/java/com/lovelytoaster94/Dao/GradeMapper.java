package com.lovelytoaster94.Dao;

import com.lovelytoaster94.Pojo.Grade;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GradeMapper {
    List<Grade> allGradeInfo();

    boolean modifyGradeInfo(Grade grade);

    boolean deleteGradeInfo(@Param("no") String no);

    List<Grade> searchGradeInfo(Grade grade);

    boolean addGradeInfo(Grade grade);
}
