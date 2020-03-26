package com.hnxx.zy.clms.controller;

import com.github.pagehelper.PageInfo;
import com.hnxx.zy.clms.common.enums.ResultEnum;
import com.hnxx.zy.clms.common.utils.Result;
import com.hnxx.zy.clms.core.entity.Classes;
import com.hnxx.zy.clms.core.entity.College;
import com.hnxx.zy.clms.core.service.ClassesService;
import com.hnxx.zy.clms.core.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("college")
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    @GetMapping(value = "all")
    public Result<PageInfo> findAllByPage(@RequestParam(required = false,defaultValue = "1") int page,
                                                 @RequestParam(required = false,defaultValue = "5") int size){
        PageInfo pages = collegeService.findAllByPage(page, size);
        return new Result<>(pages);
    }

    @PostMapping("save")
    public Result save(@RequestBody College college){
        collegeService.save(college);
        return new Result<>(ResultEnum.SUCCESS);
    }

    @PutMapping("update")
    public Result<String> updateClasses(int id){
        collegeService.updateClasses(id);
        return new Result<>(ResultEnum.SUCCESS);
    }

}
