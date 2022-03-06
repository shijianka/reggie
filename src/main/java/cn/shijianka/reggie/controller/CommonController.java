package cn.shijianka.reggie.controller;

import cn.shijianka.reggie.common.R;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 文件上传和下载
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        log.info(file.toString());

        //原始文件名
        String oldName = file.getOriginalFilename();
        String substring = oldName.substring(oldName.lastIndexOf("."));

        //转存之前先判断目标目录是否存在
        File f = new File(basePath);
        if(!f.exists()){
            f.mkdirs();
        }
        String newName = "photo_reggie_" + System.currentTimeMillis() + substring;
        try {
            //transferTo将文件转存到另外一个位置

            file.transferTo(new File(basePath+ newName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(newName);
    }
}