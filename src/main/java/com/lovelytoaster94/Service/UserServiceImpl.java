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
    public User loginVerify(User user) {
        return userMapper.loginVerify(user);
    }

    public boolean setPassword(String userName, String newPassword) {
        return userMapper.setPassword(userName, newPassword);
    }

    public boolean setAvatar(String userName, String avatarName) {
        return userMapper.setAvatar(userName, avatarName);
    }
}
