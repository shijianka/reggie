package cn.shijianka.reggie.controller;

import cn.shijianka.reggie.common.R;
import cn.shijianka.reggie.dto.SetmealDto;
import cn.shijianka.reggie.entity.SetmealDish;
import cn.shijianka.reggie.service.SetmealDishService;
import cn.shijianka.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @PostMapping
    public R<String> add(@RequestBody SetmealDto setmealDto){
        log.info(setmealDto.toString());
        setmealService.addWithDish(setmealDto);
        return R.success("添加成功");
    }
}
