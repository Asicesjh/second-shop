package com.sjh.web.swiper.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjh.utils.ResultUtils;
import com.sjh.utils.ResultVo;
import com.sjh.web.swiper.entity.SwiperParm;
import com.sjh.web.swiper.entity.Swiper;
import com.sjh.web.swiper.service.SwiperService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/swiper")
@RestController
public class SwiperController {
    @Autowired
    private SwiperService swiperService;

    //新增
    @PostMapping
    public ResultVo add(@RequestBody Swiper swiper){
        if(swiperService.save(swiper)){
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }
    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody Swiper swiper){
        if(swiperService.updateById(swiper)){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }
    //删除
    @DeleteMapping("/{swiperId}")
    public ResultVo delete(@PathVariable("swiperId") Long swiperId){
        if(swiperService.removeById(swiperId)){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }
    //列表
    @GetMapping("/list")
    public ResultVo list(SwiperParm parm){
//构造分页对象
        IPage<Swiper> page = new Page<>
                (parm.getCurrentPage(),parm.getPageSize());
//构造查询条件
        QueryWrapper<Swiper> query = new QueryWrapper<>();
        query.lambda().like(StringUtils.isNotEmpty(parm.getTitle()),Swiper::getTitle
                        ,parm.getTitle())
                .orderByDesc(Swiper::getOrderNum);
        IPage<Swiper> list = swiperService.page(page, query);
        return ResultUtils.success("查询成功!",list);
    }

}