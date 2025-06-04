package com.sjh.web.cinemalist.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjh.web.cinemalist.entity.CinemaList;
import com.sjh.web.cinemalist.mapper.CinemaListMapper;
import com.sjh.web.cinemalist.service.CinemaListService;
import org.springframework.stereotype.Service;

@Service
public class CinemaListServiceImpl extends ServiceImpl<CinemaListMapper, CinemaList> implements
        CinemaListService {
}