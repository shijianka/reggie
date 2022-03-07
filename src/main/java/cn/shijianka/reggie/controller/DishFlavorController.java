package cn.shijianka.reggie.controller;

import cn.shijianka.reggie.common.R;
import cn.shijianka.reggie.dto.DishDto;
import cn.shijianka.reggie.entity.Category;
import cn.shijianka.reggie.entity.Dish;
import cn.shijianka.reggie.entity.DishFlavor;
import cn.shijianka.reggie.service.CategoryService;
import cn.shijianka.reggie.service.DishFlavorService;
import cn.shijianka.reggie.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishFlavorController {
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
            log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /**
     * 分页查询
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //构造分页构造器对象
        Page<Dish> pDish = new Page<>(page,pageSize);
        Page<DishDto> pDishDto = new Page<>(page,pageSize);

        //条件构造器
        LambdaQueryWrapper<Dish> lqw=new LambdaQueryWrapper<>();
        //添加过滤条件
        lqw.like(name!=null,Dish::getName,name);
        //添加排序条件
        lqw.orderByDesc(Dish::getUpdateTime);
        //执行分页查询
        dishService.page(pDish,lqw);
        BeanUtils.copyProperties(pDish,pDishDto,"records");
        List<Dish> records = pDish.getRecords();

        List<DishDto> list = records.stream().map(n->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(n,dishDto);
            Long categoryId = n.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            String name1 = category.getName();
            dishDto.setCategoryName(name1);

            return dishDto;
        }).collect(Collectors.toList());


        pDishDto.setRecords(list);

        return R.success(pDishDto);
    }
    @GetMapping("/{id}")
    public R<DishDto> update(@PathVariable Long id){
        //获取Dish对象
        Dish dish = dishService.getById(id);
        //获取DishFlavor ? dishId==id
        LambdaQueryWrapper<DishFlavor> lqw=new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId,id);
//        Map<String, Object> map = dishFlavorService.getMap(lqw);
//        Set<String> set = map.keySet();
//        for (String s : set) {
//            Object o = map.get(s);
//            log.info(s);
//            log.info(o.toString());
//        }
        List<DishFlavor> list = dishFlavorService.list(lqw);
        DishDto dishDto = new DishDto();
        //拷贝dish中的数据
        dishDto.setFlavors(list);
        BeanUtils.copyProperties(dish,dishDto);
        return R.success(dishDto);
    }
}
