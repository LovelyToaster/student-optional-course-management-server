package com.lovelytoaster94.Dao;

import com.lovelytoaster94.Pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    User loginVerify(User user);

    boolean setPassword(@Param("userName") String userName, @Param("newPassword") String newPassword);

    boolean setAvatar(@Param("userName") String userName, @Param("avatarName") String avatarName);

    List<User> allUserInfo();

    List<User> searchUserInfo(User user);

    boolean resetPassword(@Param("userName") String userName);

    boolean setEmail(@Param("userName") String userName, @Param("email") String email);

    boolean setPermissions(@Param("userName") String userName, @Param("permissions") int permissions);
}
