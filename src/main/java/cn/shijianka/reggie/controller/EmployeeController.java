package cn.shijianka.reggie.controller;

import cn.shijianka.reggie.common.R;
import cn.shijianka.reggie.entity.Employee;
import cn.shijianka.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //1:将页面提交的密码password进行MD5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2:根据页面提交的用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3:如果没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("用户名不存在");

        }
        //4:密码比对，如果不一致则返回登陆失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("密码不正确");
        }
        //5：查看员工状态，如果已经禁用，返回已禁用
        if (emp.getStatus() == 0) {
            return R.error("账号已经禁用");
        }
        //6. 登录成功，将员工id存入Session并返回成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    //员工退出
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理session中的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");

    }
}
