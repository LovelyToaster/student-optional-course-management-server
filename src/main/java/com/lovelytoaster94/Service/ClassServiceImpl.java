package com.lovelytoaster94.Service;

import com.lovelytoaster94.Dao.ClassMapper;
import com.lovelytoaster94.Pojo.Class;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl implements ClassService {
    private final ClassMapper classMapper;

    public ClassServiceImpl(ClassMapper classMapper) {
        this.classMapper = classMapper;
    }

    public List<Class> allClassInfo() {
        return classMapper.allClassInfo();
    }

    public boolean modifyClassInfo(Class clazz) {
        return classMapper.modifyClassInfo(clazz);
    }

    public boolean deleteClassInfo(String classNo) {
        return classMapper.deleteClassInfo(classNo);
    }

    public List<Class> searchClassInfo(Class clazz) {
        return classMapper.searchClassInfo(clazz);
    }

    public boolean addClassInfo(Class clazz) {
        return classMapper.addClassInfo(clazz);
    }
}
