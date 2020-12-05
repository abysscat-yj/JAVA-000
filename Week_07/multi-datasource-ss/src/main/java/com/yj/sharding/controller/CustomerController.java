package com.yj.sharding.controller;

import com.yj.sharding.entity.User;
import com.yj.sharding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CustomerController {

    @Autowired
    private UserService userService;

    @GetMapping("all1")
    public List<User> getAllCustomer1() {
        return userService.getAllUserFromMaster();
    }

    @GetMapping("all2")
    public List<User> getAllCustomer2() {
        return userService.getAllUserFromSlave();
    }
}
