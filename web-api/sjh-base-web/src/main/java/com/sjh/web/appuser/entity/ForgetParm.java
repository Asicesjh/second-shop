package com.sjh.web.appuser.entity;

import lombok.Data;


@Data
public class ForgetParm {
    private String phone;
    private String username;
    private String password;
}