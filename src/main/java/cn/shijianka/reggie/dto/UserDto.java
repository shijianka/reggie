package cn.shijianka.reggie.dto;

import cn.shijianka.reggie.entity.User;
import lombok.Data;

@Data
public class UserDto extends User {
    public String code;
}
