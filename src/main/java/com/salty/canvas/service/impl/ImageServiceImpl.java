package com.salty.canvas.service.impl;

import cn.hutool.core.date.DateUtil;
import com.salty.canvas.constants.Constants;
import com.salty.canvas.model.URL;
import com.salty.canvas.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

/**
 * @author SaltyFish
 * @description
 * @since 2023/12/07 14:25
 */
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Value("${canvas.config.url}")
    public String url;

    @Value("${canvas.path}")
    public String localPath;

    @Override
    public URL putImage(MultipartFile image) {
        // 文件夹命名是按月份的
        String format = DateUtil.format(new Date(), "yyyy_MM");
        // 检查一下文件夹在不在 不在就补上
        String prePath = localPath + "/" + format;
        File f = new File(prePath);
        if (!f.exists()) {
            f.mkdirs();
        }
        //图片路径
        String filepath = prePath + "/" + image.getOriginalFilename();
        File file = new File(filepath);
        //上传图片
        try {
            image.transferTo(file);
        } catch (IllegalStateException | IOException e) {
            log.error("存图片时出现异常!");
            e.printStackTrace();
        }
        // 返回URL
        String res = url + format + "/" + image.getOriginalFilename();
        log.info("生成URL：" + res);
        return URL.builder().url(res).build();
    }
}
