package com.sjh.web.appuser.entity;

import lombok.Data;


@Data
public class UpadateParm {
    private Long userId;
    private String password;
    private String oldPassword;
}