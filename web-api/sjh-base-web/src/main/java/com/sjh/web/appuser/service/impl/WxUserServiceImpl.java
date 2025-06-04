package com.sjh.web.appuser.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjh.web.appuser.entity.WxUser;
import com.sjh.web.appuser.mapper.WxUserMapper;
import com.sjh.web.appuser.service.WxUserService;
import org.springframework.stereotype.Service;

@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser>
        implements WxUserService {
}