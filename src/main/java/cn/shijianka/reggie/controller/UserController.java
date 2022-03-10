package cn.shijianka.reggie.controller;

import cn.shijianka.reggie.common.CustomException;
import cn.shijianka.reggie.common.R;
import cn.shijianka.reggie.dto.UserDto;
import cn.shijianka.reggie.entity.User;
import cn.shijianka.reggie.service.UserService;
import cn.shijianka.reggie.utils.SMSUtils;
import cn.shijianka.reggie.utils.ValidateCodeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpServletRequest request) {
        //注册或者已经存在，发送短信验证码
        //生成验证码
        Integer integerCode = ValidateCodeUtils.generateValidateCode(6);
        //发送短信
        //定义短信发送工具类参数
        /**
         * 为了便于开发，绕过短信验证
         */
        System.out.println();
        System.out.println("短信验证码为："+integerCode);
        request.getSession().setAttribute("code", ""+integerCode);
        System.out.println();
       /* String signName = "阿里云短信测试";
        String templateCode = "SMS_154950909";
        String phoneNumbers = user.getPhone();
        String param = "" + integerCode;
        request.getSession().setAttribute("code", param);
        SMSUtils.sendMessage(signName, templateCode, phoneNumbers, param);*/
        return R.success("验证码已经发送");
    }

    @PostMapping("/login")
    public R<UserDto> login(@RequestBody UserDto userDto, HttpServletRequest request) {
        log.info(userDto.toString());
        String code = (String) request.getSession().getAttribute("code");
        if (!(code != null && code.equals(userDto.getCode()))) {
            return R.error("验证码不正确");
        }
        //验证码正确，如果用户未注册，顺便注册
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getPhone, userDto.getPhone());
        User one = userService.getOne(lqw);
        Long id = null;
        if (one == null) {
            //新用户，先注册
            userDto.setStatus(1);
            userService.save(userDto);
            User one1 = userService.getOne(lqw); //如果时新用户，则再查找一次//根据电话号码获得user对象
            id = one1.getId();
        } else {
            id = one.getId();
        }
        //将用户的id存入session，过滤器相应的以此为标识

        log.info("验证码正确，将user.id{}存入session", id);
        request.getSession().setAttribute("user", id);
        log.info(request.getSession().getAttribute("user").toString());
        return R.success(userDto);
    }
}
