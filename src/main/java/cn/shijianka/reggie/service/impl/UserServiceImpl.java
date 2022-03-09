package cn.shijianka.reggie.service.impl;

import cn.shijianka.reggie.entity.User;
import cn.shijianka.reggie.mapper.UserMapper;
import cn.shijianka.reggie.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
