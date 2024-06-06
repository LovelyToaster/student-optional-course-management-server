package com.lovelytoaster94.Service;

import com.lovelytoaster94.Pojo.User;

public interface UserService {
    User loginVerify(User user);

    boolean setPassword(String userName, String newPassword);

    boolean setAvatar(String userName,String avatarName);
}
