package com.lovelytoaster94.Service;

import com.lovelytoaster94.Pojo.User;

import java.util.List;

public interface UserService {
    User loginVerify(User user);

    boolean setPassword(String userName, String newPassword);

    boolean setAvatar(String userName, String avatarName);

    List<User> allUserInfo();

    List<User> searchUserInfo(User user);

    boolean resetPassword(String userName);
}
