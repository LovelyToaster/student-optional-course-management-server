package com.lovelytoaster94.Service;

import com.lovelytoaster94.Dao.GradeMapper;
import com.lovelytoaster94.Pojo.Grade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeMapper gradeMapper;

    public GradeServiceImpl(GradeMapper gradeMapper) {
        this.gradeMapper = gradeMapper;
    }

    public List<Grade> allGradeInfo() {
        return gradeMapper.allGradeInfo();
    }

    public boolean modifyGradeInfo(Grade grade) {
        return gradeMapper.modifyGradeInfo(grade);
    }

    public boolean deleteGradeInfo(String no) {
        return gradeMapper.deleteGradeInfo(no);
    }

    public List<Grade> searchGradeInfo(Grade grade) {
        return gradeMapper.searchGradeInfo(grade);
    }

    public boolean addGradeInfo(Grade grade) {
        return gradeMapper.addGradeInfo(grade);
    }
}
