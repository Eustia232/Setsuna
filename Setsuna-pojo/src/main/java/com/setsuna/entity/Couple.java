package com.setsuna.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Couple {

    //可以自定义json字段名
    @JsonProperty("husband")
    private String husband;

    private String wife;

}
