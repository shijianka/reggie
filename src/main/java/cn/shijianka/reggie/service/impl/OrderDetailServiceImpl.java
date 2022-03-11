package cn.shijianka.reggie.service.impl;

import cn.shijianka.reggie.entity.OrderDetail;
import cn.shijianka.reggie.mapper.OrderDetailMapper;
import cn.shijianka.reggie.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
