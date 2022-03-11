package cn.shijianka.reggie.service;

import cn.shijianka.reggie.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

@Service
public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
