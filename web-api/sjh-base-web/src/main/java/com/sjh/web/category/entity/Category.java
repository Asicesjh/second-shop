package com.sjh.web.category.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@Data
@TableName("cinemacategory")
public class Category {
    @TableId(type = IdType.AUTO)
    private Long categoryId;
    private String categoryName;
    private Integer orderNum;
}