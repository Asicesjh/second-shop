package com.sjh.web.swiper.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjh.web.swiper.entity.Swiper;
import com.sjh.web.swiper.mapper.SwiperMapper;
import com.sjh.web.swiper.service.SwiperService;
import org.springframework.stereotype.Service;

@Service
public class SwiperServiceImpl extends ServiceImpl<SwiperMapper, Swiper> implements SwiperService {
}