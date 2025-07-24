package com.setsuna.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserInfo {

    private Integer id;

    private Integer user_id;

    private String hobby;

    private String avatar;

}
