package cn.shijianka.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
@RestController
@RequestMapping("/")
public class IndexController {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private HttpServletResponse httpServletResponse;

 /*   @GetMapping
    public void index() {
        httpServletResponse.setStatus(302);
        httpServletResponse.setHeader("Location", "/backend/index.html");
    }
    @GetMapping("/{test}")
    public void front() {
        httpServletResponse.setStatus(302);
        httpServletResponse.setHeader("Location", "/front/index.html");
    }*/

    @GetMapping
    public void index() {
        String header = httpServletRequest.getHeader("User-Agent");
        log.info(header);
        if(header.contains("windows")){
            httpServletResponse.setStatus(302);
            httpServletResponse.setHeader("Location", "/backend/index.html");
        }else {
            httpServletResponse.setStatus(302);
            httpServletResponse.setHeader("Location", "/front/index.html");
        }
    }
}
