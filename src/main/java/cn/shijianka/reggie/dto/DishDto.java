package cn.shijianka.reggie.dto;

import cn.shijianka.reggie.entity.Dish;
import cn.shijianka.reggie.entity.DishFlavor;
import cn.shijianka.reggie.entity.Dish;
import cn.shijianka.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
