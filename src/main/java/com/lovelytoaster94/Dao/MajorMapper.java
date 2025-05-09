package com.lovelytoaster94.Dao;

import com.lovelytoaster94.Pojo.Major;

import java.util.List;

public interface MajorMapper {
    List<Major> allMajorInfo();

    boolean modifyMajorInfo(Major major);

    boolean deleteMajorInfo(String majorNo);

    List<Major> searchMajorInfo(Major major);

    boolean addMajorInfo(Major major);
}
