package com.sjh.web.cinemalist.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjh.utils.ResultUtils;
import com.sjh.utils.ResultVo;
import com.sjh.web.category.entity.Category;
import com.sjh.web.category.service.CategoryService;
import com.sjh.web.cinemalist.entity.CinemaList;
import com.sjh.web.cinemalist.entity.CinemaListParm;
import com.sjh.web.cinemalist.entity.UpandDownParm;
import com.sjh.web.cinemalist.service.CinemaListService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/cinema")
public class CinemaListController {
    @Autowired
    private CinemaListService cinemaListService;
    @Autowired
    private CategoryService categoryService;
    //发布
    @PostMapping("/release")
    public ResultVo release(@RequestBody CinemaList cinemaList){
    //设置时间
        cinemaList.setCreateTime(new Date());
        if(cinemaListService.save(cinemaList)){
            return ResultUtils.success("发布成功!");
        }
        return ResultUtils.error("发布失败!");
    }
    //列表
    @GetMapping("/list")
    public ResultVo getList(CinemaListParm parm){
//构造分页对象
        IPage<CinemaList> page = new Page<>
                (parm.getCurrentPage(),parm.getPageSize());
//构造查询条件
        QueryWrapper<CinemaList> query = new QueryWrapper<>();
        query.lambda().like(StringUtils.isNotEmpty(parm.getCinemaName()),CinemaList::getCinemaName,parm.getCinemaName())
                .orderByDesc(CinemaList::getCreateTime);
        IPage<CinemaList> list = cinemaListService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
    // 获取所有影院列表（不分页）
//    @GetMapping("/allList")
//    public ResultVo getAllList() {
//        // 构造查询条件（可根据需要添加查询条件）
//        QueryWrapper<CinemaList> query = new QueryWrapper<>();
//        query.lambda().orderByDesc(CinemaList::getCreateTime);
//
//        // 直接查询所有数据
//        List<CinemaList> list = cinemaListService.list(query);
//        return ResultUtils.success("查询成功", list);
//    }
    @GetMapping("/allList")
    public ResultVo getAllList(@RequestParam(required = false) String category) {
        // 构造查询条件
        QueryWrapper<CinemaList> query = new QueryWrapper<>();

        // 如果有传入categoryName参数，则添加搜索条件
        query.lambda()
                .like(StringUtils.isNotEmpty(category),
                        CinemaList::getCategory, category) // 按分类名称模糊查询
                .orderByDesc(CinemaList::getCreateTime);       // 按创建时间降序

        // 执行查询
        List<CinemaList> list = cinemaListService.list(query);

        return ResultUtils.success("查询成功", list);
    }

    //删除
    @DeleteMapping("/{cinemaId}")
    public ResultVo delete(@PathVariable("cinemaId") Long cinemaId){
        if(cinemaListService.removeById(cinemaId)){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }
    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody CinemaList cinemaList){
        if(cinemaListService.updateById(cinemaList)){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }
    //上架下架
    @PostMapping("/upandown")
    public ResultVo upandown(@RequestBody CinemaList cinemaList){
        UpdateWrapper<CinemaList> query = new UpdateWrapper<>();
        query.lambda().set(CinemaList::getStatus,cinemaList.getStatus())
                .eq(CinemaList::getCinemaId,cinemaList.getCinemaId());
        if(cinemaListService.update(query)){
            return ResultUtils.success("设置成功!");
        }
        return ResultUtils.error("设置失败!");
    }
}