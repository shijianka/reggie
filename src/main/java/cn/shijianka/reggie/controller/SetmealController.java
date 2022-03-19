package cn.shijianka.reggie.controller;

import cn.shijianka.reggie.common.R;
import cn.shijianka.reggie.dto.DishDto;
import cn.shijianka.reggie.dto.SetmealDto;
import cn.shijianka.reggie.entity.Category;
import cn.shijianka.reggie.entity.Setmeal;
import cn.shijianka.reggie.entity.SetmealDish;
import cn.shijianka.reggie.service.CategoryService;
import cn.shijianka.reggie.service.SetmealDishService;
import cn.shijianka.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public R<String> add(@RequestBody SetmealDto setmealDto){
        log.info(setmealDto.toString());
        setmealService.addWithDish(setmealDto);
        return R.success("添加成功");
    }
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> setmealPage=new Page<>(page,pageSize);
        LambdaQueryWrapper<Setmeal> lqw=new LambdaQueryWrapper<>();
        lqw.like(name!=null,Setmeal::getName,name)
                .orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(setmealPage,lqw);

        //套餐分类需要将categoryId在页面上展示为分类名称，所以引入StemealDto，其中有categoryName属性
        Page<SetmealDto> setmealDtoPage = new Page<>();
        //用工具类BeanUtils完成两个对象之间属性的拷贝 , records,是Page对象的属性，表示页面需要展示的所有数据 所以在拷贝时忽略该属性（因为CategoryId还未处理）
        BeanUtils.copyProperties(setmealPage,setmealDtoPage,"records");
        //处理categoryid,获取含有categoryname的list集合
        List<Setmeal> setmealRecords = setmealPage.getRecords();
        List<SetmealDto> setmealDtoRecords = setmealRecords.stream().map(setmeal -> {
            //获取categoryId
            Long categoryId = setmeal.getCategoryId();
            //获取category
            Category category = categoryService.getById(categoryId);
            //获取categoryName
            String categoryName = category.getName();
            //创建SetmealDto
            SetmealDto setmealDto=new SetmealDto();
            //拷贝Setmeal所有属性
            BeanUtils.copyProperties(setmeal,setmealDto);
            //将categoryName赋值给setmealDto
            setmealDto.setCategoryName(categoryName);
            //返回setmealDto
            return setmealDto;
        }).collect(Collectors.toList());
        //将含有categoryname的list集合赋值给setmealDtoPage
        setmealDtoPage.setRecords(setmealDtoRecords);
        //返回setmealDtoPage
        return R.success(setmealDtoPage);

    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info(ids.toString());
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }

    /**
     * 根据条件查询套餐数据
     * @param setmeal
     * @returnw
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list( Setmeal setmeal){
        List<Setmeal> list = setmealService.list();
        return R.success(list);
    }
}
