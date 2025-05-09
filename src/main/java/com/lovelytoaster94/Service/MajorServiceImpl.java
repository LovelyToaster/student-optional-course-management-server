package com.lovelytoaster94.Service;

import com.lovelytoaster94.Dao.MajorMapper;
import com.lovelytoaster94.Pojo.Major;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {
    private final MajorMapper majorMapper;

    public MajorServiceImpl(MajorMapper majorMapper) {
        this.majorMapper = majorMapper;
    }

    public List<Major> allMajorInfo() {
        return majorMapper.allMajorInfo();
    }

    public boolean modifyMajorInfo(Major major) {
        return majorMapper.modifyMajorInfo(major);
    }

    public boolean deleteMajorInfo(String majorNo) {
        return majorMapper.deleteMajorInfo(majorNo);
    }

    public List<Major> searchMajorInfo(Major major) {
        return majorMapper.searchMajorInfo(major);
    }

    public boolean addMajorInfo(Major major) {
        return majorMapper.addMajorInfo(major);
    }
}
