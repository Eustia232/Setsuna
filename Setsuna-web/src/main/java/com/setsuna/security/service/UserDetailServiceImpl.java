package com.setsuna.security.service;

import com.setsuna.constants.MessageConstant;
import com.setsuna.entity.User;
import com.setsuna.exception.UnAuthorizedException;
import com.setsuna.mapper.UserMapper;
import com.setsuna.security.entity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectUserByUsername(username);
        if (user == null) {
            throw new UnAuthorizedException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        return new LoginUser(user.getId(), user.getUsername(), user.getPassword());
    }

}
