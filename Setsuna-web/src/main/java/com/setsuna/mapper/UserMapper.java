package com.setsuna.mapper;

import com.setsuna.entity.User;
import com.setsuna.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectUserByUsername(String username);

    User selectUserByUsernameAndLoginType(String username, String loginType);

    void insertUser(User user);

    UserInfo selectUserInfoByUserId(Integer id);

    void InsertUserInfo(UserInfo userInfo);

}
