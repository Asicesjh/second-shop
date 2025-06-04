package com.sjh.web.appuser.entity;

import lombok.Data;

@Data
public class WxUserParm {
    private Long currentPage;
    private Long pageSize;
    private String phone;
}
