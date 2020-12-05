package com.yj.sharding.service;


import com.yj.sharding.entity.User;
import com.yj.sharding.mapper.UserMapper;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getAllUserFromMaster() {
        HintManager hintManager = HintManager.getInstance();
        hintManager.setMasterRouteOnly();
        List<User> users = userMapper.findAllMsater();
        hintManager.close();
        return users;
    }

    public List<User> getAllUserFromSlave() {
        return userMapper.findAllSlave();
    }
}
