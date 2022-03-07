package cn.shijianka.reggie.dto;

import cn.shijianka.reggie.entity.Setmeal;
import cn.shijianka.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
