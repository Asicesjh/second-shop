package com.sjh.web.cinemalist.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("cinema_list")
public class CinemaList {
    @TableId(type = IdType.AUTO)
    private Long cinemaId;
    private String cinemaName;
    private String image;
    private String price;
    private String category;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    //0：启用 1：停用
    private String status;
    // 0：未删除 1：已删除
//    private String deleteStatus;
    //时间
    private String rating;
    //描述
    private String description;
}
