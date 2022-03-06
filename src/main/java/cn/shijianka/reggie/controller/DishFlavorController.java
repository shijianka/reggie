package cn.shijianka.reggie.controller;

import cn.shijianka.reggie.common.R;
import cn.shijianka.reggie.dto.DishDto;
import cn.shijianka.reggie.service.DishFlavorService;
import cn.shijianka.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishFlavorController {
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private DishService dishService;
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
            log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");

    }
}
