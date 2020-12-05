package com.ds.service;


import com.ds.entity.User;
import com.ds.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getAllUserFromMaster() {
        return userMapper.findAllMsater();
    }

    public List<User> getAllUserFromSlave() {
        return userMapper.findAllSlave();
    }
}
