package cn.shijianka.reggie.controller;

import cn.shijianka.reggie.common.R;
import cn.shijianka.reggie.entity.Category;
import cn.shijianka.reggie.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类成功
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category:{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        Page<Category> page1= new Page(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> lqw=new LambdaQueryWrapper();
        lqw.orderByAsc(Category::getSort);
        categoryService.page(page1,lqw);
        return R.success(page1);

    }

    /**
     * 根据id删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long id){
        log.info("删除分类，id：{}",id);
//        categoryService.removeById(id);
        categoryService.remove(id);
        return  R.success("分类信息删除成功");
    }
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类信息：{}",category);
        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

    //根据条件查询分类数据
    @GetMapping("/list")
    public R<List<Category>>  list(Category category){
        LambdaQueryWrapper<Category> lqw=new LambdaQueryWrapper();
        lqw.eq(category.getType()!=null,Category::getType,category.getType());
        //添加排序
        lqw.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(lqw);
        return R.success(list);
    }
}
