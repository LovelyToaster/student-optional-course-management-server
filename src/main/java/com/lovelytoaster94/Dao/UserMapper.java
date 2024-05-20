package com.lovelytoaster94.Dao;

import com.lovelytoaster94.Pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User loginVerify(@Param("userName") String userName, @Param("userPassword") String userPassword);
}
