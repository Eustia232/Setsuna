package com.setsuna.mapper;

import com.setsuna.entity.User;
import com.setsuna.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectUserByUserName(String userName);

    void insertUser(User user);

    UserInfo selectUserInfoByUserId(Integer id);

}
