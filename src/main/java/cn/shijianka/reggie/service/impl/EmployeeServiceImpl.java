package cn.shijianka.reggie.service.impl;

import cn.shijianka.reggie.entity.Employee;
import cn.shijianka.reggie.mapper.EmployeeMapper;
import cn.shijianka.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
