package com.lovelytoaster94.Service;

import com.lovelytoaster94.Dao.UserMapper;
import com.lovelytoaster94.Pojo.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User loginVerify(String userName, String userPassword) {
        return userMapper.loginVerify(userName, userPassword);
    }
}
