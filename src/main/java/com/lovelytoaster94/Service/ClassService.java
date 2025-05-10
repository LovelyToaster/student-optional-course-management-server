package com.lovelytoaster94.Service;

import com.lovelytoaster94.Pojo.Class;

import java.util.List;

public interface ClassService {
    List<Class> allClassInfo();

    boolean modifyClassInfo(Class clazz);

    boolean deleteClassInfo(String classNo);

    List<Class> searchClassInfo(Class clazz);

    boolean addClassInfo(Class clazz);
}
