package com.sjh.web.appuser.entity;
import lombok.Data;

@Data
public class LoginVo {
    private Long userId;
    private String phone;
    private String nickName;
    private String token;
    private String picture;
}