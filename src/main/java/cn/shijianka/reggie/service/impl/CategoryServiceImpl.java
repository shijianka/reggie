package cn.shijianka.reggie.service.impl;

import cn.shijianka.reggie.entity.Category;
import cn.shijianka.reggie.mapper.CategoryMapper;
import cn.shijianka.reggie.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
