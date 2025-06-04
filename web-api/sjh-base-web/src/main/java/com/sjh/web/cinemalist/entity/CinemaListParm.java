package com.sjh.web.cinemalist.entity;
import lombok.Data;

@Data
public class CinemaListParm {
    private Long currentPage;
    private Long pageSize;
    private String cinemaName;
}