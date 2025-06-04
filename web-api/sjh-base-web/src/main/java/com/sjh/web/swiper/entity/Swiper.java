package com.sjh.web.swiper.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("swiper")
public class Swiper {
    @TableId(type = IdType.AUTO)
    private Long swiperId;
    private String title;
    private String image;
    private String status;
    private Integer orderNum;
}