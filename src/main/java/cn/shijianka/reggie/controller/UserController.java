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
    public R<String> sendMsg(@RequestBody User user, HttpServletRequest request){
        //注册或者已经存在，发送短信验证码
            //生成验证码
        Integer integerCode = ValidateCodeUtils.generateValidateCode(6);
            //发送短信
        //定义短信发送工具类参数
        String signName="传智健康";
        String templateCode="SMS_175485149";
        String phoneNumbers=user.getPhone();
        String param=""+integerCode;
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getPhone,user.getPhone());
        User one = userService.getOne(lqw);
        if(one==null){
            //新用户，先注册
            user.setStatus(1);
            userService.save(user);
        }
        request.getSession().setAttribute("code",param);
        SMSUtils.sendMessage(signName,templateCode,phoneNumbers,param);
        return R.success("验证码已经发送到"+phoneNumbers);
    }
    @PostMapping("/login")
    public R<UserDto> login(@RequestBody UserDto userDto,HttpServletRequest request){
        log.info(userDto.toString());
        String code = (String) request.getSession().getAttribute("code");
        if(!(code!=null&& code.equals(userDto.getCode())) ){
            return R.error("验证码不正确");
        }
        //验证码正确，如果用户未注册，顺便注册
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getPhone,userDto.getPhone());
        User one = userService.getOne(lqw);
        if(one==null){
            //新用户，先注册
            userDto.setStatus(1);
            userService.save(userDto);
        }
        //将用户的id存入session，过滤器相应的以此为标识
        request.getSession().setAttribute("user",userDto.getId());
        return R.success(userDto);
    }
}
