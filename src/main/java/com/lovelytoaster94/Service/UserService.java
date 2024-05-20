package com.lovelytoaster94.Service;

import com.lovelytoaster94.Pojo.User;

public interface UserService {
    User loginVerify(String userName, String userPassword);
}
