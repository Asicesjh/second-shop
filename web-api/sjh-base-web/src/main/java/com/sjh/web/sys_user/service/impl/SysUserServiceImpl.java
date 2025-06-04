package com.sjh.web.sys_user.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjh.web.sys_user.entity.SysUser;
import com.sjh.web.sys_user.mapper.SysUserMapper;
import com.sjh.web.sys_user.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}