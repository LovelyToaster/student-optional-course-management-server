package com.lovelytoaster94.Service;

import com.lovelytoaster94.Pojo.Major;

import java.util.List;

public interface MajorService {
    List<Major> allMajorInfo();

    boolean modifyMajorInfo(Major major);

    boolean deleteMajorInfo(String majorNo);

    List<Major> searchMajorInfo(Major major);

    boolean addMajorInfo(Major major);
}
