package cn.shijianka.reggie.service;

import cn.shijianka.reggie.dto.SetmealDto;
import cn.shijianka.reggie.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐同时保存关联的关系
     * @param setmealDto
     */
    void addWithDish(SetmealDto setmealDto);
    void removeWithDish(List<Long> ids);
}
