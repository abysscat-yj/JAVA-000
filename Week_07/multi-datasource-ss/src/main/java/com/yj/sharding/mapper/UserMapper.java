package com.yj.sharding.mapper;

import com.yj.sharding.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface UserMapper {

    @Select("select id, user_id userId, user_name userName from user")
    List<User> findAllMsater();

    @Select("select id, user_id userId, user_name userName from user")
    List<User> findAllSlave();

}
