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
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //构造分页构造器对象
        Page<Dish> pDish = new Page<>(page, pageSize);
        Page<DishDto> pDishDto = new Page<>(page, pageSize);

        //条件构造器
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        //添加过滤条件
        lqw.like(name != null, Dish::getName, name);
        //添加排序条件
        lqw.orderByDesc(Dish::getUpdateTime);
        //执行分页查询
        dishService.page(pDish, lqw);
        BeanUtils.copyProperties(pDish, pDishDto, "records");
        List<Dish> records = pDish.getRecords();

        List<DishDto> list = records.stream().map(n -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(n, dishDto);
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

    /**
     * 数据回显
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> update1(@PathVariable Long id) {
        DishDto dishDto = dishService.getWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 修改菜品
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    @Transactional
    public R<String> update2(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
    }

    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        //构造条件构造器
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        //查询分类为CategoryId的dish
        lqw.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        //按照sort降序
        lqw.orderByDesc(Dish::getSort);
        //起售状态
        lqw.eq(Dish::getStatus, 1);
        //list集合
        List<Dish> list = dishService.list(lqw);
        List<DishDto> dishDtoList = list.stream().map(dish2 -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish2, dishDto);
            LambdaQueryWrapper<DishFlavor> lqwDishFlavor = new LambdaQueryWrapper<>();
            lqwDishFlavor.eq(DishFlavor::getDishId, dish2.getId());
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lqwDishFlavor);

            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());

//        return R.success( dishService.list(lqw));
        return R.success(dishDtoList);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("要删除的id==>{}",ids);
        LambdaQueryWrapper<Dish> lqw=new LambdaQueryWrapper<>();
        //要删除的那个菜品必须是停售，
        lqw.eq(Dish::getStatus,0);
        for (Long id : ids) {
            //要删除的那个菜品必须是停售，
            lqw.eq(Dish::getId,id);
            dishService.remove(lqw);
        }
        return R.success("");
    }

    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable("status") Integer status,@RequestParam List<Long> ids){
//        log.info("PathVaribale==> {} , ids ==> {}",status,ids);
        List<Dish> dishes = dishService.listByIds(ids);
        List<Dish> resultList = dishes.stream().map(dish -> {
            dish.setStatus(status);
            return dish;
        }).collect(Collectors.toList());
        dishService.saveOrUpdateBatch(resultList);
        return R.success("");
    }

}