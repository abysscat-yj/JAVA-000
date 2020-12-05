package com.ds.mapper;

import com.ds.datasource.CurDataSource;
import com.ds.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface UserMapper {

    @CurDataSource
    @Select("select id, user_id userId, user_name userName from user")
    List<User> findAllMsater();

    @CurDataSource("slave")
    @Select("select id, user_id userId, user_name userName from user")
    List<User> findAllSlave();

}
