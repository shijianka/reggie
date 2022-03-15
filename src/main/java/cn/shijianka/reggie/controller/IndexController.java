package cn.shijianka.reggie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class IndexController {
    @Autowired
    private HttpServletResponse httpServletResponse;

    @GetMapping
    public void index() {
        httpServletResponse.setStatus(302);
        httpServletResponse.setHeader("Location","/backend/index.html");
    }
}
