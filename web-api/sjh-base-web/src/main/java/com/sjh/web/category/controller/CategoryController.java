package com.sjh.web.category.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjh.utils.ResultUtils;
import com.sjh.utils.ResultVo;
import com.sjh.web.category.entity.Category;
import com.sjh.web.category.entity.ListParm;
import com.sjh.web.category.entity.SelectType;
import com.sjh.web.category.service.CategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService CategoryService;
    //新增
    @PostMapping
    public ResultVo add(@RequestBody Category goodsCategory) {
        if (CategoryService.save(goodsCategory)) {
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }
    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody Category goodsCategory) {
        if (CategoryService.updateById(goodsCategory)) {
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }
    //删除
    @DeleteMapping("/{categoryId}")
    public ResultVo delete(@PathVariable("categoryId") Long categoryId) {
        if (CategoryService.removeById(categoryId)) {
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }
    //列表
    @GetMapping("/list")
    public ResultVo list(ListParm parm) {
//构造分页对象
        IPage<Category> page = new Page<>(parm.getCurrentPage(),
                parm.getPageSize());
//构造查询条件
        QueryWrapper<Category> query = new QueryWrapper<>();
        query.lambda().like(StringUtils.isNotEmpty(parm.getCategoryName()),
                        Category::getCategoryName, parm.getCategoryName())
                .orderByDesc(Category::getOrderNum);
//查询数据
        IPage<Category> list = CategoryService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }
    //程序分类
    // 获取全部分类列表（不分页）
    @GetMapping("/allList")
    public ResultVo getAllList() {
        // 构造查询条件
        QueryWrapper<Category> query = new QueryWrapper<>();
        query.lambda()
                .orderByDesc(Category::getOrderNum); // 保持相同的排序方式

        // 直接查询所有数据（不分页）
        List<Category> list = CategoryService.list(query);

        return ResultUtils.success("查询成功", list);
    }
}