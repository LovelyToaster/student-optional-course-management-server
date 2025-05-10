package com.lovelytoaster94.Dao;

import com.lovelytoaster94.Pojo.Class;

import java.util.List;

public interface ClassMapper {
    List<Class> allClassInfo();

    boolean modifyClassInfo(Class clazz);

    boolean deleteClassInfo(String classNo);

    List<Class> searchClassInfo(Class clazz);

    boolean addClassInfo(Class clazz);
}
