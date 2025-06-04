package com.sjh.web.category.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjh.web.category.entity.Category;
import com.sjh.web.category.mapper.CategoryMapper;
import com.sjh.web.category.service.CategoryService;
import org.springframework.stereotype.Service;
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,
        Category> implements CategoryService {
}