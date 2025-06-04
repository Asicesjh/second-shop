package com.sjh.web.swiper.entity;
import lombok.Data;

@Data
public class SwiperParm {
    private Long currentPage;
    private Long pageSize;
    private String title;
}